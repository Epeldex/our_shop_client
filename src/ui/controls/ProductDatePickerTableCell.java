package ui.controls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import transfer.objects.Product;
import ui.controller.LocalDateStringConverter;

/**
 * Custom TableCell for handling LocalDate in a DatePicker for a JavaFX
 * TableView. This class extends TableCell and provides custom editing
 * capabilities for LocalDate. It uses a DatePicker control for editing
 * LocalDate values.
 *
 * @param <Product> The type of data displayed in the TableView
 */
public class ProductDatePickerTableCell extends TableCell<Product, LocalDate> {

    private DatePicker datePicker; // The DatePicker control for editing
    private String oldValue; // The old value before editing
    private static DateFormat dateFormat; // Date format for displaying
    private static DateTimeFormatter dateFormatter; // DateTimeFormatter for formatting LocalDate

    /**
     * Constructor for the ProductDatePickerTableCell. It initializes date
     * formats for display and formatting LocalDate.
     */
    public ProductDatePickerTableCell() {
        dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        dateFormatter = DateTimeFormatter.ofPattern(SimpleDateFormat.class.cast(dateFormat).toPattern());
    }

    /**
     * Overrides the startEdit method to provide custom editing capabilities. It
     * creates a DatePicker control for editing LocalDate values.
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            datePicker = new DatePicker();
            datePicker.setConverter(new LocalDateStringConverter(dateFormat));
            oldValue = getText();

            // Set prompt text with an example date
            datePicker.setPromptText(SimpleDateFormat.class.cast(dateFormat).toPattern());
            datePicker.setOnAction((event) -> {
                commitEdit(Instant.ofEpochMilli(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .atZone(ZoneId.systemDefault()).toLocalDate());
            });
            setText(null);
            setGraphic(datePicker);
        }
    }

    /**
     * Overrides the updateItem method to update the displayed item. It sets the
     * text or graphic based on whether the cell is in editing mode.
     */
    @Override
    public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setText(null);
                setGraphic(datePicker);
            } else if (item != null) {
                // Use the default Locale for date formatting
                setText(dateFormatter.format(item));
                setGraphic(null);
            }
        }
    }

    /**
     * Overrides the cancelEdit method to cancel the editing mode. It sets the
     * graphic to null and restores the old value.
     */
    @Override
    public void cancelEdit() {
        setGraphic(null);
        super.cancelEdit();
        setText(oldValue);
    }
}
