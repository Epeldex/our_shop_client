package logic.encryption;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import logic.exceptions.LogicException;
import rest.UserRESTClient;

/**
 * Implementation of the {@link EncriptionManager} interface. This class
 * provides functionalities for encrypting and decrypting messages using AES
 * encryption, and hashing messages using MD5 algorithm.
 * <p>
 * It manages a symmetric key (AES) and an asymmetric public key (RSA) for
 * encryption and decryption purposes. The symmetric key is obtained and
 * decrypted using the RSA public key.
 * </p>
 * <p>
 * The implementation uses an instance of {@link UserRESTClient} to retrieve the
 * encrypted symmetric key.
 * </p>
 *
 * @author alexa
 */
public class EncriptionManagerImplementation implements EncriptionManager {

    private static final Logger LOGGER = Logger.getLogger("EncriptionManager");

    private static UserRESTClient rest = new UserRESTClient();

    private static PublicKey publicKey;
    private static SecretKey symmetricKey;

    /**
     * Constructor for {@link EncriptionManagerImplementation}. Initializes the
     * public key and symmetric key by fetching and processing them. If keys are
     * not already set, it fetches and sets them up.
     */
    public EncriptionManagerImplementation() {
        try {
            if (publicKey == null) {
                publicKey = getPublicKey();
            }
            if (symmetricKey == null) {
                symmetricKey = getSymmetricKey();
            }
        } catch (LogicException ex) {
            Logger.getLogger(EncriptionManagerImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retrieves the public RSA key used for decrypting the symmetric key. The
     * public key is fetched from a resource file and processed.
     *
     * @return PublicKey The RSA public key.
     * @throws LogicException If there is an issue in fetching or processing the
     * public key.
     */
    private PublicKey getPublicKey() throws LogicException {
        try {
            InputStream encodedKeyStream = getClass().getClassLoader().getResourceAsStream("keys/publicKey.der");
            byte[] encodedKeyBytes = new byte[encodedKeyStream.available()];
            encodedKeyStream.read(encodedKeyBytes);
            byte[] publicKeyBytes = Base64.getDecoder().decode(encodedKeyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error setting up public key encryption");
            throw new LogicException(e);
        }

    }

    /**
     * {@inheritDoc} Encrypts the provided message using AES encryption
     * algorithm with the symmetric key. The message is first base64 decoded
     * before encryption.
     */
    @Override
    public byte[] encryptMessage(String message) throws LogicException {
        try {
            Cipher cipher = Cipher.getInstance("AES"); // No Bouncy Castle provider
            cipher.init(Cipher.ENCRYPT_MODE, symmetricKey);
            return cipher.doFinal(Base64.getDecoder().decode(message));
        } catch (Exception e) {
            throw new LogicException(e);
        }
    }

    /**
     * {@inheritDoc} Decrypts the provided message which is encrypted using AES
     * encryption algorithm. The message is base64 decoded before being
     * decrypted.
     */
    @Override
    public byte[] decryptMessage(String message) throws LogicException {
        try {
            LOGGER.info("Decrypting message");
            Cipher cipher = Cipher.getInstance("AES"); // No Bouncy Castle provider
            cipher.init(Cipher.DECRYPT_MODE, symmetricKey);
            return cipher.doFinal(Base64.getDecoder().decode(message));

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error decrypting message", e);
            throw new LogicException(e);
        }

    }

    /**
     * {@inheritDoc} Hashes the provided message using MD5 hashing algorithm.
     * Converts the byte array of the hashed message into a hexadecimal string.
     */
    @Override
    public String hashMessage(String message) throws LogicException {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md5.digest(message.getBytes());

            // Convert the byte array to a hexadecimal representation
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = String.format("%02X", b);
                hexStringBuilder.append(hex);
            }
            return hexStringBuilder.toString();
        } catch (Exception e) {
            throw new LogicException(e);
        }
    }

    /**
     * Retrieves and sets up the symmetric AES key. The symmetric key is fetched
     * from a REST client, decrypted using the RSA public key, and then used for
     * creating an AES key specification.
     *
     * @return SecretKey The symmetric AES key.
     * @throws LogicException If there is an issue in fetching or processing the
     * symmetric key.
     */
    public SecretKey getSymmetricKey() throws LogicException {
        try {
            String encodedKey = rest.requestSymmetricKey();
            byte[] decodedKey = Base64.getDecoder().decode(extractKey(encodedKey));
            return new SecretKeySpec(decryptSymmetricKey(decodedKey), "AES");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error encrypting message", e.getMessage());
            throw new LogicException(e);
        }
    }

    /**
     * Decrypts the symmetric key using the RSA public key. This method is used
     * internally to decrypt the symmetric key fetched from the REST client.
     *
     * @param key The encrypted symmetric key as a byte array.
     * @return byte[] The decrypted symmetric key.
     * @throws LogicException If there is an issue in decrypting the symmetric
     * key.
     */
    public byte[] decryptSymmetricKey(byte[] key) throws LogicException {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(key);
        } catch (Exception e) {
            throw new LogicException(e);
        }
    }

    /**
     * Extracts the symmetric key from an XML string. The symmetric key is
     * embedded in an XML structure and this method extracts it using regex.
     *
     * @param xmlString The XML string containing the symmetric key.
     * @return String The extracted symmetric key.
     * @throws Exception If there is an issue in extracting the key.
     */
    private String extractKey(String xmlString) throws Exception {
        String regex = "<key>(.*?)</key>";
        Matcher matcher = Pattern.compile(regex).matcher(xmlString);
        return matcher.find() ? matcher.group(1) : null;
    }

}
