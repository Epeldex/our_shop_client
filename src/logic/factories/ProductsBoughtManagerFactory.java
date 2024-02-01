package logic.factories;

import logic.business.ProductsBoughtManagerImplementation;
import logic.interfaces.ProductsBoughtManager;

/**
 * Factory class for providing a singleton instance of ProductsBoughtManager.
 * Implements the Singleton pattern to ensure a single instance is used
 * globally.
 */
public class ProductsBoughtManagerFactory {

    private static ProductsBoughtManager obj = new ProductsBoughtManagerImplementation();

    /**
     * Returns the singleton instance of ProductsBoughtManager.
     *
     * @return singleton instance of ProductsBoughtManager.
     */
    public static ProductsBoughtManager getInstance() {
        return obj;
    }

}
