package it.unimore.iot.health.api.datamanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.unimore.iot.health.api.datamanager.model.HealthCheckDescriptor;

/**
 * {@code HealthCheckReadRequest} is a DTO class (Data Transfer Object) that allows clients not to
 * see null values of HealthCheckDescriptor's fields in response to a GET request.
 * So in order to avoid putting JSON dependencies in the HealthCheckDescriptor we leverage the DTO,
 * Otherwise the HealthCheckDescriptor wouldn't be a POJO anymore.
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthCheckReadRequest extends HealthCheckDescriptor {
}
