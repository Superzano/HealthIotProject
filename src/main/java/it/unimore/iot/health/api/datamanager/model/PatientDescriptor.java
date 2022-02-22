package it.unimore.iot.health.api.datamanager.model;

/**
 * {@code PatientDescriptor} is a classic POJO class that describes the structure of a patient.
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
public class PatientDescriptor {

    // Attributes
    /**
     * A String representing the patient identifier
     */
    private String patientId;
    /**
     * A String representing the patient name
     */
    private String name;
    /**
     * A String representing the patient surname
     */
    private String surname;
    /**
     * A String representing the patient gender [male, female, N/A]
     */
    private String gender;
    /**
     * A String representing the patient birthplace
     */
    private String placeOfBirth;
    /**
     * A String representing the patient birthdate
     */
    private String dateOfBirth;
    /**
     * A String representing the patient phone Number
     */
    private Long phoneNumber;
    /**
     * A String representing the patient email
     */
    private String email;


    // Constructors
    public PatientDescriptor(){
    }

    public PatientDescriptor(String patientId, String email, Long phoneNumber) {
        this.patientId = patientId;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public PatientDescriptor(String name, String surname, String gender, String dateOfBirth, String placeOfBirth, String email, Long phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    //Getters and Setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

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

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    // Override toString() to print
    @Override
    public String toString() {
        return "PatientDescriptor{" +
                "patientId='" + patientId + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                '}';
    }



}
