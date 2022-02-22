package it.unimore.iot.health.api.datamanager.utils;

import java.util.ArrayList;

/**
 * {@code SenMLPack} class represents a SenMLRecord ArrayList used for receiving telemetry data within
 * {@code MqttHandler} and {@code HealthCheckResource} classes, and to emulate telemetry data within
 * {@code DummyDataGenerator}
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
public class SenMLPack extends ArrayList<SenMLRecord> {

    /**
     * This method emulates the SenMLPack provided by the telemetry.
     *
     * @return a SenMLPack containing dummy SenMLRecords
     */
    public static ArrayList<SenMLPack> provideSenMlListSample() {

        // ------------ HeartBeat SenMLPack Sample
        SenMLPack senMLPackHeartBeat = new SenMLPack();
        SenMLRecord senMLRecordHeartBeat1 = new SenMLRecord();
        SenMLRecord senMLRecordHeartBeat2 = new SenMLRecord();
        SenMLRecord senMLRecordHeartBeat3 = new SenMLRecord();

        senMLRecordHeartBeat1.setBn("smartWatchId");
        senMLRecordHeartBeat1.setBt(0);
        senMLRecordHeartBeat1.setBu("beat/min");
        senMLRecordHeartBeat1.setN("heart_rate_sensor");
        senMLRecordHeartBeat1.setV(101.0);

        senMLRecordHeartBeat2.setN("heart_rate_sensor");
        senMLRecordHeartBeat2.setT(1644443095109L);
        senMLRecordHeartBeat2.setV(105.0);

        senMLRecordHeartBeat3.setN("heart_rate_sensor");
        senMLRecordHeartBeat3.setT(1644443096121L);
        senMLRecordHeartBeat3.setV(103.0);

        senMLPackHeartBeat.add(senMLRecordHeartBeat1);
        senMLPackHeartBeat.add(senMLRecordHeartBeat2);
        senMLPackHeartBeat.add(senMLRecordHeartBeat3);
        // ------------------

        // ------------ Temperature SenMLPack Sample
        SenMLPack senMLPackTemperature = new SenMLPack();
        SenMLRecord senMLRecordTemperature = new SenMLRecord();

        senMLRecordTemperature.setBn("smartWatchId");
        senMLRecordTemperature.setBt(0);
        senMLRecordTemperature.setBu("Cel");
        senMLRecordTemperature.setN("temperature_sensor");
        senMLRecordTemperature.setV(36.5);

        senMLPackTemperature.add(senMLRecordTemperature);
        // ------------------

        // ------------ Glucose SenMLPack Sample
        SenMLPack senMLPackGlucose = new SenMLPack();
        SenMLRecord senMLRecordGlucose = new SenMLRecord();

        senMLRecordGlucose.setBn("smartWatchId");
        senMLRecordGlucose.setN("glucose_sensor");
        senMLRecordGlucose.setBt(0);
        senMLRecordGlucose.setBu("mg/l");
        senMLRecordGlucose.setV(7.1);

        senMLPackGlucose.add(senMLRecordGlucose);
        // ------------------

        // --- Saturation SenMLPack Sample
        SenMLPack senMLPackSaturation = new SenMLPack();
        SenMLRecord senMLRecordSaturation1 = new SenMLRecord();
        SenMLRecord senMLRecordSaturation2 = new SenMLRecord();
        SenMLRecord senMLRecordSaturation3 = new SenMLRecord();

        senMLRecordSaturation1.setBn("smartWatchId");
        senMLRecordSaturation1.setN("saturation_sensor");
        senMLRecordSaturation1.setBt(0);
        senMLRecordSaturation1.setBu("%");
        senMLRecordSaturation1.setV(92.0);

        senMLRecordSaturation2.setT(1644443095109L);
        senMLRecordSaturation2.setN("saturation_sensor");
        senMLRecordSaturation2.setV(93.0);

        senMLRecordSaturation3.setT(1644443096121L);
        senMLRecordSaturation3.setN("saturation_sensor");
        senMLRecordSaturation3.setV(93.5);

        senMLPackSaturation.add(senMLRecordSaturation1);
        senMLPackSaturation.add(senMLRecordSaturation2);
        senMLPackSaturation.add(senMLRecordSaturation3);
        // ------------------

        ArrayList<SenMLPack> senMLPackArrayListSample = new ArrayList<>();
        senMLPackArrayListSample.add(senMLPackHeartBeat);
        senMLPackArrayListSample.add(senMLPackSaturation);
        senMLPackArrayListSample.add(senMLPackGlucose);
        senMLPackArrayListSample.add(senMLPackTemperature);

        return senMLPackArrayListSample;

    }

}
