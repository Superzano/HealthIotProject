package it.unimore.iot.health.api.datamanager.model;

import it.unimore.iot.health.api.datamanager.utils.SenMLPack;
import it.unimore.iot.health.api.datamanager.utils.SenMLRecord;
import it.unimore.iot.health.api.datamanager.utils.UtilityClass;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * {@code HealthCheckDescriptor} is a classic POJO class that describes the structure of a healthcheck.
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
public class HealthCheckDescriptor {

    // Attributes
    /**
     * A String representing a healthcheck identifier
     */
    private String healthcheckId;
    /**
     * A String representing a patient identifier, to which the healthcheck refers
     */
    private String patientId;
    /**
     * A String representing the doctor identifier
     */
    private String doctorId;
    /**
     * A String representing the smartwatch identifier
     */
    private String smartWatchId;
    /**
     * A String representing the smartwatch software version
     */
    private String firmwareVersion;
    /**
     * A String representing the unit of heart rate measurements
     */
    private String heartBeatUnit;
    /**
     * A String representing the unit of saturation measurements
     */
    private String saturationUnit;
    /**
     * A String representing the unit of glucose measurements
     */
    private String glucoseUnit;
    /**
     * A String representing the unit of temperature measurements
     */
    private String temperatureUnit;
    /**
     * A LocalDateTime attribute representing the local date and time
     */
    private LocalDateTime dateTime;
    /**
     * A Double representing the average of all heart rate measurements
     */
    private Double averageHeartBeat;
    /**
     * A Double representing the level of glucose in the patient's blood sample
     */
    private Double glucose;
    /**
     * A Double representing the average of all saturation measurements
     */
    private Double averageSaturation;
    /**
     * A Double representing the patient's temperature
     */
    private Double temperature;
    /**
     * An ArrayList of senMLRecords representing all the heart rate measurements
     */
    private ArrayList<SenMLRecord> heartBeatGraph;
    /**
     * An ArrayList of senMLRecords representing all the saturation measurements
     */
    private ArrayList<SenMLRecord> saturationGraph;
    /**
     * A String representing a heart rate anomaly
     */
    private String anomalyHeartBeat;
    /**
     * A String representing a saturation anomaly
     */
    private String anomalySaturation;
    /**
     * A String representing a glucose anomaly
     */
    private String anomalyGlucose;
    /**
     * A String representing a temperature anomaly
     */
    private String anomalyTemperature;


    // Constructors
    public HealthCheckDescriptor(){
    }

