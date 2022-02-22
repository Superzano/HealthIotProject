package it.unimore.iot.health.api.datamanager.services;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import it.unimore.iot.health.api.datamanager.resources.HealthCheckResource;
import it.unimore.iot.health.api.datamanager.resources.PatientResource;
import it.unimore.iot.health.api.datamanager.utils.DummyDataGenerator;

/**
 * {@code AppService} is the base class that starts the server. It is based on dropwizard and extends an Application type
 *  class which has as parameters {@code <AppConfig>}, our configuration.
 *  @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
public class AppService extends Application<AppConfig> {

    /**
     * This is the Java main method, and "main" is the identifier that the JVM looks for as the starting point of the java program
     * @param args it contains the supplied command-line arguments as an array of String objects.
     * @throws Exception it is a form of
     *  {@code Throwable} that indicates conditions that a reasonable
     *  application might want to catch.
     * @since 1.0
     */
    public static void main(String[] args) throws Exception{

        new AppService().run("server", args.length > 0 ? args[0] : "configuration.yml");
    }

    /**
     * This method runs our application by passing it a configuration file which is automatically read and interpreted
     * by the library. This configuration file is called "configuration.yml", which is a compact and convenient data
     * format for making configurations, which uses key-value pairs even with a hierarchical structure. Dropwizard uses
     * this file to be able to configure the parameters of its application, so we can tell the type of protocol used,
     * the port on which it listens etc...
     *
     * @param appConfig it is a system variable related to the configuration of our application
     * @param environment it is a system variable that consists of all the resources which our application provides.
     * @throws Exception it is a form of
     *     {@code Throwable} that indicates conditions that a reasonable
     *      application might want to catch.
     * @since 1.0
     */
    public void run(AppConfig appConfig, Environment environment) throws Exception {

        // Create demo patients and demo healthchecks
        DummyDataGenerator.generateDummyPatients(appConfig.getInventoryDataManager());
        DummyDataGenerator.generateDummyHealthChecks(appConfig.getInventoryDataManager());

        // Add defined resources to the environment
        environment.jersey().register(new PatientResource(appConfig));
        environment.jersey().register(new HealthCheckResource(appConfig));

    }
}
