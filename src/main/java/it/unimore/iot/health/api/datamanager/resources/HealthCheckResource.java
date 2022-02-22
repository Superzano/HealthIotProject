package it.unimore.iot.health.api.datamanager.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.jersey.errors.ErrorMessage;
import it.unimore.iot.health.api.datamanager.communication.MqttHandler;
import it.unimore.iot.health.api.datamanager.dto.HealthCheckCreationRequest;
import it.unimore.iot.health.api.datamanager.dto.HealthCheckReadRequest;
import it.unimore.iot.health.api.datamanager.dto.HealthCheckUpdateRequest;
import it.unimore.iot.health.api.datamanager.model.HealthCheckDescriptor;
import it.unimore.iot.health.api.datamanager.services.AppConfig;
import it.unimore.iot.health.api.datamanager.utils.SenMLPack;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * {@code HealthCheckResource} class contains a series of methods necessary for the correct management
 * of incoming requests by a client, in accordance with the CRUD manipulation of the healthcheck resource.
 * Therefore, any request sent by a client related to the Healthcheck resource is processed here, and this is where
 * all HTTP response codes come into play.
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
@Path("/api/iot/inventory/healthcheck")
public class HealthCheckResource {

    // Constant Strings to retrieve the correct SenMLPack to map to the HealthCheckDescriptor
    private static final String TEMPERATURE_KEY = "temperature_sensor";
    private static final String HEART_RATE_KEY = "heart_rate_sensor";
    private static final String SATURATION_KEY = "saturation_sensor";
    private static final String GLUCOSE_KEY = "glucose_sensor";

    // Utils
    final protected Logger logger = LoggerFactory.getLogger(HealthCheckResource.class);
    final AppConfig conf;

    // Constructor
    public HealthCheckResource(AppConfig conf) {
        this.conf = conf;
    }

    /**
     * This method allows managing and responding to a GET request from a client,
     * who wants to obtain the list of registered healthcehks.
     *
     * @param req Dropwizard maps the incoming request into an object, which can be used within our method
     * @param patientId it is a String representing the UUID of a patient, as @QueryParam
     * @param anomaly it is a String representing a type of measurement of which to find anomalous healthchecks,
     *                as @QueryParam
     * @return
     * <ol>
     *     <li>
     *         If both patientId and anomaly are not present then serviceList will contain ALL registered healthchecks.
     *         Else if the patientId is provided and anomaly are provided then serviceList will contain
     *         the healthcehcks for a particular patient that are anomalous in the type of measurements specified
     *         by the anomaly String.
     *         The sole anomaly String is not allowed, so if there is no patientId provided (together with the anomaly)
     *         then a 400 BadRequest response is sent to the client
     *     <li> If after loading the healthcheck list from the specific data structure, it is empty,
     *          then it responds with a {@code 404 Not Found} and an informational body serialized in JSON </li>
     *     <li> If, on the other hand, the list contains at least one healthchecks, it responds
     *          with {@code 200 OK} + serialized payload in JSON </li>
     *      <li> If for any reason an exception is thrown, it is captured and the server responds
     *           with {@code 500 Internal Server Error } and an informational body serialized in JSON </li>
     *
     * </ol>
     * @since 1.0
     */
    @GET
    @Path("/")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHealthChecks(@Context ContainerRequestContext req,
                                    @QueryParam("patientId") String patientId,
                                    @QueryParam("anomaly") String anomaly) {

        try {

            logger.info("Loading all stored IoT Inventory HealthChecks filtered by Anomaly: {} and PatientId: {}", anomaly, patientId);

            // List that will host our healthchecks
            List<HealthCheckDescriptor> serviceList;

            // No filter applied
            if(anomaly == null && patientId == null)
                serviceList =  this.conf.getInventoryDataManager().getHealthCheckList();

            // Filer applied
            else if(patientId != null)
                serviceList = this.conf.getInventoryDataManager().getHealthCheckByFilter(patientId, anomaly);

            // If anomaly is present then patientId must be present as well, otherwise a 400 Bad Request is sent
            else
                return Response.status(Response.Status.BAD_REQUEST).
                        type(MediaType.APPLICATION_JSON_TYPE).
                        entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(),"You must provide either <patientId> or <patientId> & <anomaly> for filtering!")).
                        build();

