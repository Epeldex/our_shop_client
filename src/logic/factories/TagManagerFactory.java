package logic.factories;

import logic.business.TagManagerImplementation;
import logic.interfaces.TagManager;

/**
 * Factory class for providing a singleton instance of TagManager. It implements
 * the Singleton pattern to ensure a single, consistent instance is used
 * throughout the application.
 */
public class TagManagerFactory {

    private static TagManager obj = new TagManagerImplementation();

    /**
     * Returns the singleton instance of TagManager.
     *
     * @return Singleton instance of TagManager.
     */
    public static TagManager getInstance() {
        return obj;
    }
}
