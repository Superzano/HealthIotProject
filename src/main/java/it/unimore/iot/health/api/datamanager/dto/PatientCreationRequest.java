package it.unimore.iot.health.api.datamanager.dto;

import it.unimore.iot.health.api.datamanager.model.PatientDescriptor;

/**
 * {@code PatientCreationRequest} is a DTO class (Data Transfer Object) that allows clients to
 * only have access to certain attributes when creating a new patient. It allows them to modify only DTO class' attributes,
 * while the other attributes of the {@code PatientDescriptor} are not accessible
 * (the server replies with 400 Bad Request "Unable to process JSON")
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
public class PatientCreationRequest {

    // Attributes
    /**
     * A String representing the name of the patient
     */
    private String name;
    /**
     * A String representing the surname of the patient
     */
    private String surname;
    /**
     * A String representing the gender of the patient [male, female, N/A]
     */
    private String gender;
    /**
     * A String representing the birthdate of the patient
     */
    private String dateOfBirth;
    /**
     * A String representing the birthplace of the patient
     */
    private String placeOfBirth;
    /**
     * A Long representing the phone number of the patient
     */
    private Long phoneNumber;
    /**
     * A String representing the email of the patient
     */
    private String email;

    // Default Constructor
    public PatientCreationRequest(){
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }


    // Override toString() to print
    @Override
    public String toString() {
        return "PatientCreationRequest{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                '}';
    }


    /**
     * This method is responsible for mapping a new {@code PatientDescriptor} from {@code PatientCreationRequest}.
     *
     * @param patientCreationRequest the request from the client to create a new patient.
     * @return an instance of {@code PatientDescriptor} passing DTO class' attributes to its constructor.
     * @since 1.0
     */
    public PatientDescriptor toDescriptorCreatePatient() {
        return new PatientDescriptor(this.getName(), this.getSurname(),
                                     this.getGender(), this.getDateOfBirth(),
                                     this.getPlaceOfBirth(), this.getEmail(),
                                     this.getPhoneNumber());
    }

}
