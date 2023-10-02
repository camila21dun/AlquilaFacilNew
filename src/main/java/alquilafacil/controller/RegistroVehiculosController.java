package alquilafacil.controller;

import java.net.URL;
import java.util.ResourceBundle;


import alquilafacil.application.Main;
import alquilafacil.exception.AlquilaException;
import alquilafacil.model.AlquilaFacil;

import alquilafacil.model.Vehiculo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;



public class RegistroVehiculosController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRegistrarVehiculo;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<String> cbTipoCaja;

    @FXML
    private TextField txtKilometraje;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtPlaca;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtPuertas;

    @FXML
    private TextField txtReferencia;

    @FXML
    private TextField txtUrlFoto;
    private Main main;
    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();


    @FXML
    void registrarVehiculoEvent(ActionEvent event) {
        registrarVehiculoAction();

    }

    private void registrarVehiculoAction() {

        String placa = txtPlaca.getText();
        String referencia = txtReferencia.getText();
        String marca = txtMarca.getText();
        //String modelo = txtModelo.getText();
        String foto =  txtUrlFoto.getText();
        int kilometraje = Integer.parseInt(txtKilometraje.getText());
        float precio =  Float.parseFloat(txtPrecio.getText());
        int puertas =  Integer.parseInt(txtPuertas.getText());
        boolean tipoCaja = cbTipoCaja.getValue().isEmpty();
        int modelo = 0;
        if (verificarEntero(txtModelo.getText())){
            System.out.println("si lo es"+modelo);
            modelo = Integer.parseInt(txtModelo.getText());
        }else{
            System.out.println("no es un numero: "+modelo);
        }

        if(verificarCampos(placa,referencia,marca,modelo,foto,kilometraje,precio,tipoCaja,puertas))
        {
            try {

                Vehiculo vehiculo = new Vehiculo(placa, referencia, marca, modelo, foto, kilometraje, precio, tipoCaja, puertas);
                alquilaFacil.registrarVehiculo(placa, referencia, marca, modelo, foto, kilometraje, precio, tipoCaja, puertas);


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Se ha registrado correctamente el vehiculo de placa  " + vehiculo.getPlaca());
                alert.show();
                limpiarTexto();

            } catch (AlquilaException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.setHeaderText("");
                alert.show();

            } catch (NumberFormatException e1) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Tenga en cuenta que el número de puertas, modelo, precio por día y kilometraje deben ser números enteros");
                alert.setHeaderText(null);
                alert.show();


            }
        }else{
            AlquilaFacil.mensajeAlerta("Error", "Completa los campos faltantes");
        }


    }

    private boolean verificarEntero(String modelo) {

        try {
            if (Integer.parseInt(modelo) > 0 ){
                return true;
            }
        }catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setHeaderText("");
            alert.show();
        }

        System.out.println("no es un numero: "+modelo);
        return false;
    }

    private boolean verificarCampos(String placa, String referencia, String marca, int  modelo, String foto, int kilometros, float precio, boolean esAutomatico,int numPuertas) {

        if (placa.equals("")){
            return false;
        }
        if (referencia.equals("")){
            return false;
        }
        if (marca.equals("")){
            return false;

        }
        if (modelo < 0){
            return false;
        }
        if (kilometros < 0){
            return false;
        }
        if (precio < 0 ){
            return false;
        }
        if (esAutomatico= Boolean.parseBoolean(null)){
            return false;
        }
        if (numPuertas < 0){
            return false;
        }
        return true;
    }



    @FXML
    void volverEvent(ActionEvent event) {
        this.main.mostrarInicio();

    }

    @FXML
    void initialize() {
        cbTipoCaja.getItems().addAll("Manual","Automatica");
    }

    public void setApplication(Main main) {
        this.main=main;
    }

    private void limpiarTexto() {
        txtPuertas.setText("");
        txtKilometraje.setText("");
        txtUrlFoto.setText("");
        txtMarca.setText("");
        txtReferencia.setText("");
        txtModelo.setText("");
        txtPrecio.setText("");
        txtPlaca.setText("");
        cbTipoCaja.commitValue();
    }
}
