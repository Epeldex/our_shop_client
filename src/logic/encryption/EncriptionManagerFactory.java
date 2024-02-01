/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.encryption;

/**
 * The {@code EncriptionManagerFactory} class is a factory class for creating
 * and managing instances of {@code EncriptionManager}. It uses the Singleton
 * pattern to ensure that only one instance of {@code EncriptionManager} is
 * created and used throughout the application.
 */
public class EncriptionManagerFactory {

    // Singleton instance of EncriptionManager
    private static EncriptionManager obj = new EncriptionManagerImplementation();

    /**
     * Returns the singleton instance of {@code EncriptionManager}. If the
     * instance does not exist, it is created; if it exists, the existing
     * instance is returned. This ensures that there is always only one instance
     * of {@code EncriptionManager} in use.
     *
     * @return the single instance of {@code EncriptionManager}
     */
    public static EncriptionManager getInstance() {
        return obj;
    }

}
