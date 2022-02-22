package it.unimore.iot.health.api.datamanager.dto;

import it.unimore.iot.health.api.datamanager.model.PatientDescriptor;

/**
 * {@code PatientUpdateRequest} is a DTO class (Data Transfer Object) that allows clients to
 * only have access to certain attributes when updating a patient. It allows them to modify DTO class'
 * attributes, while the other attributes of the {@code PatientDescriptor} are not accessible
 * (the server replies with 400 Bad Request "Unable to process JSON")
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
public class PatientUpdateRequest {

    // Attributes
    /**
     * A String representing patient identifier
     */
    private String patientId;
    /**
     * A Long representing patient's phone number
     */
    private Long phoneNumber;
    /**
     * A String representing patient's email
     */
    private String email;

    // Default Constructor
    public PatientUpdateRequest(){
    }

    // Getters and Setters
    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    // Override toString() to print
    @Override
    public String toString() {
        return "PatientUpdateRequest{" +
                "patientId='" + patientId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    /**
     * This method is responsible for mapping a new {@code PatientDescriptor} from {@code PatientUpdateRequest}
     *
     * @param patientUpdateRequest the request from the client to update a patient.
     * @return an instance of {@code PatientDescriptor} passing patientId, phoneNumber and email to its constructor.
     * @since 1.0
     */
    public PatientDescriptor toDescriptorUpdatePatient() {

        return new PatientDescriptor(this.getPatientId(), this.getEmail(), this.getPhoneNumber());
    }

}