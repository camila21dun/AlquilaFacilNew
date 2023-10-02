package alquilafacil.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import alquilafacil.application.Main;
import alquilafacil.exception.AlquilaException;
import alquilafacil.model.AlquilaFacil;
import alquilafacil.model.Cliente;
import alquilafacil.model.Vehiculo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import lombok.Setter;

public class RegistroAlquilerController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRegistrarAlquiler;

    @FXML
    private TableColumn<Vehiculo, String> columModelo;

    @FXML
    private TableColumn<Vehiculo, String> columPlaca;

    @FXML
    private TableColumn<Vehiculo, Float> columPrecioDia;

    @FXML
    private TableColumn<Vehiculo, String> columReferencia;

    @FXML
    private DatePicker dateFinal;

    @FXML
    private DatePicker dateInicio;

    @FXML
    private TableView<Vehiculo> tableVehiculos;

    @FXML
    private TextField txtCedulaCliente;

    private AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();

    @Setter @Getter
    private Main application;

    @FXML
    void registrarAlquilerEvent(ActionEvent event) {
        registrarAlquilerAction();

    }

    private void registrarAlquilerAction() {
        // Obtener los datos del formulario
        Vehiculo vehiculoSeleccionado = tableVehiculos.getSelectionModel().getSelectedItem();
        String cedulaCliente = txtCedulaCliente.getText();
        LocalDate fechaInicio = dateInicio.getValue();
        LocalDate fechaFinal = dateFinal.getValue();

        try {
            // Validar que se hayan proporcionado todos los datos requeridos
            if (fechaInicio == null || fechaFinal == null || cedulaCliente.isEmpty() || vehiculoSeleccionado == null) {
                mostrarError("Por favor, complete todos los campos.");
                return;
            }

            Cliente cliente = alquilaFacil.obtenerCliente(cedulaCliente);
            if (cliente == null) {
                mostrarError("El cliente con cédula " + cedulaCliente + " no está registrado. Registre al cliente primero.");
                return;
            }

            //double costoTotal = alquilaFacil.registrarAlquiler(cedulaCliente, vehiculoSeleccionado.getPlaca(), fechaInicio, fechaFinal).getValorTotal();

            // Mostrar la factura al usuario
            mostrarFactura(500);

            // Limpiar el formulario si es necesario
            limpiarFormulario();
        /*} catch (AlquilaException e) {
            mostrarError("Error en el registro de alquiler: " + e.getMessage());
        */}catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
        }
    }

    @FXML
    void initialize() {

    }

    private void mostrarFactura(double costoTotal) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Factura de Alquiler");
        alert.setHeaderText("Detalle del Alquiler");
        alert.setContentText("Costo Total: $" + costoTotal);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ha ocurrido un error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarFormulario() {
        // Limpia los campos del formulario
        txtCedulaCliente.clear();
        dateInicio.setValue(null);
        dateFinal.setValue(null);
        tableVehiculos.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        columModelo.setCellValueFactory( new PropertyValueFactory<>("modelo"));
        columReferencia.setCellValueFactory( new PropertyValueFactory<>("referencia"));
        columPrecioDia.setCellValueFactory( new PropertyValueFactory<>("Precio"));
        tableVehiculos.setItems(FXCollections.observableArrayList( alquilaFacil.getVehiculos() ));
    }
}
