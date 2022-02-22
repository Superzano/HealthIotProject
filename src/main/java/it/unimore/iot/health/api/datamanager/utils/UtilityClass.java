package it.unimore.iot.health.api.datamanager.utils;

import it.unimore.iot.health.api.datamanager.model.HealthCheckDescriptor;
import it.unimore.iot.health.api.datamanager.model.PatientDescriptor;

import java.lang.reflect.Field;

/**
 * {@code UtilityClass} is a helper class with only static methods that are useful in certain part of the
 * codebase. This kind of class does not need to be instantiated to get access to its methods.
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
public final class UtilityClass {

    private UtilityClass(){
        throw new UnsupportedOperationException("This utility class cannot be instantiated!");
    }

    /**
     * This method allows taking as input the {@code SenMLPack} containing the telemetry data and remap it on a new
     * {@code senMLPack} with a different management of base fields and regular fields. For example, this method maps in
     * the bn (base name) the name field of the {@code SenMLRecord} contained inside the {@code SenMLPack}, as the device ID is
     * contained in a special field of the {@code HealthCheckDescriptor}.
     *
     * @param senMLPack it is the ArrayList of SenMLRecord related to telemetry data
     * @return a new mapped SenMLPack with respect to its fields
     * @since 1.0
     */
    public static SenMLPack modifySenMLPack(SenMLPack senMLPack){

        // Create a new instance of SenMLPack
        SenMLPack newSenMLPack = new SenMLPack();

        // Create new instances of SenMLRecords (as many as the old SenMLPack)
        for(SenMLRecord senMLRecord : senMLPack){

            SenMLRecord newSenMLRecord = new SenMLRecord();

            // Set the base fields and regular fields as desired
            // If SenMLPack is empty that means we need to fill the base fields of the first SenMLRecord
            if(newSenMLPack.isEmpty()){
                newSenMLRecord.setBt(senMLRecord.getBt());
                newSenMLRecord.setBu(senMLRecord.getBu());
                newSenMLRecord.setBn(senMLRecord.getN());
            }else
                newSenMLRecord.setT(senMLRecord.getT());

            // Value (regula field) must be set regardless of whether it is the first senML record or not
            newSenMLRecord.setV(senMLRecord.getV());

            // Finally, add the SenMLRecord to the ArrayList (SenMLPack)
            newSenMLPack.add(newSenMLRecord);

        }

        return newSenMLPack;
    }

    /**
     * This method is responsible for making the copy of the non-null values of the attributes present inside the source
     * {@code PatientDescriptor} (which contains all the null attributes except those inserted by the client during the patient PUT request)
     * on a destination {@code PatientDescriptor}.
     *
     * <p> Without this algorithm any fields not specified in the PUT phase would be set to null. </p>
     *
     * @param destination   it represents the PatientDescriptor object {@code on} which to map the non-null values of the attributes coming from the update request by the client.
     * @param source    it represents the PatientDescriptor object {@code from} which to map the non-null values of its attributes to {@code destination}.
     * @return  the destination PatientDescriptor updated
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static PatientDescriptor copyDifferences(PatientDescriptor destination, PatientDescriptor source) throws IllegalAccessException, NoSuchFieldException {

        // Iterate all the fields of the destination class and get every name and every respective value
        for (Field field : destination.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(source);

            // Then map the value from the source, with respect to a certain destination name, to the destination
            if (value != null) {
                Field destField = destination.getClass().getDeclaredField(name);
                destField.setAccessible(true);
                destField.set(destination, value);
            }
        }
        // At this point the destination PatientDescriptor is correctly updated with non-null values from the source
        return destination;
    }

    /**
     * This method is responsible for making the copy of the non-null values of the attributes present inside the source
     * {@code HealthCheckDescriptor} (which contains all the null attributes except those inserted by the client during the healthcheck PUT request)
     * on a destination {@code HealthCheckDescriptor}.
     *
     * <p> Without this algorithm any fields not specified in the PUT phase would be set to null. </p>
     *
     * @param destination   it represents the HealthCheckDescriptor object {@code on} which to map the non-null values of the attributes coming from the update request by the client.
     * @param source    it represents the HealthCheckDescriptor object {@code from} which to map the non-null values of its attributes to {@code destination}.
     * @return  the destination HealthCheckDescriptor updated
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static HealthCheckDescriptor copyDifferences(HealthCheckDescriptor destination, HealthCheckDescriptor source) throws IllegalAccessException, NoSuchFieldException {

        // Iterate all the fields of the destination class and get every name and every respective value
        for (Field field : destination.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(source);

            // Then map the value from the source, with respect to a certain destination name, to the destination
            if (value != null) {
                Field destField = destination.getClass().getDeclaredField(name);
                destField.setAccessible(true);
                destField.set(destination, value);
            }
        }
        // At this point the destination HealthCheckDescriptor is correctly updated with non-null values from the source
        return destination;
    }

}
