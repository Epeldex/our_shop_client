package logic.encryption;

import logic.exceptions.LogicException;

/**
 * This interface defines the contract for encryption and hashing services.
 * Implementations of this interface are expected to provide functionalities for
 * encrypting, decrypting messages, and hashing messages.
 * <p>
 * The implementation is expected to use a combination of symmetric and
 * asymmetric encryption algorithms (AES for symmetric and RSA for asymmetric)
 * and MD5 for hashing. Symmetric and asymmetric keys are retrieved and managed
 * internally.
 * </p>
 */
public interface EncriptionManager {

    /**
     * Encrypts a given message using a symmetric encryption algorithm (AES).
     * The method expects a plain text message and returns the encrypted message
     * as a byte array.
     *
     * @param message The message to be encrypted.
     * @return The encrypted message as a byte array.
     * @throws LogicException If any issue occurs during the encryption process.
     */
    public byte[] encryptMessage(String message) throws LogicException;

    /**
     * Decrypts a given message which was previously encrypted using the
     * symmetric encryption algorithm (AES). The method expects an encrypted
     * message as input and returns the decrypted message as a byte array.
     *
     * @param message The encrypted message to be decrypted.
     * @return The decrypted message as a byte array.
     * @throws LogicException If any issue occurs during the decryption process.
     */
    public byte[] decryptMessage(String message) throws LogicException;

    /**
     * Hashes a given message using the MD5 hashing algorithm. This method is
     * used to generate a hash representation of a message.
     *
     * @param message The message to be hashed.
     * @return The hashed string representation of the message.
     * @throws LogicException If any issue occurs during the hashing process.
     */
    public String hashMessage(String message) throws LogicException;
}
