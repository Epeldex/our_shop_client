package ui.controls;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import transfer.objects.Product;
import transfer.objects.Supplier;

/**
 * A custom TableCell implementation for editing Supplier values in a ComboBox
 * within a table. This cell is specifically designed for tables with Product
 * objects, allowing the selection or editing of associated Suppliers.
 */
public class EditableComboBoxTableCell extends TableCell<Product, Supplier> {

    private final ComboBox<Supplier> comboBox = new ComboBox<>();
    private static ObservableList<Supplier> observableSupplierList;
    private static String oldValue = new String(" ");

    /**
     * Creates an EditableComboBoxTableCell with a list of suppliers.
     *
     * @param sList The list of Supplier objects to be displayed in the
     * ComboBox.
     */
    public EditableComboBoxTableCell(List<Supplier> sList) {
        observableSupplierList = FXCollections.observableArrayList(sList);
        comboBox.setEditable(true);
        comboBox.setConverter(new StringConverter<Supplier>() {
            @Override
            public String toString(Supplier supplier) {
                return supplier == null ? "" : supplier.getName();
            }

            @Override
            public Supplier fromString(String string) {
                for (Supplier s : observableSupplierList) {
                    if (s.getName().equals(string)) {
                        return s;
                    }
                }
                return new Supplier();
            }
        });
        comboBox.setOnAction(event -> commitEdit(comboBox.getValue()));
    }

    /**
     * Starts editing the cell, replacing text with the ComboBox.
     */
    @Override
    public void startEdit() {
        super.startEdit();
        setText(null);
        setGraphic(comboBox);
        oldValue = getItem().getName();
        comboBox.setItems(observableSupplierList);
    }

    /**
     * Cancels the editing and restores the cell to its original state.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
        updateItem(getItem(), isEmpty());
    }

    /**
     * Updates the item of the TableCell.
     *
     * @param item The Supplier item that is to be displayed in the cell.
     * @param empty A boolean flag indicating whether the cell is empty.
     */
    @Override
    public void updateItem(Supplier item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setText(null);
                setGraphic(comboBox);
            } else {
                setText(item != null && !item.isEmpty() ? item.getName() : oldValue);
                setGraphic(null);
            }
        }
    }
}
