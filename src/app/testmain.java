/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import logic.factories.*;
import transfer.objects.*;

/**
 *
 * @author alexa
 */
public class testmain {

    public static void main(String args[]) throws Exception {
        for (User u : UserManagerFactory.getInstance().findAllUsers()) {
            System.out.println(u.getPassword());
        }

    }

}
