package it.unimore.iot.health.api.datamanager.services;

import io.dropwizard.Configuration;
import it.unimore.iot.health.api.datamanager.persistence.InventoryDataManager;
import it.unimore.iot.health.api.datamanager.persistence.IInventoryDataManager;

/**
 * {@code AppConfig} extends the {@code Configuration} class allowing us to define our own configuration
 * @author Christopher Zanoli, Undergraduate student - 270765@studenti.unimore.it
 */
public class AppConfig extends Configuration{

    // InventoryDataManagerInterface variable declaration
    public IInventoryDataManager inventoryDataManagerInterface = null;

    /**
     * Inside {@code AppConfig} we have defined an "interface" type variable without specifying the
     * class that implements it, because the implementation of this interface is done in {@code InventoryDataManager}
     * which manages the data in the local memory, through {@code HashMap}. This is because if in the future we want
     * to manage data through a database, just make a new implementation of the {@code IInventoryDataManager} interface,
     * and inside {@code getInventoryDataManager()} we only need to change the following line of code:
     * <p> </p>
     * {@code this.inventoryDataManagerInterface = new SqlDatabaseInventoryDataManager ();}
     * <p> </p>
     * This allows making other components of the application not care about how the data manager is implemented,
     * as long as it respects the correct interface.
     *
     * @return the IInventoryDataManager variable previously declared
     */
    public IInventoryDataManager getInventoryDataManager() {

        if(this.inventoryDataManagerInterface == null)
            this.inventoryDataManagerInterface = new InventoryDataManager();

        return this.inventoryDataManagerInterface;
    }
}