            // If serviceList is empty then that means that no healthchecks have been found, so a 404 Not Found is sent
            if(serviceList.isEmpty())
                return Response.status(Response.Status.NOT_FOUND).
                        type(MediaType.APPLICATION_JSON_TYPE).
                        entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),"HealthChecks Not Found !")).
                        build();

            // If healthchecks have been found then a 200 OK + payload response is sent:
            /* In order not to see null fields we send a List of HealthCheckReadRequest,
               because there is the JSON dependency there: @JsonInclude(JsonInclude.Include.NON_NULL).
               So in order to avoid putting that dependency in the HealthCheckDescriptor we leverage the DTO,
               Otherwise the HealthCheckDescriptor wouldn't be a POJO anymore.
             */

            // Instance the List of HealthCheckReadRequest
            List<HealthCheckReadRequest> readServiceList = new ArrayList<>();

            // Converto to DTO every HealthCheckDescriptor of serviceList and add it to the List we're going send
            for(HealthCheckDescriptor healthCheckDescriptor: serviceList){
                readServiceList.add(convertToDto(healthCheckDescriptor));
            }

            // Response 200 OK + payload
            return Response.ok(readServiceList).build();

        } catch (Exception e){
            e.printStackTrace();
            // If an exception has been thrown then a 500 Internal Server Error response is sent
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    type(MediaType.APPLICATION_JSON_TYPE).
                    entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !")).
                    build();
        }
    }

    /**
     * This method allows managing and responding to a GET request from a client,
     * who wants to obtain a particular healthcheck through its healthcheckId contained in the resource's URI.
     *
     * @param req Dropwizard maps the incoming request into an object, which can be used within our method
     * @param healthCheckId it is a String representing the UUID of the healthcheck, contained in the URI as @PathParam
     * @return
     * <ol>
     *     <li> If the healthcheckID is not provided,
     *     then it responds with a {@code 400 Bad Request} and an informational body serialized in JSON </li>
     *     <li> If after loading the healthcheck list from the specific data structure it is empty,
     *     then it responds with a {@code 404 Not Found} and an informational body serialized in JSON </li>
     *     <li> If, on the other hand, the list contains at least one healthcheck, it responds
     *     with {@code 200 OK} + serialized payload in JSON </li>
     *     <li> If for any reason an exception is thrown, it is captured and the server responds
     *     with {@code 500 Internal Server Error } and an informational body serialized in JSON </li>
     * </ol>
     * @since 1.0
     */
    @GET
    @Path("/{healthcheck_id}")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHealthCheckById(@Context ContainerRequestContext req,
                                @PathParam("healthcheck_id") String healthCheckId) {

        try {

            logger.info("Loading HealthCheck Info for id: {}", healthCheckId);

            //Check if healthCheckId is null, if yes then a 400 Bad Request response is sent
            if(healthCheckId == null)
                return Response.status(Response.Status.BAD_REQUEST).
                        type(MediaType.APPLICATION_JSON_TYPE).
                        entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(),"Invalid HealthCheck Id Provided !")).
                        build();

            // Load the stored healthcheck through the InventoryDataManager
            // The method returns an Optional, so it could be empty
            Optional<HealthCheckDescriptor> healthCheckDescriptor = this.conf.getInventoryDataManager().getHealthCheck(healthCheckId);

            // If no patient has been found then a 404 Not Found response is sent
            if(healthCheckDescriptor.isEmpty())
                return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),"HealthCheck Not Found !")).build();

            // Converto to DTO the HealthCheckDescriptor
            HealthCheckReadRequest healthCheckReadRequest = convertToDto(healthCheckDescriptor.get());

            // If the patient has been found then a 200 OK + payload response is sent
            return Response.ok(healthCheckReadRequest).build();

        } catch (Exception e){
            e.printStackTrace();

            // If an exception has been thrown then a 500 Internal Server Error response is sent
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !")).build();
        }
    }

    /**
     * This method allows managing and responding to a POST request from a client,
     * who wants to create a new healthcheck.
     *
     * @param req Dropwizard maps the incoming request into an object, which can be used within our method
     * @param uriInfo it is the object made available by dropwizard that will allow us to reply with the absolute
     *                URI of the resource that has been created
     * @param healthCheckCreationRequest it is the representation of the resource in relation to the DTO
     * @return <ol>
     *              <li> If the request is not valid, then it responds with a {@code 400 Bad Request} and an informational body serialized in JSON </li>
     *              <li> After creating the healthcheck through the InventoryDataManger, it responds
     *                  with {@code 201 Created} + the Location Header with the absolute URI of the created healthcheck </li>
     *              <li> If for any reason an exception is thrown, it is captured and the server responds
     *                  with {@code 500 Internal Server Error } and an informational body serialized in JSON </li>
     *        </ol>
     * @since 1.0
     */
    @POST
    @Path("/")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createHealthCheck(@Context ContainerRequestContext req,
                                   @Context UriInfo uriInfo,
                                   HealthCheckCreationRequest healthCheckCreationRequest) {

        try {

            logger.info("Incoming HealthCheck Creation Request: {}", healthCheckCreationRequest);

            // Check if the request is valid, and if not then a 400 Bad Request response is sent
            // The patientId must not be null as well as the doctorId
            if(healthCheckCreationRequest == null || healthCheckCreationRequest.getPatientId() == null || healthCheckCreationRequest.getDoctorId() == null)
                return Response.status(Response.Status.BAD_REQUEST).
                        type(MediaType.APPLICATION_JSON_TYPE).
                        entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(),"Invalid request payload")).
                        build();

            // PatientId is provided by the client during the healthcheck creation => we need to check within patientMap if that patientId exists
            if(this.conf.getInventoryDataManager().getPatientById(healthCheckCreationRequest.getPatientId()).isEmpty())
                return Response.status(Response.Status.BAD_REQUEST).
                        type(MediaType.APPLICATION_JSON_TYPE).
                        entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(),"You're trying to create a healthcheck for a patientId not registered!")).
                        build();

            // Need to map the DTO [HealthCheckCreationRequest] to the descriptor [HealthCheckDescriptor]
            HealthCheckDescriptor healthCheckDescriptor = healthCheckCreationRequest.toDescriptorCreateHealthCheck();

            // Create the healthcheck through the InventoryDataManager
            healthCheckDescriptor = this.conf.getInventoryDataManager().createNewHealthCheck(healthCheckDescriptor);

            /*
            // Now we need to retrieve telemetry data:
            // 1. instance a new mqtt handler
            MqttHandler mqttHandler = new MqttHandler();
            logger.info("Mqtt Handler initialized successfully!\n");

            // 2. connect the client
            mqttHandler.connectClient();
            logger.info("Connected!\n");

            // 3. send command start to smartwatch
            mqttHandler.sendCommandStart();
            logger.info("Session started!\n");

            // 4. subscribe to both info and telemetry topics
            logger.info("Receiving data ...\n");
            mqttHandler.subscribeInfo();
            mqttHandler.subscribeTelemetry();

            // 5. give it time to receive all data
            mqttHandler.waitToFinish();

            // 6. client disconnection
            mqttHandler.disconnectClient();
            logger.info("Data received successfully and client disconnected!\n");
            */

            //Set Smart Watch id
            healthCheckDescriptor.setSmartWatchId("smarWatch0001");
            healthCheckDescriptor.setFirmwareVersion("v 1.0.0.1");

            // Get the SenML List of senMLPacks [emulation of telemetry]
            List<SenMLPack> senMLPackList = SenMLPack.provideSenMlListSample();

            // Set date and time of start telemetry (emulated)
            healthCheckDescriptor.setDateTime(LocalDateTime.now());

            // Map SenMLPacks to HealthCheck Descriptor
            // These packs are correctly retrieved from the List through "n" [regular field] and then mapped based on that
            for(SenMLPack senMLPack : senMLPackList){
                switch (senMLPack.get(0).getN()) {
                    case HEART_RATE_KEY -> {
                        healthCheckDescriptor.mapHeartRateData(senMLPack);
                        healthCheckDescriptor.findAnomalyHeartBeat();
                    }
                    case SATURATION_KEY -> {
                        healthCheckDescriptor.mapSaturationData(senMLPack);
                        healthCheckDescriptor.findAnomalySaturation();
                    }
                    case GLUCOSE_KEY -> {
                        healthCheckDescriptor.mapGlucoseData(senMLPack);
                        healthCheckDescriptor.findAnomalyGlucose();
                    }
                    case TEMPERATURE_KEY -> {
                        healthCheckDescriptor.mapTemperatureData(senMLPack);
                        healthCheckDescriptor.findAnomalyTemperature();
                    }
                }
            }

            // The healthcheck has been created and a 204 No Content response is sent
            return Response.created(new URI(String.format("%s/%s",uriInfo.getAbsolutePath(),healthCheckDescriptor.getHealthcheckId()))).build();

        }catch (Exception e){
            e.printStackTrace();

            // If an exception has been thrown then a 500 Internal Server Error response is sent
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !")).build();
        }
    }

    /**
     * This method allows managing and responding to a PUT request from a client,
     * who wants to update an already existing healthcheck.
     *
     * @param req Dropwizard maps the incoming request into an object, which can be used within our method
     * @param healthCheckId it is a String representing the UUID of the healthcheck, contained in the URI as @PathParam
     * @param healthCheckUpdateRequest it is the representation of the resource in relation to the DTO
     * @return
     * <ol>
     *      <li> If the request is not valid because healthCheckIds mismatch between the body and the URI,
     *      then it responds with a {@code 400 Bad Request} and an informational body serialized in JSON </li>
     *      <li> After updating healthcheck's data through the InventoryDataManger, it responds
     *           with {@code 204 No Content} </li>
     *      <li> If for any reason an exception is thrown, it is captured and the server responds
     *           with {@code 500 Internal Server Error } and an informational body serialized in JSON </li>
     * </ol>
     * @since 1.0
     */
    @PUT
    @Path("/{healthcheck_id}")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateHealthCheck(@Context ContainerRequestContext req,
                                   @Context UriInfo uriInfo,
                                   @PathParam("healthcheck_id") String healthCheckId,
                                   HealthCheckUpdateRequest healthCheckUpdateRequest) {

        try {

            logger.info("Incoming HealthCheck ({}) Update Request: {}", healthCheckId, healthCheckUpdateRequest);

            // Check if the healthCheckId matches the healthCheckId of URI, if not then a 400 Bad Request response is sent
            if(healthCheckUpdateRequest == null || healthCheckUpdateRequest.getHealthcheckId() == null || !healthCheckUpdateRequest.getHealthcheckId().equals(healthCheckId))
                return Response.status(Response.Status.BAD_REQUEST).
                        type(MediaType.APPLICATION_JSON_TYPE).
                        entity(new ErrorMessage(Response.Status.BAD_REQUEST.
                                getStatusCode(),"Invalid request ! Check HealthCheck Id")).build();

            // Check if the healthcheck is available otherwise a 404 response will be sent to the client
            if(this.conf.getInventoryDataManager().getHealthCheck(healthCheckId).isEmpty())
                return Response.status(Response.Status.NOT_FOUND).
                        type(MediaType.APPLICATION_JSON_TYPE).
                        entity(new ErrorMessage(Response.Status.NOT_FOUND.
                                getStatusCode(),"HealthCheck not found !")).build();

            // Need to map the DTO [HealthCheckUpdateRequest] to the descriptor [HealthCheckDescriptor]
            HealthCheckDescriptor healthCheckDescriptor = healthCheckUpdateRequest.toDescriptorUpdateHealthCheck();

            // Update the healthcheck through the InventoryDataManager
            this.conf.getInventoryDataManager().updateHealthCheck(healthCheckDescriptor);

            // The healthcheck has been updated and a 204 No Content response is sent
            return Response.noContent().build();

        } catch (Exception e){
            e.printStackTrace();

            // If an exception has been thrown then a 500 Internal Server Error response is sent
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    type(MediaType.APPLICATION_JSON_TYPE).
                    entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !")).
                    build();
        }
    }

    /**
     * This method allows managing and responding to a DELETE request from a client,
     * who wants to delete an already existing healthcheck.
     *
     * @param req Dropwizard maps the incoming request into an object, which can be used within our method
     * @param healthCheckId it is a String representing the UUID of the healthcheck, contained in the URI as @PathParam
     * @return  <ol>
     *              <li> If the request is not valid then it responds with a {@code 400 Bad Request}
     *                  and an informational body serialized in JSON </li>
     *              <li> After deleting patient's data through the InventoryDataManger, it responds
     *                  with {@code 204 No Content} </li>
     *              <li> If for any reason an exception is thrown, it is captured and the server responds
     *                  with {@code 500 Internal Server Error } and an informational body serialized in JSON </li>
     *        </ol>
     * @since 1.0
     */
    @DELETE
    @Path("/{healthcheck_id}")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteHealthCheck(@Context ContainerRequestContext req,
                                 @PathParam("healthcheck_id") String healthCheckId) {

        try {

            logger.info("Deleting HealthCheck with id: {}", healthCheckId);

            // Check if the healthcheckId in the URI of the request is null, if yes then 400 Bad Request response is sent
            if(healthCheckId == null)
                return Response.status(Response.Status.BAD_REQUEST).
                        type(MediaType.APPLICATION_JSON_TYPE).
                        entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(),"Invalid HealthCheck Id Provided !")).
                        build();

            // Check if the healthcheck is available, if not then 404 Not Found response is sent
            if(this.conf.getInventoryDataManager().getHealthCheck(healthCheckId).isEmpty())
                return Response.status(Response.Status.NOT_FOUND).
                        type(MediaType.APPLICATION_JSON_TYPE).
                        entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),"HealthCheck Not Found !")).
                        build();

            // Delete the healthcheck through the InventoryDataManager
            this.conf.getInventoryDataManager().deleteHealthCheck(healthCheckId);

            // The healthcheck has been deleted and a 204 No Content response is sent
            return Response.noContent().build();

        } catch (Exception e){
            e.printStackTrace();

            // If an exception has been thrown then a 500 Internal Server Error response is sent
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !")).build();
        }
    }

    /**
     * This method allow mapping the HealthCheckDescriptor, that is a POJO with no dependencies
     * to the HealthCheckReadRequest, where there are JSON dependencies. We use the {@code ModelMapper} to
     * perform the conversion.
     *
     * @param healthCheckDescriptor it is the descriptor of the healthcheck with which the service layer works.
     * @return the HealthCheckRequest, that is the 1:1 mapping from its descriptor [extends], with which the presentation layer works.
     */
    private HealthCheckReadRequest convertToDto(HealthCheckDescriptor healthCheckDescriptor){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(healthCheckDescriptor, HealthCheckReadRequest.class);
    }


}
