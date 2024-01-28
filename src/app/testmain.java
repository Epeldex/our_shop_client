/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import logic.encryption.EncriptionManagerFactory;
import logic.factories.AdminManagerFactory;
import transfer.objects.Admin;
import transfer.objects.UserType;

/**
 *
 * @author alexa

 */
public class testmain {

    public static void main(String args[]) {
        User user = 
                new User("Shop Admin",
                        Base64.getEncoder().encodeToString(
                                EncriptionManagerFactory.getInstance().encryptMessage(
                                        EncriptionManagerFactory.getInstance().hashMessage("abcd*1234"))));
        user = new UserRESTClient().signIn(user, User.class);
        System.out.println(user.getId());
    }

}
