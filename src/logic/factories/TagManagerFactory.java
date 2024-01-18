/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.factories;

import logic.business.TagManagerImplementation;
import logic.interfaces.TagManager;

/**
 *
 * @author alexa
 */
public class TagManagerFactory {

    private static TagManager obj = new TagManagerImplementation();

    public static TagManager getInstance() {
        return obj;
    }

}
