/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.encryption;
/**
 *
 * @author alexa
 */
public class EncriptionManagerFactory {

    private static EncriptionManager obj = new EncriptionManagerImplementation();

    public static EncriptionManager getInstance() {
        return obj;
    }

}
