package it.unimore.iot.health.api.datamanager.utils;

import it.unimore.iot.health.api.datamanager.exception.DataManagerException;
import it.unimore.iot.health.api.datamanager.model.HealthCheckDescriptor;
import it.unimore.iot.health.api.datamanager.model.PatientDescriptor;
import it.unimore.iot.health.api.datamanager.persistence.IInventoryDataManager;
import java.time.LocalDateTime;

/**
 * The {@code DummyDataGenerator} class is a utility class that can create patients and healthchecks
 * in order to start the demo with already some patients and healthchecks to work with via the RESTful API.
 *
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
public final class DummyDataGenerator {

    private DummyDataGenerator(){
        throw new UnsupportedOperationException("This utility class cannot be instantiated!");
    }

    /**
     * This method is responsible for setting up, assigning data and saving some demo patients in a specific data structure.
     *
     * @param inventoryDataManager  The class responsible for saving patients in a specific data structure when calling {@code createNewPatient()} method.
     * @throws DataManagerException The exception thrown in case of errors relating to the inventoryDataManager with an explanatory string.
     * @since 1.0
     */
    public static void generateDummyPatients(IInventoryDataManager inventoryDataManager) throws DataManagerException {

        //patient 1
        PatientDescriptor patientDescriptor = new PatientDescriptor();
        patientDescriptor.setPatientId("ab4e277b-dff6-4475-9944-82eabd5a93e9");
        patientDescriptor.setName("Mario");
        patientDescriptor.setSurname("Rossi");
        patientDescriptor.setGender("male");
        patientDescriptor.setDateOfBirth("12-03-1996");
        patientDescriptor.setPlaceOfBirth("Pieve di Coriano");
        patientDescriptor.setPhoneNumber(33495567729L);
        patientDescriptor.setEmail("mario.rossi@gmail.com");

        //patient 2
        PatientDescriptor patientDescriptor2 = new PatientDescriptor();
        patientDescriptor2.setPatientId("218f1737-d7cf-4891-b4f2-ab330613a4bc");
        patientDescriptor2.setName("Lucia");
        patientDescriptor2.setSurname("Bianchi");
        patientDescriptor2.setGender("female");
        patientDescriptor2.setDateOfBirth("02-11-1973");
        patientDescriptor2.setPlaceOfBirth("Mantova");
        patientDescriptor2.setPhoneNumber(3472945775L);
        patientDescriptor2.setEmail("lucia.bianchi@gmail.com");

        //patient 3
        PatientDescriptor patientDescriptor3 = new PatientDescriptor();
        patientDescriptor3.setPatientId("de5dbdf3-ab9c-4b1f-860c-a72f91caa125");
        patientDescriptor3.setName("Luca");
        patientDescriptor3.setSurname("Verdi");
        patientDescriptor3.setGender("male");
        patientDescriptor3.setDateOfBirth("03-10-2001");
        patientDescriptor3.setPlaceOfBirth("Poggio Rusco");
        patientDescriptor3.setPhoneNumber(347483784703L);
        patientDescriptor3.setEmail("luca.verdi@gmail.com");


        //create patients
        inventoryDataManager.createNewPatient(patientDescriptor);
        inventoryDataManager.createNewPatient(patientDescriptor2);
        inventoryDataManager.createNewPatient(patientDescriptor3);

    }

    /**
     * This method is responsible for setting up, assigning data and saving some demo healthchecks in a specific data structure.
     *
     * @param inventoryDataManager  It is responsible for saving healthcecks in a specific data structure when calling  {@code createNewHealthCheck()} method.
     * @throws DataManagerException The exception thrown in case of errors relating to the inventoryDataManager with an explanatory string.
     * @since 1.0
     */
    public static void generateDummyHealthChecks(IInventoryDataManager inventoryDataManager) throws DataManagerException {

        //SenMLPack HeartBeat HealthCheck1
        SenMLPack senMLPackHeartBeat1 = new SenMLPack();
        SenMLRecord senMLRecordHeartBeat1 = new SenMLRecord();
        SenMLRecord senMLRecordHeartBeat2 = new SenMLRecord();
        SenMLRecord senMLRecordHeartBeat3 = new SenMLRecord();

        senMLRecordHeartBeat1.setBn("heart_rate_sensor");
        senMLRecordHeartBeat1.setBt(0);
        senMLRecordHeartBeat1.setBu("beat/min");
        senMLRecordHeartBeat1.setV(104.0);

        senMLRecordHeartBeat2.setT(1644443095109L);
        senMLRecordHeartBeat2.setV(105.0);

        senMLRecordHeartBeat3.setT(1644443096121L);
        senMLRecordHeartBeat3.setV(106.0);

        senMLPackHeartBeat1.add(senMLRecordHeartBeat1);
        senMLPackHeartBeat1.add(senMLRecordHeartBeat2);
        senMLPackHeartBeat1.add(senMLRecordHeartBeat3);

        //------

        //SenMLPack Saturation HealthCheck1
        SenMLPack senMLPackSaturation1= new SenMLPack();
        SenMLRecord senMLRecordSaturation1 = new SenMLRecord();
        SenMLRecord senMLRecordSaturation2 = new SenMLRecord();
        SenMLRecord senMLRecordSaturation3 = new SenMLRecord();

        senMLRecordSaturation1.setBn("saturation_sensor");
        senMLRecordSaturation1.setBt(0);
        senMLRecordSaturation1.setBu("%");
        senMLRecordSaturation1.setV(99.0);

        senMLRecordSaturation2.setT(1644443095109L);
        senMLRecordSaturation2.setV(98.5);

        senMLRecordSaturation3.setT(1644443096121L);
        senMLRecordSaturation3.setV(99.5);

        senMLPackSaturation1.add(senMLRecordSaturation1);
        senMLPackSaturation1.add(senMLRecordSaturation2);
        senMLPackSaturation1.add(senMLRecordSaturation3);

        //------

        //SenMLPack HeartBeat HealthCheck2
        SenMLPack senMLPackHeartBeat2 = new SenMLPack();
        SenMLRecord senMLRecordHeartBeat4 = new SenMLRecord();
        SenMLRecord senMLRecordHeartBeat5 = new SenMLRecord();
        SenMLRecord senMLRecordHeartBeat6 = new SenMLRecord();

        senMLRecordHeartBeat4.setBn("heart_rate_sensor");
        senMLRecordHeartBeat4.setBt(0);
        senMLRecordHeartBeat4.setBu("beat/min");
        senMLRecordHeartBeat4.setV(56.0);

        senMLRecordHeartBeat5.setT(1644443095109L);
        senMLRecordHeartBeat5.setV(54.0);

        senMLRecordHeartBeat6.setT(1644443096121L);
        senMLRecordHeartBeat6.setV(55.0);

        senMLPackHeartBeat2.add(senMLRecordHeartBeat4);
        senMLPackHeartBeat2.add(senMLRecordHeartBeat5);
        senMLPackHeartBeat2.add(senMLRecordHeartBeat6);

        //------

        //SenMLPack Saturation HealthCheck2
        SenMLPack senMLPackSaturation2 = new SenMLPack();
        SenMLRecord senMLRecordSaturation4 = new SenMLRecord();
        SenMLRecord senMLRecordSaturation5 = new SenMLRecord();
        SenMLRecord senMLRecordSaturation6 = new SenMLRecord();

        senMLRecordSaturation4.setBn("saturation_sensor");
        senMLRecordSaturation4.setBt(0);
        senMLRecordSaturation4.setBu("%");
        senMLRecordSaturation4.setV(85.0);

        senMLRecordSaturation5.setT(1644443095109L);
        senMLRecordSaturation5.setV(86.0);

        senMLRecordSaturation6.setT(1644443096121L);
        senMLRecordSaturation6.setV(84.0);

        senMLPackSaturation2.add(senMLRecordSaturation4);
        senMLPackSaturation2.add(senMLRecordSaturation5);
        senMLPackSaturation2.add(senMLRecordSaturation6);


        //SenMLPack HeartBeat HealthCheck3
        SenMLPack senMLPackHeartBeat3 = new SenMLPack();
        SenMLRecord senMLRecordHeartBeat7 = new SenMLRecord();
        SenMLRecord senMLRecordHeartBeat8 = new SenMLRecord();
        SenMLRecord senMLRecordHeartBeat9 = new SenMLRecord();

        senMLRecordHeartBeat7.setBn("heart_rate_sensor");
        senMLRecordHeartBeat7.setBt(0);
        senMLRecordHeartBeat7.setBu("beat/min");
        senMLRecordHeartBeat7.setV(76.0);

        senMLRecordHeartBeat8.setT(1644443095109L);
        senMLRecordHeartBeat8.setV(74.0);

        senMLRecordHeartBeat9.setT(1644443096121L);
        senMLRecordHeartBeat9.setV(75.0);

        senMLPackHeartBeat3.add(senMLRecordHeartBeat7);
        senMLPackHeartBeat3.add(senMLRecordHeartBeat8);
        senMLPackHeartBeat3.add(senMLRecordHeartBeat9);

        //------

        //SenMLPack Saturation HealthCheck3
        SenMLPack senMLPackSaturation3 = new SenMLPack();
        SenMLRecord senMLRecordSaturation7 = new SenMLRecord();
        SenMLRecord senMLRecordSaturation8 = new SenMLRecord();
        SenMLRecord senMLRecordSaturation9 = new SenMLRecord();

        senMLRecordSaturation7.setBn("saturation_sensor");
        senMLRecordSaturation7.setBt(0);
        senMLRecordSaturation7.setBu("%");
        senMLRecordSaturation7.setV(99.0);

        senMLRecordSaturation8.setT(1644443095109L);
        senMLRecordSaturation8.setV(98.0);

        senMLRecordSaturation9.setT(1644443096121L);
        senMLRecordSaturation9.setV(98.5);

        senMLPackSaturation3.add(senMLRecordSaturation7);
        senMLPackSaturation3.add(senMLRecordSaturation8);
        senMLPackSaturation3.add(senMLRecordSaturation9);

        //------

        //HealthCheck1
        HealthCheckDescriptor healthCheckDescriptor = new HealthCheckDescriptor();
        healthCheckDescriptor.setHealthcheckId("74z1e8ba-bb2a-4410-9bac-e23804cce781");
        healthCheckDescriptor.setPatientId("ab4e277b-dff6-4475-9944-82eabd5a93e9");
        healthCheckDescriptor.setDoctorId("doctor00001");
        healthCheckDescriptor.setSmartWatchId("smartWatchId00001");
        healthCheckDescriptor.setFirmwareVersion("v1.0.0.1");
        healthCheckDescriptor.setDateTime(LocalDateTime.of(2021,2,12,15,30,56,128));
        healthCheckDescriptor.setHeartBeatUnit("beat/min");
        healthCheckDescriptor.setSaturationUnit("%");
        healthCheckDescriptor.setGlucoseUnit("mg/l");
        healthCheckDescriptor.setTemperatureUnit("Cel");
        healthCheckDescriptor.setAverageHeartBeat(105.0);
        healthCheckDescriptor.setAverageSaturation(99.0);
        healthCheckDescriptor.setGlucose(8.0);
        healthCheckDescriptor.setTemperature(36.5);
        healthCheckDescriptor.setHeartBeatGraph(senMLPackHeartBeat1);
        healthCheckDescriptor.setSaturationGraph(senMLPackSaturation1);
        healthCheckDescriptor.setAnomalyHeartBeat("tachycardia");

        //HealthCheck2
        HealthCheckDescriptor healthCheckDescriptor2 = new HealthCheckDescriptor();
        healthCheckDescriptor2.setHealthcheckId("916b0556-4afc-4578-b031-a53493ee5c52");
        healthCheckDescriptor2.setPatientId("ab4e277b-dff6-4475-9944-82eabd5a93e9");
        healthCheckDescriptor2.setDoctorId("doctor00001");
        healthCheckDescriptor2.setSmartWatchId("smartWatchId00001");
        healthCheckDescriptor2.setFirmwareVersion("v1.1.0.2");
        healthCheckDescriptor2.setDateTime(LocalDateTime.of(2022,3,30,16,40,46,77));
        healthCheckDescriptor2.setHeartBeatUnit("beat/min");
        healthCheckDescriptor2.setSaturationUnit("%");
        healthCheckDescriptor2.setGlucoseUnit("mg/l");
        healthCheckDescriptor2.setTemperatureUnit("Cel");
        healthCheckDescriptor2.setAverageHeartBeat(55.0);
        healthCheckDescriptor2.setAverageSaturation(85.0);
        healthCheckDescriptor2.setGlucose(7.1);
        healthCheckDescriptor2.setTemperature(36.2);
        healthCheckDescriptor2.setHeartBeatGraph(senMLPackHeartBeat2);
        healthCheckDescriptor2.setSaturationGraph(senMLPackSaturation2);
        healthCheckDescriptor2.setAnomalyHeartBeat("bradycardia");
        healthCheckDescriptor2.setAnomalySaturation("pathological hypoxia");

        //HealthCheck3
        HealthCheckDescriptor healthCheckDescriptor3 = new HealthCheckDescriptor();
        healthCheckDescriptor3.setHealthcheckId("612f504e-b680-457f-9612-2a5fd9e1dbd0");
        healthCheckDescriptor3.setPatientId("218f1737-d7cf-4891-b4f2-ab330613a4bc");
        healthCheckDescriptor3.setDoctorId("doctor00001");
        healthCheckDescriptor3.setSmartWatchId("smartWatchId00001");
        healthCheckDescriptor3.setFirmwareVersion("v1.1.0.2");
        healthCheckDescriptor3.setDateTime(LocalDateTime.of(2022,4,1,9,10,36,100));
        healthCheckDescriptor3.setHeartBeatUnit("beat/min");
        healthCheckDescriptor3.setSaturationUnit("%");
        healthCheckDescriptor3.setGlucoseUnit("mg/l");
        healthCheckDescriptor3.setTemperatureUnit("Cel");
        healthCheckDescriptor3.setAverageHeartBeat(75.0);
        healthCheckDescriptor3.setAverageSaturation(98.5);
        healthCheckDescriptor3.setGlucose(13.2);
        healthCheckDescriptor3.setTemperature(36.0);
        healthCheckDescriptor3.setHeartBeatGraph(senMLPackHeartBeat1);
        healthCheckDescriptor3.setSaturationGraph(senMLPackSaturation2);
        healthCheckDescriptor3.setAnomalyGlucose("diabetes");

        //create HealthChecks
        inventoryDataManager.createNewHealthCheck(healthCheckDescriptor);
        inventoryDataManager.createNewHealthCheck(healthCheckDescriptor2);
        inventoryDataManager.createNewHealthCheck(healthCheckDescriptor3);

    }


}
