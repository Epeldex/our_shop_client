package ui.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.util.StringConverter;

/**
 * A custom StringConverter for converting between LocalDate and String
 * representations.
 *
 * @author Alex Epelde
 */
public class LocalDateStringConverter extends StringConverter<LocalDate> {

    /**
     * The DateTimeFormatter used for formatting and parsing LocalDate objects.
     * It is initialized based on the pattern of the provided DateFormat.
     */
    public static DateTimeFormatter dateFormatter;

    /**
     * Constructs a LocalDateStringConverter with the given DateFormat.
     *
     * @param formatter The DateFormat used for pattern extraction.
     */
    public LocalDateStringConverter(DateFormat formatter) {
        // Extract pattern from the provided DateFormat
        String pattern = SimpleDateFormat.class.cast(formatter).toPattern();
        // Initialize the DateTimeFormatter with the extracted pattern
        dateFormatter = DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * Converts a LocalDate object to its String representation.
     *
     * @param object The LocalDate object to be converted.
     * @return The String representation of the LocalDate, or an empty string if
     * the input is null.
     */
    @Override
    public String toString(LocalDate object) {
        return object == null ? "" : object.toString();
    }

    /**
     * Converts a String representation to a LocalDate object.
     *
     * @param string The String representation of a LocalDate.
     * @return The parsed LocalDate object, or null if the input is null or
     * empty.
     */
    @Override
    public LocalDate fromString(String string) {
        if (string != null && !string.isEmpty()) {
            return LocalDate.parse(string, dateFormatter);
        } else {
            return null;
        }
    }
}
