package it.unimore.iot.health.api.datamanager.persistence;

import java.util.List;
import java.util.Optional;
import it.unimore.iot.health.api.datamanager.exception.DataManagerException;
import it.unimore.iot.health.api.datamanager.model.HealthCheckDescriptor;
import it.unimore.iot.health.api.datamanager.model.PatientDescriptor;

/**
 * {@code IInventoryDataManager} is an interface that is implemented in this project by {@link InventoryDataManager}.
 * This interface provides a series of methods in order to manage the inventory of both patients and healtchchecks.
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
public interface IInventoryDataManager {

    //Patient
    /**
     * This method allows to obtain the list of all patients that have been registered.
     */
    List<PatientDescriptor> getPatientList() ;

    /**
     * This method allows to get a specific patient, identified by its id passed as input.
     *
     * @param patientId is a {@code String} representing the patient identifier.
     */
    Optional<PatientDescriptor> getPatientById(String patientId) throws DataManagerException;

    /**
     * This method allows to create a new patient using patientDescriptor passed as input.
     *
     * @param patientDescriptor is the object to be created.
     */
    PatientDescriptor createNewPatient(PatientDescriptor patientDescriptor) throws DataManagerException;

    /**
     * This method allows to update a specific patient's data passing a PatientDescriptor.
     *
     * @param patientDescriptor it is the patientDescriptor with new incoming values
     */
    void updatePatient(PatientDescriptor patientDescriptor) throws DataManagerException, IllegalAccessException, NoSuchFieldException;

    /**
     * This method allows to delete a patient using the patientId as input.
     *
     * @param patientId it is a {@code String} representing the patient identifier.
     */
    void deletePatient(String patientId) throws DataManagerException;


    //HealthCheck
    /**
     * This method allows to obtain the list of all healthcheacks that have been created.
     */
    List<HealthCheckDescriptor> getHealthCheckList() ;

    /**
     * This method allows to get a specific healthcheck, identified by its id passed as input.
     *
     * @param healthCheckId is a {@code String} representing the healthcheck identifier.
     */
    Optional<HealthCheckDescriptor> getHealthCheck(String healthCheckId) throws DataManagerException;

    /**
     * This method allows to retrieve a filtered healthcheck list.
     *
     * @param patientId it is a string representing the patient identifier.
     * @param anomaly it is a string representing the type of measurement whose anomalies are to be found.
     */
    List<HealthCheckDescriptor> getHealthCheckByFilter(String patientId, String anomaly) throws DataManagerException;

    /**
     * This method allows to create a new healthcheck using healthCheckDescriptor passed as input.
     *
     * @param healthCheckDescriptor is the object to be created.
     */
    HealthCheckDescriptor createNewHealthCheck(HealthCheckDescriptor healthCheckDescriptor) throws DataManagerException;

    /**
     * This method allows to update a specific healthcheck's data passing a HealthCheckDescriptor.
     *
     * @param healthCheckDescriptor it is the healthCheckDescriptor with new incoming values.
     */
    void updateHealthCheck(HealthCheckDescriptor healthCheckDescriptor) throws DataManagerException, IllegalAccessException, NoSuchFieldException;

    /**
     * This method allows to delete a healthcheck using the healthCheckId as input.
     *
     * @param healthCheckId it is a {@code String} representing the healthcheck identifier.
     */
    void deleteHealthCheck(String healthCheckId) throws DataManagerException;

}