    public HealthCheckDescriptor(String patientId, String doctorId) {
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public HealthCheckDescriptor(String healthcheckId, String doctorId, String smartWatchId) {
        this.healthcheckId = healthcheckId;
        this.doctorId = doctorId;
        this.smartWatchId = smartWatchId;
    }


    // Getters and Setters
    public String getHealthcheckId() {
        return healthcheckId;
    }

    public void setHealthcheckId(String healthcheckId) {
        this.healthcheckId = healthcheckId;
    }

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

    public Double getAverageHeartBeat() {
        return averageHeartBeat;
    }

    public void setAverageHeartBeat(Double averageHeartBeat) {
        this.averageHeartBeat = averageHeartBeat;
    }

    public Double getGlucose() {
        return glucose;
    }

    public void setGlucose(Double glucose) {
        this.glucose = glucose;
    }

    public Double getAverageSaturation() {
        return averageSaturation;
    }

    public void setAverageSaturation(Double averageSaturation) {
        this.averageSaturation = averageSaturation;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public ArrayList<SenMLRecord> getHeartBeatGraph() {
        return heartBeatGraph;
    }

    public void setHeartBeatGraph(ArrayList<SenMLRecord> heartBeatGraph) {
        this.heartBeatGraph = heartBeatGraph;
    }

    public ArrayList<SenMLRecord> getSaturationGraph() {
        return saturationGraph;
    }

    public void setSaturationGraph(ArrayList<SenMLRecord> saturationGraph) {
        this.saturationGraph = saturationGraph;
    }

    public String getAnomalyHeartBeat() {
        return anomalyHeartBeat;
    }

    public void setAnomalyHeartBeat(String anomalyHeartBeat) {
        this.anomalyHeartBeat = anomalyHeartBeat;
    }

    public String getAnomalySaturation() {
        return anomalySaturation;
    }

    public void setAnomalySaturation(String anomalySaturation) {
        this.anomalySaturation = anomalySaturation;
    }

    public String getAnomalyGlucose() {
        return anomalyGlucose;
    }

    public void setAnomalyGlucose(String anomalyGlucose) {
        this.anomalyGlucose = anomalyGlucose;
    }

    public String getFever() {
        return anomalyTemperature;
    }

    public void setFever(String fever) {
        this.anomalyTemperature = fever;
    }

    public String getSmartWatchId() {
        return smartWatchId;
    }

    public void setSmartWatchId(String smartWatchId) {
        this.smartWatchId = smartWatchId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getHeartBeatUnit() {
        return heartBeatUnit;
    }

    public void setHeartBeatUnit(String heartBeatUnit) {
        this.heartBeatUnit = heartBeatUnit;
    }

    public String getSaturationUnit() {
        return saturationUnit;
    }

    public void setSaturationUnit(String saturationUnit) {
        this.saturationUnit = saturationUnit;
    }

    public String getGlucoseUnit() {
        return glucoseUnit;
    }

    public void setGlucoseUnit(String glucoseUnit) {
        this.glucoseUnit = glucoseUnit;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }


    // Override toString() to print
    @Override
    public String toString() {
        return "HealthCheckDescriptor{" +
                "healthcheckId='" + healthcheckId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", smartWatchId='" + smartWatchId + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", heartBeatUnit='" + heartBeatUnit + '\'' +
                ", saturationUnit='" + saturationUnit + '\'' +
                ", glucoseUnit='" + glucoseUnit + '\'' +
                ", temperatureUnit='" + temperatureUnit + '\'' +
                ", dateTime=" + dateTime +
                ", averageHeartBeat=" + averageHeartBeat +
                ", glucose=" + glucose +
                ", averageSaturation=" + averageSaturation +
                ", temperature=" + temperature +
                ", heartBeatGraph=" + heartBeatGraph +
                ", saturationGraph=" + saturationGraph +
                ", anomalyHeartBeat='" + anomalyHeartBeat + '\'' +
                ", anomalySaturation='" + anomalySaturation + '\'' +
                ", anomalyGlucose='" + anomalyGlucose + '\'' +
                ", fever='" + anomalyTemperature + '\'' +
                '}';
    }

    // Methods
    /**
     * This method allows mapping a possible anomalous heart rate, based on the average value of the measured data
     */
    public void findAnomalyHeartBeat(){

        String ANOMALY_HEART_BEAT_TACHYCARDIA = "tachycardia";
        String ANOMALY_HEART_BEAT_BRADYCARDIA = "bradycardia";

        if(this.averageHeartBeat < 60.0)
            this.anomalyHeartBeat = ANOMALY_HEART_BEAT_BRADYCARDIA;
        else if(this.averageHeartBeat > 100)
            this.anomalyHeartBeat = ANOMALY_HEART_BEAT_TACHYCARDIA;
        else
            this.anomalyHeartBeat = null;
    }

    /**
     * This method allows mapping a possible anomalous saturation, based on the average value of the measured data
     */
    public void findAnomalySaturation(){

        String ANOMALY_SATURATION_MILD_HYPOXIA = "mild_hypoxia";
        String ANOMALY_SATURATION_PATHOLOGICAL_HYPOXIA = "pathological_hypoxia";

        if(this.averageSaturation > 90.0 && this.averageSaturation <= 95.0)
            this.anomalySaturation = ANOMALY_SATURATION_MILD_HYPOXIA;
        else if(this.averageSaturation >= 70 && this.averageSaturation <= 90.0)
            this.anomalySaturation = ANOMALY_SATURATION_PATHOLOGICAL_HYPOXIA;
        else
            this.anomalySaturation = null;
    }

    /**
     * This method allows mapping a possible anomalous glucose, based on the value of the measured data
     */
    public void findAnomalyGlucose(){

        String ANOMALY_GLUCOSE_PRE_DIABETES = "pre_diabetes";
        String ANOMALY_GLUCOSE_DIABETES = "diabetes";

        if(this.glucose > 10.0 && this.glucose < 12.5)
            this.anomalyGlucose = ANOMALY_GLUCOSE_PRE_DIABETES;
        else if(glucose >= 12.5)
            this.anomalyGlucose = ANOMALY_GLUCOSE_DIABETES;
        else
            this.anomalyGlucose = null;
    }

    /**
     * This method allows mapping a possible anomalous temperature, based on the value of the measured data
     */
    public void findAnomalyTemperature(){

        String ANOMALY_LOW_FEVER = "low_fever";
        String ANOMALY_HIGH_FEVER = "high_fever";

        if(this.temperature > 37.0 && this.temperature < 38.0)
            this.anomalyTemperature = ANOMALY_LOW_FEVER;
        else if(temperature >= 38.0)
            this.anomalyTemperature = ANOMALY_HIGH_FEVER;
        else
            this.anomalyTemperature = null;
    }

    /**
     * This method allows mapping the telemetry SenMLRecords' values and fields related to heart rate
     * to the HealthCheckDescriptor
     *
     * @param senMLPack it is the List of SenMLRecords from the telemetry
     */
    public void mapHeartRateData(SenMLPack senMLPack) {

        // Calculate the sum of heart rates from measurements
        double sum = 0;
        for(SenMLRecord senMLRecord: senMLPack){
            sum += senMLRecord.getV();
        }

        // Map the average
        this.averageHeartBeat = sum / senMLPack.size();

        // Map the unit
        this.heartBeatUnit = senMLPack.get(0).getBu();

        // Map the ArrayList of SenMLRecords
        this.heartBeatGraph = UtilityClass.modifySenMLPack(senMLPack);

    }

    /**
     * This method allows mapping the telemetry SenMLRecords' values and fields related to saturation
     * to the HealthCheckDescriptor
     *
     * @param senMLPack it is the List of SenMLRecords from the telemetry
     */
    public void mapSaturationData(SenMLPack senMLPack) {

        // Calculate the sum of saturation values from measurements
        double sum = 0;
        for(SenMLRecord senMLRecord: senMLPack){
            sum += senMLRecord.getV();
        }

        // Map the average
        this.averageSaturation= sum / senMLPack.size();

        // Map the unit
        this.saturationUnit = senMLPack.get(0).getBu();

        // Map the ArrayList of SenMLRecords
        this.saturationGraph = UtilityClass.modifySenMLPack(senMLPack);

    }

    /**
     * This method allows mapping the telemetry SenMLRecords' values and fields related to glucose
     * to the HealthCheckDescriptor
     *
     * @param senMLPack it is the List of SenMLRecords from the telemetry
     */
    public void mapGlucoseData(SenMLPack senMLPack) {

        // Map the value
        this.glucose = senMLPack.get(0).getV();

        // Map the unit
        this.glucoseUnit = senMLPack.get(0).getBu();

    }

    /**
     * This method allows mapping the telemetry SenMLRecords' values and fields related to temperature
     * to the HealthCheckDescriptor
     *
     * @param senMLPack it is the List of SenMLRecords from the telemetry
     */
    public void mapTemperatureData(SenMLPack senMLPack) {

        // Map the value
        this.temperature = senMLPack.get(0).getV();

        // Map the unit
        this.temperatureUnit = senMLPack.get(0).getBu();
    }

}
