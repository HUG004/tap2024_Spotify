package com.example.tap2024.components;

import javafx.scene.control.*;
import java.util.Optional;
import java.util.function.Consumer;

public class ButtonCell<T> extends TableCell<T, String> {
    Button btnCelda;
    private final Consumer<T> editAction;
    private final Consumer<T> deleteAction;

    public ButtonCell(String label, Consumer<T> editAction, Consumer<T> deleteAction) {
        btnCelda = new Button(label);
        this.editAction = editAction;
        this.deleteAction = deleteAction;
        btnCelda.setOnAction(event -> EventoBoton(label));
    }

    @Override
    protected void updateItem(String s, boolean b) {
        super.updateItem(s, b);
        if (!b) {
            this.setGraphic(btnCelda);
        }
    }

    private void EventoBoton(String label) {
        T item = this.getTableView().getItems().get(this.getIndex());

        if (label.equals("Editar")) {
            editAction.accept(item);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText("¿Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> opcion = alert.showAndWait();
            if (opcion.get() == ButtonType.OK) {
                deleteAction.accept(item);
                this.getTableView().refresh();
            }
        }
    }
}
