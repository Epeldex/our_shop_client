/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.util.StringConverter;

/**
 *
 * @author alexa
 */
public class LocalDateStringConverter extends StringConverter<LocalDate> {

    public static DateTimeFormatter dateFormatter;

    public LocalDateStringConverter(DateFormat formatter) {
        String pattern = SimpleDateFormat.class.cast(formatter).toPattern();

        dateFormatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public String toString(LocalDate object) {
        return object == null ? "" : object.toString();
    }

    @Override
    public LocalDate fromString(String string) {
        if (string != null && !string.isEmpty()) {
            return LocalDate.parse(string, dateFormatter);
        } else {
            return null;
        }
    }

}
