package ui.controls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import transfer.objects.Product;
import transfer.objects.Supplier;
import ui.controller.LocalDateStringConverter;

public class SupplierDatePickerTableCell extends TableCell<Supplier, LocalDate> {

    private DatePicker datePicker;
    private String oldValue;
    private static DateFormat dateFormat;
    private static DateTimeFormatter dateFormatter;

    public SupplierDatePickerTableCell() {
        dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        dateFormatter = DateTimeFormatter.ofPattern(SimpleDateFormat.class.cast(dateFormat).toPattern());

    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            datePicker = new DatePicker();
            datePicker.setConverter(new LocalDateStringConverter(dateFormat));
            oldValue = datePicker.getEditor().getText();

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

    @Override
    public void cancelEdit() {
        setGraphic(null);
        super.cancelEdit();
        setText(oldValue);
    }

}

