package ui.controls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import transfer.objects.Product;

public class DatePickerTableCell extends TableCell<Product, Date> {

    private DatePicker datePicker;
    private static DateFormat dateFormatter;

    public DatePickerTableCell() {
        dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            datePicker = new DatePicker();

            // Set prompt text with an example date
            datePicker.setPromptText(SimpleDateFormat.class.cast(dateFormatter).toPattern());
            datePicker.setOnAction((event) -> {
                commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            });
            setText(null);
            setGraphic(datePicker);
        }
    }

    @Override
    public void updateItem(Date item, boolean empty) {
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
    }

}
