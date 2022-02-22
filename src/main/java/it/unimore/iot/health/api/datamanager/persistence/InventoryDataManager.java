package it.unimore.iot.health.api.datamanager.persistence;

import it.unimore.iot.health.api.datamanager.exception.DataManagerException;
import it.unimore.iot.health.api.datamanager.model.HealthCheckDescriptor;
import it.unimore.iot.health.api.datamanager.model.PatientDescriptor;
import it.unimore.iot.health.api.datamanager.utils.UtilityClass;

import java.util.*;
import java.util.stream.Collectors;

/**
 * {@code InventoryDataManager} is a class implementation of {@code IInventoryDataManager} interface. This class
 * provides all the necessary methods to handle PatientDescriptor objects and HealthCheckDescriptor objects saved
 * in the RAM. Therefore, this class implements the required persistence to correctly support CRUD interactions at
 * API RESTful level
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
public class InventoryDataManager implements IInventoryDataManager {

    // String used to correctly implement the GetHealthCheckWithFilter interaction
    private static final String HEART_BEAT_QUERY = "heartRate";
    private static final String SATURATION_QUERY = "saturation";
    private static final String GLUCOSE_QUERY = "glucose";
    private static final String TEMPERATURE_QUERY = "temperature";

    // Data structures to save patients' data and healthchecks' data
    private final HashMap<String, PatientDescriptor> patientMap;
    private final HashMap<String, HealthCheckDescriptor> healthCheckMap;

    // Constructor
    public InventoryDataManager() {
        this.patientMap = new HashMap<>();
        this.healthCheckMap = new HashMap<>();
    }

    //Override methods -- Patient
    /**
     * This method allows to obtain the list of all patients (with relative data) that have been registered.
     *
     * @return a new ArrayList containing all the PatientDescriptors saved in the patientMap at the moment its call.
     * @since 1.0
     */
    @Override
    public List<PatientDescriptor> getPatientList() {
        return new ArrayList<>(this.patientMap.values());
    }

    /**
     * This method allows to get a specific patient, identified by its id passed as input.
     *
     * @param patientId is a {@code String} representing the patient identifier, used to match in the {@code patientMap} the
     *                  correct {@code PatientDescriptor} to return.
     * @return A container object which may or may not contain a non-null (PatientDescriptor) value.
     * If a value is present, isPresent() will return true and get() will return the value.
     * @since 1.0
     */
    @Override
    public Optional<PatientDescriptor> getPatientById(String patientId) {
        return Optional.ofNullable(this.patientMap.get(patientId));
    }

    /**
     * This method allows to create a new patient in the patientMap, exploiting the patientDescriptor passed as input.
     *
     * @param patientDescriptor is the descriptor of the patient to create in the patientMap.
     * @return the patientDescriptor created.
     * @throws DataManagerException if the patientDescriptor provided cannot be created.
     * @since 1.0
     */
    @Override
    public PatientDescriptor createNewPatient(PatientDescriptor patientDescriptor) throws DataManagerException {

        // Check if incoming values are correct
        if(patientDescriptor != null && patientDescriptor.getEmail() != null){

            // Define UUID (thanks to DTO PatientCreationRequest a client cannot specify an id, that is completely determined by the server)
            if(patientDescriptor.getPatientId() == null)
                patientDescriptor.setPatientId(UUID.randomUUID().toString());

            // Add the new patient to the PatientMap
            this.patientMap.put(patientDescriptor.getPatientId(), patientDescriptor);

            return patientDescriptor;
        }
        else
            throw new DataManagerException("Wrong parameters: cannot create a new patient");
    }

    /**
     * This method allows to update a specific patient's data passing a new PatientDescriptor during the PUT interaction.
     *
     * @param patientDescriptorSource is the PatientDescriptor object coming from the PUT, whose non-null fields
     *                                must override the PatientDescriptorDestination's fields already present in the
     *                                patientMap.
     * @throws IllegalAccessException exception thrown with respect to {@code copyDifferences()} method.
     * @throws NoSuchFieldException exception thrown with respect to {@code copyDifferences()} method
     * @since 1.0
     */
    @Override
    public void updatePatient(PatientDescriptor patientDescriptorSource) throws IllegalAccessException, NoSuchFieldException {

        // Get the patientDescriptor to update from the patientMap
        Optional<PatientDescriptor> patientDescriptorDestination = getPatientById(patientDescriptorSource.getPatientId());

        // Check if the patientDescriptor to update is effectively present in the patientMap
        if(patientDescriptorDestination.isPresent()){

            // Copy non-null values from patientDescriptorSource to patientDescriptorDestination
            PatientDescriptor newDestination = UtilityClass.copyDifferences(patientDescriptorDestination.get(), patientDescriptorSource);

            // Then replace the old patientDescriptor with the updated one
            this.patientMap.put(newDestination.getPatientId(), newDestination);
        }

    }

    /**
     * This method allows to delete a patient using the patientId as input to match the correct patient to delete
     * in the patientMap.
     *
     * @param patientId is a String representing the identifier of the patient to delete.
     * @since 1.0
     */
    @Override
    public void deletePatient(String patientId) {
        this.patientMap.remove(patientId);
    }


    //Override methods -- HealthCheck
    /**
     * This method allows to obtain the list of all healthchecks (with relative data) that have been created.
     *
     * @return a new ArrayList containing all the HealthCheckDescriptors saved in the patientMap at the moment its call.
     */
    @Override
    public List<HealthCheckDescriptor> getHealthCheckList() {
        return new ArrayList<>(this.healthCheckMap.values());
    }

    /**
     * This method allows to get a specific healthcheck, identified by its id passed as input.
     *
     * @param healthCheckId is a {@code String} representing the healthcheck identifier, used to match
     *                      in the {@code healthCheckMap} the correct {@code healthCheckDescriptor} to return.
     * @return A container object which may or may not contain a non-null (HealthCheckDescriptor) value.
     *       If a value is present, isPresent() will return true and get() will return the value.
     * @since 1.0
     */
    @Override
    public Optional<HealthCheckDescriptor> getHealthCheck(String healthCheckId) {
        return Optional.ofNullable(this.healthCheckMap.get(healthCheckId));
    }

    /**
     * This method allows to retrieve the healthcheck list according to some filtering criteria:
     * <ol>
     *   <li> If {@code anomaly} is provided as query parameter then they must necessarily also provide patientId as
     *   query parameter and the final list will contain all the healthchecks of a particular patient that
     *   are anomalous in one of the following aspects: [heart rate, saturation, glucose, temperature]  </li>
     *   <li> If {@code anomaly} is not provided (null), then a list of healthchecks of a particular patient
     *   is returned, regardless of the possible anomalies </li>
     * </ol>
     *
     *
     * @param patientId a String representing the patient identifier.
     * @param anomaly a pre-configured String representing the macro-type of anomaly a client needs to retrieve.
     * @return a List of filtered healthchecks.
     * @throws DataManagerException if the anomaly String provided by the client does not match the pre-configured query strings
     * @since 1.0
     */
    @Override
    public List<HealthCheckDescriptor> getHealthCheckByFilter(String patientId, String anomaly) throws DataManagerException{

        // Instantiate the result list getting all the healthchecks
        List<HealthCheckDescriptor> resultList = this.getHealthCheckList();

        if(patientId != null)
            // Maintaining into the list only the healthcecks related to patientId
            resultList = this.healthCheckMap.values().stream()
                    .filter(healthCheckDescriptor -> healthCheckDescriptor != null && healthCheckDescriptor.getPatientId().equals(patientId))
                    .collect(Collectors.toList());

        if(anomaly != null)
            //Maintaining into the list only the healthchecks with anomalies relating to the anomaly string provided
            resultList = switch (anomaly) {
                case HEART_BEAT_QUERY -> resultList.stream()
                        .filter(healthCheckDescriptor -> healthCheckDescriptor != null && healthCheckDescriptor.getAnomalyHeartBeat() != null)
                        .collect(Collectors.toList());
                case SATURATION_QUERY -> resultList.stream()
                        .filter(healthCheckDescriptor -> healthCheckDescriptor != null && healthCheckDescriptor.getAnomalySaturation() != null)
                        .collect(Collectors.toList());
                case GLUCOSE_QUERY -> resultList.stream()
                        .filter(healthCheckDescriptor -> healthCheckDescriptor != null && healthCheckDescriptor.getAnomalyGlucose() != null)
                        .collect(Collectors.toList());
                case TEMPERATURE_QUERY -> resultList.stream()
                        .filter(healthCheckDescriptor -> healthCheckDescriptor != null && healthCheckDescriptor.getFever() != null)
                        .collect(Collectors.toList());
                default -> throw new DataManagerException("Wrong query string for anomaly");
            };

        return resultList;

    }

    /**
     * This method creates a new healthcheck in the healthCheckMap, given a HealthCheckDescriptor as input.
     *
     * @param healthCheckDescriptor it is the {@code HealthCheckDescriptor} to be created.
     * @return the {@code HealthCheckDescriptor} created.
     * @throws DataManagerException if the healthCheckDescriptor provided cannot be created.
     */
    @Override
    public HealthCheckDescriptor createNewHealthCheck(HealthCheckDescriptor healthCheckDescriptor) throws DataManagerException {

        // Check if incoming values are correct
        if(healthCheckDescriptor != null &&
                healthCheckDescriptor.getPatientId() != null &&
                healthCheckDescriptor.getDoctorId() != null){

            // Define UUID, completely determined by the server
            if(healthCheckDescriptor.getHealthcheckId() == null)
                healthCheckDescriptor.setHealthcheckId(UUID.randomUUID().toString());

            // Add the new HealthCheck to the HealthCheckMap
            this.healthCheckMap.put(healthCheckDescriptor.getHealthcheckId(), healthCheckDescriptor);

            return healthCheckDescriptor;
        }
        else
            throw new DataManagerException("Wrong parameters");
    }

    /**
     * This method allows to update a specific patient's data passing a new PatientDescriptor during the PUT interaction.
     *
     * @param healthCheckDescriptorSource is the HealthCheckDescriptor object coming from the PUT, whose non-null fields
     *                                    must override the HealthCheckDescriptorDestination's fields already present in the
     *                                   healthCheckMap.
     * @throws IllegalAccessException exception thrown with respect to {@code copyDifferences()} method.
     * @throws NoSuchFieldException exception thrown with respect to {@code copyDifferences()} method.
     * @since 1.0
     */
    @Override
    public void updateHealthCheck(HealthCheckDescriptor healthCheckDescriptorSource) throws IllegalAccessException, NoSuchFieldException {

        // Get the healthCheckDescriptor to update from the healthCheckMap
        Optional<HealthCheckDescriptor> healthCheckDescriptorDestination = getHealthCheck(healthCheckDescriptorSource.getHealthcheckId());

        // Check if the healthCheckDescriptor to update is effectively present in the healthCheckMap
        if(healthCheckDescriptorDestination.isPresent()){

            // Copy non-null values from healthCheckDescriptorSource to healthCheckDescriptorDestination
            HealthCheckDescriptor newDestination = UtilityClass.copyDifferences(healthCheckDescriptorDestination.get(),
                                                                                healthCheckDescriptorSource);

            // Then replace the old healthCheckDescriptor with the updated one
            this.healthCheckMap.put(newDestination.getHealthcheckId(), newDestination);
        }

    }

    /**
     * This method allows to delete a healthcheck using the healthCheckId as input to match the correct healthcheck to delete
     *      in the healthCheckMap.
     *
     * @param healthCheckId is a String representing the identifier of the healthcheck to delete.
     * @since 1.0
     */
    @Override
    public void deleteHealthCheck(String healthCheckId) {
        this.healthCheckMap.remove(healthCheckId);
    }


}

