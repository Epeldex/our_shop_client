/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.encryption;

import javax.ws.rs.InternalServerErrorException;

/**
 *
 * @author alexa
 */
public interface EncriptionManager {

    public byte[] encryptMessage(String message) throws InternalServerErrorException;

    public byte[] decryptMessage(String message) throws InternalServerErrorException;

    public String hashMessage(String message) throws InternalServerErrorException;


}
