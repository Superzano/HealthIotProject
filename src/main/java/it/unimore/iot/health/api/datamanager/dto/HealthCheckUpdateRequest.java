package it.unimore.iot.health.api.datamanager.dto;

import it.unimore.iot.health.api.datamanager.model.HealthCheckDescriptor;

/**
 * {@code HealthCheckUpdateRequest} is a DTO class (Data Transfer Object) that allows clients to
 * only have access to certain attributes when updating a healthceck. It allows them to modify DTO class'
 * attributes, while the other attributes of the {@code HealthCheckDescriptor} are not accessible
 * (the server replies with 400 Bad Request "Unable to process JSON")
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
public class HealthCheckUpdateRequest {

    // Attributes
    /**
     * A String representing healthcheck identifier
     */
    private String healthcheckId;
    /**
     * A String representing doctor identifier
     */
    private String doctorId;
    /**
     * A String representing smartwatch identifier
     */
    private String smartWatchId;

    // Default Constructor
    public HealthCheckUpdateRequest(){
    }

    // Getters and Setters
    public String getHealthcheckId() {
        return healthcheckId;
    }

    public void setHealthcheckId(String healthcheckId) {
        this.healthcheckId = healthcheckId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getSmartWatchId() {
        return smartWatchId;
    }

    public void setSmartWatchId(String smartWatchId) {
        this.smartWatchId = smartWatchId;
    }


    // Override toString() to print
    @Override
    public String toString() {
        return "HealthCheckUpdateRequest{" +
                "healthcheckId='" + healthcheckId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", smartWatchId='" + smartWatchId + '\'' +
                '}';
    }


    /**
     * This method is responsible for mapping a new {@code HealthCheckDescriptor} from {@code HealthCheckUpdateRequest}
     *
     * @param healthCheckUpdateRequest  the request from the client to update a healthcheck.
     * @return  an instance of {@code HealthCheckDescriptor} passing healthcheckId, doctorId and smartWatchId to its constructor.
     * @since 1.0
     */
    public HealthCheckDescriptor toDescriptorUpdateHealthCheck() {
        return new HealthCheckDescriptor(this.getHealthcheckId(),
                this.getDoctorId(),
                this.getSmartWatchId());
    }

}
