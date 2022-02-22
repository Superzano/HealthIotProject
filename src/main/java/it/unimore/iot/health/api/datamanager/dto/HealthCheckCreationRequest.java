package it.unimore.iot.health.api.datamanager.dto;

import it.unimore.iot.health.api.datamanager.model.HealthCheckDescriptor;

/**
 * {@code HealthCheckCreationRequest} is a DTO class (Data Transfer Object) that allows clients to
 * only have access to certain attributes when creating a new healthceck. It allows them to modify only patientId
 * and doctorId, while the other attributes of the {@code HealthCheckDescriptor} are not accessible
 * (the server replies with 400 Bad Request "Unable to process JSON")
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
public class HealthCheckCreationRequest {

    // Attributes
    /**
     * A String representing patient identifier
     */
    private String patientId;
    /**
     * A String representing doctor identifier
     */
    private String doctorId;


    // Constructor
    public HealthCheckCreationRequest(){
    }


    // Getters and Setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }


    // Override toString() to print
    @Override
    public String toString() {
        return "HealthCheckCreationRequest{" +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                '}';
    }


    /**
     * This method is responsible for mapping a new {@code HealthCheckDescriptor} from {@code HealthCheckCreationRequest}.
     *
     * @param healthCheckCreationRequest    the request from the client to create a new healthcheck.
     * @return  an instance of {@code HealthCheckDescriptor} passing patientId and doctorId to its constructor.
     * @since 1.0
     */
    public HealthCheckDescriptor toDescriptorCreateHealthCheck() {
        return new HealthCheckDescriptor(this.getPatientId(), this.getDoctorId());
    }

}
