package it.unimore.iot.health.api.datamanager.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.jersey.errors.ErrorMessage;
import it.unimore.iot.health.api.datamanager.dto.PatientCreationRequest;
import it.unimore.iot.health.api.datamanager.dto.PatientUpdateRequest;
import it.unimore.iot.health.api.datamanager.model.PatientDescriptor;
import it.unimore.iot.health.api.datamanager.services.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code PatientResource} class contains a series of methods necessary for the correct management
 * of incoming requests by a client, in accordance with the CRUD manipulation of the patient resource.
 * Therefore, any request sent by a client related to the Patient resource is processed here, and this is where
 * all HTTP response codes come into play.
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */

@Path("/api/iot/inventory/patient") // Base path
public class PatientResource {

    // Utils
    final protected Logger logger = LoggerFactory.getLogger(PatientResource.class);
    final AppConfig conf;

    // Constructor
    public PatientResource(AppConfig conf) {
        this.conf = conf;
    }

    /**
     * This method allows managing and responding to a GET request from a client,
     * who wants to obtain the list of registered patients.
     *
     * @param req Dropwizard maps the incoming request into an object, which can be used within our method
     * @return
     * <ol>
     *     <li> If after loading the patient list from the specific data structure it is empty,
     *     then it responds with a {@code 404 Not Found} and an informational body serialized in JSON </li>
     *     <li> If, on the other hand, the list contains at least one patient, it responds
     *     with {@code 200 OK} + serialized payload in JSON </li>
     *     <li> If for any reason an exception is thrown, it is captured and the server responds
     *     with {@code 500 Internal Server Error } and an informational body serialized in JSON </li>
     * </ol>
     * @since 1.0
     */
    @GET
    @Path("/")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatients(@Context ContainerRequestContext req) {

        try {

            logger.info("Loading all stored IoT Inventory Patients ...");

            // Load the stored patients through the InventoryDataManager
            List<PatientDescriptor> patientList = this.conf.getInventoryDataManager().getPatientList();

            // If no patients have been found then a 404 Not Found response is sent
            if(patientList.isEmpty())
                return Response.status(Response.Status.NOT_FOUND).
                        type(MediaType.APPLICATION_JSON_TYPE).
                        entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),"Patients Not Found !")).
                        build();

            // If at least one patient has been found then a 200 OK + payload response is sent
            return Response.ok(patientList).build();

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
     * who wants to obtain a particular patient through his patientId contained in the resource's URI.
     *
     * @param req Dropwizard maps the incoming request into an object, which can be used within our method
     * @param patientId it is a String representing the UUID of the patient, contained in the URI as @PathParam
     * @return
     * <ol>
     *     <li> If the patietnID is not provided,
     *     then it responds with a {@code 400 Bad Request} and an informational body serialized in JSON </li>
     *     <li> If after loading the patient list from the specific data structure it is empty,
     *     then it responds with a {@code 404 Not Found} and an informational body serialized in JSON </li>
     *     <li> If, on the other hand, the list contains at least one patient, it responds
     *     with {@code 200 OK} + serialized payload in JSON </li>
     *     <li> If for any reason an exception is thrown, it is captured and the server responds
     *     with {@code 500 Internal Server Error } and an informational body serialized in JSON </li>
     * </ol>
     * @since 1.0
     */

    @GET
    @Path("/{patient_id}")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientById(@Context ContainerRequestContext req,
                                @PathParam("patient_id") String patientId) {

        try {

            logger.info("Loading Patient Info for id: {}", patientId);

            // Check the request and if it is null then a 400 Bad Request response is sent
            if(patientId == null)
                return Response.status(Response.Status.BAD_REQUEST).
                        type(MediaType.APPLICATION_JSON_TYPE).
                        entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(),"Invalid Patient Id Provided !")).
                        build();

            // Load the stored patient through the InventoryDataManager
            // The method returns an Optional, so it could be empty
            Optional<PatientDescriptor> patientDescriptor = this.conf.getInventoryDataManager().getPatientById(patientId);

            // If no patient has been found then a 404 Not Found response is sent
            if(patientDescriptor.isEmpty())
                return Response.status(Response.Status.NOT_FOUND).
                        type(MediaType.APPLICATION_JSON_TYPE).
                        entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),"Patient Not Found !")).
                        build();

            // If the patient has been found then a 200 OK + payload response is sent
            return Response.ok(patientDescriptor.get()).build();

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
     * This method allows managing and responding to a POST request from a client,
     * who wants to create a new patient.
     *
     * @param req Dropwizard maps the incoming request into an object, which can be used within our method
     * @param uriInfo it is the object made available by dropwizard that will allow us to reply with the absolute
     *                URI of the resource that has been created
     * @param patientCreationRequest it is the representation of the resource in relation to the DTO
     * @return <ol>
     *              <li> If the request is not valid according to the checks of {@code isPatientCreationRequestValid} method,
     *                  then it responds with a {@code 400 Bad Request} and an informational body serialized in JSON </li>
     *              <li> After creating the patient through the InventoryDataManger, it responds
     *                  with {@code 201 Created} + the Location Header with the absolute URI of the created patient </li>
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
    public Response createPatient(@Context ContainerRequestContext req,
                               @Context UriInfo uriInfo,
                               PatientCreationRequest patientCreationRequest) {

        try {

            logger.info("Incoming User Creation Request: {}", patientCreationRequest);

            // Check if the request is valid, and if not then a 400 Bad Request response is sent
            if(!isPatientCreationRequestValid(patientCreationRequest))
                return Response.status(Response.Status.BAD_REQUEST).
                        type(MediaType.APPLICATION_JSON_TYPE).
                        entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(),"Invalid request payload"))
                        .build();

            // Need to map the DTO [PatientCreationRequest] to the descriptor [PatientDescriptor]
            PatientDescriptor patientDescriptor = patientCreationRequest.toDescriptorCreatePatient();

            // Create the patient through the InventoryDataManager
            patientDescriptor = this.conf.getInventoryDataManager().createNewPatient(patientDescriptor);

            // The patient has been created and a 201 + header Location response is sent
            // Here we attach the new patientId created by the server to the base path of the request
            return Response.created(new URI(String.format("%s/%s",uriInfo.getAbsolutePath(),patientDescriptor.getPatientId()))).
                    build();

        } catch (Exception e){
            e.printStackTrace();
            // If an exception has been thrown then a 500 Internal Server Error response is sent
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !")).build();
        }
    }

    /**
     * This method allows managing and responding to a PUT request from a client,
     * who wants to update an already existing patient.
     *
     * @param req Dropwizard maps the incoming request into an object, which can be used within our method
     * @param patientId it is a String representing the UUID of the patient, contained in the URI as @PathParam
     * @param patientUpdateRequest it is the representation of the resource in relation to the DTO
     * @return
     * <ol>
     *      <li> If the request is not valid according to the checks of {@code isPatientUpdateRequestValid} method or
     *      because patientIds mismatch between the body and the URI, then it responds with a {@code 400 Bad Request}
     *      and an informational body serialized in JSON </li>
     *      <li> After updating patient's data through the InventoryDataManger, it responds
     *           with {@code 204 No Content} </li>
     *      <li> If for any reason an exception is thrown, it is captured and the server responds
     *           with {@code 500 Internal Server Error } and an informational body serialized in JSON </li>
     * </ol>
     * @since 1.0
     */

    @PUT
    @Path("/{patient_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePatient(@Context ContainerRequestContext req,
                                 @PathParam("patient_id") String patientId,
                                 PatientUpdateRequest patientUpdateRequest) {
        try {

            // Check if the request is valid, and if not then a 400 Bad Request response is sent
            if(!isPatientUpdateRequestValid(patientUpdateRequest))
                return Response.status(Response.Status.BAD_REQUEST).
                        type(MediaType.APPLICATION_JSON_TYPE).
                        entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(),"Invalid request payload"))
                        .build();

            // Check if patientId is not null and if it matches the patientId of URI, if not then a 400 Bad Request response is sent
            if(patientUpdateRequest.getPatientId() == null || !patientUpdateRequest.getPatientId().equals(patientId))
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(),"Invalid request ! Check Patient Id"))
                        .build();

            // Check if the patient is available and correctly registered otherwise a 404 response will be sent to the client
            if(this.conf.getInventoryDataManager().getPatientById(patientId).isEmpty())
                return Response.status(Response.Status.NOT_FOUND)
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),"Patient Not Found !"))
                        .build();

            // Need to map the DTO [PatientUpdateRequest] to the descriptor [PatientDescriptor]
            PatientDescriptor patientDescriptor = patientUpdateRequest.toDescriptorUpdatePatient();

            // Update the patient through the InventoryDataManager
            this.conf.getInventoryDataManager().updatePatient(patientDescriptor);

            // The patient has been updated and a 204 No Content response is sent
            return Response.noContent().build();

        } catch (Exception e){
            e.printStackTrace();

            // If an exception has been thrown then a 500 Internal Server Error response is sent
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !")).build();
        }
    }

    /**
     * This method allows managing and responding to a DELETE request from a client,
     * who wants to delete an already existing patient.
     *
     * @param req Dropwizard maps the incoming request into an object, which can be used within our method
     * @param patientId it is a String representing the UUID of the patient, contained in the URI as @PathParam
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
    @Path("/{patient_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePatient(@Context ContainerRequestContext req,
                                 @PathParam("patient_id") String patientId) {
        try {

            // Check if the patientId in the URI of the request is null, if yes then 400 Bad Request response is sent
            if(patientId == null)
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(),"Invalid Patient Id Provided !"))
                        .build();

            // Load the stored patient through the InventoryDataManager
            // The method returns an Optional, so it could be empty
            Optional<PatientDescriptor> patientDescriptor = this.conf.getInventoryDataManager().getPatientById(patientId);

            // Check if the patient is available, if not then 404 Not Found response is sent
            if(patientDescriptor.isEmpty())
                return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),"Patient Not Found !")).build();

            // Delete the patient through the InventoryDataManager
            this.conf.getInventoryDataManager().deletePatient(patientId);

            // The patient has been deleted and a 204 No Content response is sent
            return Response.noContent().build();

        } catch (Exception e){
            e.printStackTrace();

            // If an exception has been thrown then a 500 Internal Server Error response is sent
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !"))
                    .build();
        }
    }

    /**
     * This method allows checking if the client's creation of a patient is valid
     *
     * @param patientCreationRequest it is the representation of the resource [DTO] by the client
     * @return True if
     * <ol>
     *     <li>
     *         The creation request is not null
     *     </li>
     *     <li>
     *         The creation request respects the rules defined by {@code isCreationPatientDataValid} method
     *     </li>
     *
     *
     * </ol>
     * @since 1.0
     */
    private boolean isPatientCreationRequestValid(PatientCreationRequest patientCreationRequest) {

        return patientCreationRequest != null && isCreationPatientDataValid(patientCreationRequest);
    }

    /**
     * This method allows checking if the client's update of a patient is valid
     *
     * @param patientUpdateRequest it is the representation of the resource [DTO] by the client
     * @return True if
     * <ol>
     *     <li>
     *         The update request is not null
     *     </li>
     *     <li>
     *         The update request respects the rules defined by {@code isUpdatePatientDataValid} method
     *     </li>
     *
     *
     * </ol>
     * @since 1.0
     */
    private boolean isPatientUpdateRequestValid(PatientUpdateRequest patientUpdateRequest) {

        return patientUpdateRequest != null && isUpdatePatientDataValid(patientUpdateRequest);
    }

    /**
     * This method defines a set of rules that the creation request must respect, relating to
     * the fields of its body.
     *
     * @param patientCreationRequest it is the representation of the resource [DTO] by the client
     * @return True if {@code ALL} the rules are respected
     * @since 1.0
     */
    private boolean isCreationPatientDataValid(PatientCreationRequest patientCreationRequest){

        List<String> genders = Stream.of("male", "female", "N/A").collect(Collectors.toList());

        return  patientCreationRequest.getName() != null &&
                patientCreationRequest.getSurname() != null &&
                patientCreationRequest.getGender() != null &&
                patientCreationRequest.getPhoneNumber() != null &&
                patientCreationRequest.getEmail() != null &&
                patientCreationRequest.getDateOfBirth() != null &&
                patientCreationRequest.getPlaceOfBirth() != null &&
                patientCreationRequest.getEmail().contains("@") &&
                !patientCreationRequest.getName().matches(".*\\d.*") &&
                !patientCreationRequest.getSurname().matches(".*\\d.*") &&
                genders.contains(patientCreationRequest.getGender()) &&
                !patientCreationRequest.getPlaceOfBirth().matches(".*\\d.*");

    }

    /**
     * This method defines a set of rules that the update request must respect, relating to
     * the fields of its body.
     *
     * @param patientUpdateRequest it is the representation of the resource [DTO] by the client
     * @return True if {@code ALL} the rules are respected
     * @since 1.0
     */
    private boolean isUpdatePatientDataValid(PatientUpdateRequest patientUpdateRequest) {

        if (patientUpdateRequest.getEmail() != null)
            return patientUpdateRequest.getEmail().contains("@");
        else
            return true;
    }
}