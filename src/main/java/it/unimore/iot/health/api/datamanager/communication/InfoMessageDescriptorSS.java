package it.unimore.iot.health.api.datamanager.communication;

/**
 * {@code InfoMessageDescriptorSS} is a classic POJO class representing smartwatch info data.
 * @author Jacopo Maragna, Undergraduate student - 271504@studenti.unimore.it
 */
public class InfoMessageDescriptorSS {

    //Attributes
    /**
     * A String representing smartwatch identifier.
     */
    private String deviceId;
    /**
     * A String representing smartwatch manufacturer.
     */
    private String producer;
    /**
     * A String representing smartwatch software version.
     */
    private String softwareVersion;

    //Constructors
    public InfoMessageDescriptorSS() {
    }
    public InfoMessageDescriptorSS(String deviceId, String producer, String softwareVersion) {
        this();
        this.deviceId = deviceId;
        this.producer = producer;
        this.softwareVersion = softwareVersion;
    }

    //Getters and Setters
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getProducer() {
        return producer;
    }
    public void setProducer(String producer) {
        this.producer = producer;
    }
    public String getSoftwareVersion() {
        return softwareVersion;
    }
    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    //Override toString() to print
    @Override
    public String toString() {
        return "InfoMessageDescriptorSS{" +
                "deviceId='" + deviceId + '\'' +
                ", producer='" + producer + '\'' +
                ", softwareVersion='" + softwareVersion + '\'' +
                '}';
    }
}