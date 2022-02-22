package it.unimore.iot.health.api.datamanager.exception;

/**
 * {@code DataManagerException} class is an extension of {@link Exception} and it is responsible for providing an error
 * message when an exception related to the {@link it.unimore.iot.health.api.datamanager.persistence.InventoryDataManager} in thrown.
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
public class DataManagerException extends Exception{

    /**
     * @param errorMessage  it is the error message to be visualised when an exception is thrown
     * @since 1.0
     */
    public DataManagerException(String errorMessage){
        super(errorMessage);
    }
}
