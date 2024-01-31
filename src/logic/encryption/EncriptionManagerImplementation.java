/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.encryption;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.ws.rs.InternalServerErrorException;
import rest.UserRESTClient;

/**
 *
 * @author alexa
 */
public class EncriptionManagerImplementation implements EncriptionManager {

    private static final Logger LOGGER = Logger.getLogger("EncriptionManager");

    private static UserRESTClient rest = new UserRESTClient();

    private static PublicKey publicKey;
    private static SecretKey symmetricKey;

    public EncriptionManagerImplementation() throws InternalServerErrorException {
        if (publicKey == null) {
            publicKey = getPublicKey();
        }
        if (symmetricKey == null) {
            symmetricKey = getSymmetricKey();
        }
    }

    private PublicKey getPublicKey() throws InternalServerErrorException {
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
            throw new InternalServerErrorException(e);
        }

    }

    @Override
    public byte[] encryptMessage(String message) throws InternalServerErrorException {
        try {
            Cipher cipher = Cipher.getInstance("AES"); // No Bouncy Castle provider
            cipher.init(Cipher.ENCRYPT_MODE, symmetricKey);
            return cipher.doFinal(Base64.getDecoder().decode(message));
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public byte[] decryptMessage(String message) throws InternalServerErrorException {
        try {
            LOGGER.info("Decrypting message");
            Cipher cipher = Cipher.getInstance("AES"); // No Bouncy Castle provider
            cipher.init(Cipher.DECRYPT_MODE, symmetricKey);
            return cipher.doFinal(Base64.getDecoder().decode(message));

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error decrypting message", e);
            throw new InternalServerErrorException(e);
        }

    }

    @Override
    public String hashMessage(String message) throws InternalServerErrorException {
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
            throw new InternalServerErrorException(e);
        }
    }

    public SecretKey getSymmetricKey() throws InternalServerErrorException {
        try {
            String encodedKey = rest.requestSymmetricKey();
            byte[] decodedKey = Base64.getDecoder().decode(extractKey(encodedKey));
            return new SecretKeySpec(decryptSymmetricKey(decodedKey), "AES");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error encrypting message", e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    public byte[] decryptSymmetricKey(byte[] key) throws InternalServerErrorException {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(key);
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
    }

    private String extractKey(String xmlString) throws Exception {
        String regex = "<key>(.*?)</key>";
        Matcher matcher = Pattern.compile(regex).matcher(xmlString);
        return matcher.find() ? matcher.group(1) : null;
    }

}
