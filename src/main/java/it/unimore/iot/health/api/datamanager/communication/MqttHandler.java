package it.unimore.iot.health.api.datamanager.communication;

import com.google.gson.Gson;
import it.unimore.iot.health.api.datamanager.utils.SenMLPack;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * {@code MqttHandler} is the class responsible for managing the MQTT communication, so it is the hook for the telemetry part of the complete project.
 *
 * @author Jacopo Maragna, Undergraduate student - 271504@studenti.unimore.it
 */
public class MqttHandler {

    // Global variables
    /**
     * The use of global variables allows the visibility of them in both the main thread and the listeners' threads,
     * since the moment they will be initialized into the listeners.
     */
    public static InfoMessageDescriptorSS infoPayload = new InfoMessageDescriptorSS();
    public static List<SenMLPack> telemetryPayload = new ArrayList<>();
    public static boolean finish; // Variable to state the end of the subscribing

    // Utils
    private final Gson gson;
    private final static Logger logger = LoggerFactory.getLogger(MqttHandler.class);

    // MQTT related variables
    private IMqttClient client;
    private MqttConnectOptions options;

    // Variables to map the mqtt's topics
    private final String controlTopic;
    private final String telemetryTopic;
    private final String infoTopic;

    public MqttHandler() {

        // Utils initialization
        this.gson = new Gson();

        // Topics initialization using the configuration files
        this.infoTopic = String.format("%s/%s/%s/%s",
                MqttConfigurationParametersSS.MQTT_BASIC_TOPIC,
                MqttConfigurationParametersSS.SMARTWATCH_TOPIC,
                MqttConfigurationParametersSS.SMARTWATCH_ID,
                MqttConfigurationParametersSS.SMARTWATCH_INFO_TOPIC);

        this.telemetryTopic = String.format("%s/%s/%s/%s",
                MqttConfigurationParametersSS.MQTT_BASIC_TOPIC,
                MqttConfigurationParametersSS.SMARTWATCH_TOPIC,
                MqttConfigurationParametersSS.SMARTWATCH_ID,
                MqttConfigurationParametersSS.SMARTWATCH_TELEMETRY_TOPIC);

        this.controlTopic = String.format("%s/%s/%s/%s",
                MqttConfigurationParametersSS.MQTT_BASIC_TOPIC,
                MqttConfigurationParametersSS.SMARTWATCH_TOPIC,
                MqttConfigurationParametersSS.SMARTWATCH_ID,
                MqttConfigurationParametersSS.SMARTWATCH_CONTROL_TOPIC);
    }

    // Getters for the data retrieved from the broker
    public InfoMessageDescriptorSS getInfoPayload() {
        return infoPayload;
    }

    public List<SenMLPack> getTelemetryPayload() {
        return telemetryPayload;
    }

    /**
     * This method allows to realize the initialization as a MQTT client
     */
    private void initMQTTClient() {

        try {
            // Random string to identify the connection id
            String clientId = UUID.randomUUID().toString();

            // MQTT client attributes
            MqttClientPersistence persistence = new MemoryPersistence();
            this.client = new MqttClient(String.format("tcp://%s:%d", MqttConfigurationParametersSS.BROKER_ADDRESS,
                    MqttConfigurationParametersSS.BROKER_PORT), clientId, persistence);

            // Setting up the options
            this.options = new MqttConnectOptions();
            this.options.setUserName(MqttConfigurationParametersSS.MQTT_USERNAME);
            this.options.setPassword((MqttConfigurationParametersSS.MQTT_PASSWORD).toCharArray());
            this.options.setAutomaticReconnect(true);
            this.options.setCleanSession(true);
            this.options.setConnectionTimeout(10);

        } catch (Exception e) {
            logger.error("Something went wrong initializing the MQTT client\n");
            e.printStackTrace();
        }
    }

    /**
     * This method allows to realize the connection to the Broker
     */
    public void connectClient() {
        try {
            initMQTTClient(); // call to the stand above method
            this.client.connect(options);
        } catch (Exception e) {
            logger.error("Something went wrong connecting to the MQTT broker\n");
            e.printStackTrace();
        }
    }

    /**
     * This method allows to realize the subscription to the info topic for receiving the smartwatch related data
     */
    public void subscribeInfo() {

        try {
            if (this.client.isConnected()) {

                // The subscribe method uses a listener dispatched to another parallel thread
                this.client.subscribe(this.infoTopic, (topic, mqttMessage) -> {
                    byte[] payload = mqttMessage.getPayload();
                    infoPayload = gson.fromJson(new String(payload), InfoMessageDescriptorSS.class); // saving data on the global variable
                });
            } else {
                logger.error("Error: MQTT client is not connected!");
            }
        } catch (Exception e) {
            logger.error("Something went wrong subscribing to smartwatch information\n");
            e.printStackTrace();
        }
    }

    /**
     * This method allows to realize the subscription to the telemetry topic
     */
    public void subscribeTelemetry() {
        try {

            // The subscribe method uses a listener dispatched to another parallel thread
            if (this.client.isConnected()) {
                this.client.subscribe(this.telemetryTopic, (topic, mqttMessage) -> {
                    byte[] payload = mqttMessage.getPayload();
                    telemetryPayload.add(gson.fromJson(new String(payload), SenMLPack.class)); // Saving data on the global variable
                });
            } else {
                logger.error("Error: MQTT client is not connected!");
            }

        } catch (Exception e) {
            logger.error("Something went wrong subscribing to smartwatch telemetry\n");
            e.printStackTrace();
        }
    }

    /**
     * This method allows to send command START
     */
    public void sendCommandStart() {

        try {

            if (this.client.isConnected()) {

                // Variables to synchronize the communication
                String jsonPayload = this.gson.toJson(true);

                MqttMessage msg = new MqttMessage(jsonPayload.getBytes());
                msg.setQos(2);
                msg.setRetained(false); // Need to be NOT retained
                this.client.publish(this.controlTopic, msg);

            } else {
                logger.error("Error: Topic or Msg = Null or MQTT Client is not Connected !");
            }

        } catch (Exception e) {
            logger.error("Something went wrong publishing START command!\n");
            e.printStackTrace();
        }
    }

    /**
     * This method allows to wait to finish the sensing session
     */
    public void waitToFinish() {

        try {

            if (this.client.isConnected()) {

                // The subscribe method uses a listener dispatched to another parallel thread
                this.client.subscribe(this.controlTopic, (topic, mqttMessage) -> {

                    byte[] payload = mqttMessage.getPayload();
                    finish = gson.fromJson(new String(payload), Boolean.class);

                });

                // Block the main thread until the command FINISH is received and the global variable finish becomes consistent
                boolean finishFlag = false;

                // Run until not finished
                while (!finishFlag) {
                    Thread.sleep(1000); // It allows data synchronization between listener's thread and main thread
                    finishFlag = finish;
                }

                finish = false; // For precaution we set the global variable to false in order to avoid a non-block on the next communications

            } else {
                logger.error("Error: MQTT client is not connected!");
            }

        } catch (Exception e) {
            logger.error("Something went wrong subscribing to finish command\n");
            e.printStackTrace();
        }

    }

    /**
     * This method allows to disconnect the client MQTT
     */
    public void disconnectClient() {
        try {
            this.client.disconnect();
            this.client.close();
        } catch (Exception e) {
            logger.error("Disconnection failed!\n");
            e.printStackTrace();
        }
    }

}
