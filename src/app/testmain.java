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
 * @author 2dam
 */
public class testmain {

    public static void main(String args[]) {
        try {
            Admin admin = new Admin();
            admin.setUsername("Admin");
            admin.setActive(true);
            admin.setPassword(EncriptionManagerFactory.getInstance().hashMessage("abcd*1234"));
            admin.setUserType(UserType.ADMIN);

            AdminManagerFactory.getInstance().createAdmin(admin);
        } catch (Exception e) {

        }
    }

}
