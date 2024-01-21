/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.StringConverter;

/**
 * <p>
 * {@link StringConverter} implementation for {@link Double} (and double
 * primitive) values.</p>
 *
 * @since JavaFX 2.1
 */
public class DoubleStringConverter extends StringConverter<Double> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Double fromString(String value) {
        try {
            // If the specified value is null or zero-length, return 0
            if (value == null) {
                return 0D;
            }

            value = value.trim();

            if (value.length() < 1) {
                return 0D;
            }

            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            Logger.getLogger(DoubleStringConverter.class.getName()).log(Level.SEVERE, "The introduced value is not numeric");
            //We return null because we cannot throw an exception
            //The desired behavior will be implemented in the onEditCommit handlers for Doubles
            return null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(Double value) {
        // If the specified value is null, return a zero-length String
        if (value == null) {
            return "";
        }

        return Double.toString(value.doubleValue());
    }
}
