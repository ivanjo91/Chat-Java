/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.ConectarBD;

/**
 *
 * @author David
 */
public class ControladorPantallaLogin implements Initializable {
    
    ConectarBD cnx;
    String[] listaServidores = {"servidor1", "servidor2", "servidor3"};
    public Stage escenarioLogin;
    public String userBD, pwd;
    
    private Label label;
    @FXML
    private AnchorPane ac;
    @FXML
    private ComboBox<String> comboBoxServidor;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtClave;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Button btnRegistrarse;
    @FXML
    private Button btnSalir;
    @FXML
    private Label lbUser;
    @FXML
    private Label lbClave;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        comboBoxServidor.setItems(FXCollections.observableArrayList(listaServidores));
    }    

    @FXML
    private void comboBoxServidor(ActionEvent event) {
        String opcion = comboBoxServidor.getValue();
        switch(opcion){
            case "servidor1":
                userBD = "Pr1mdxAdrh";
                pwd = "fNBUrxid1O";
                break;
            case "servidor2":
                userBD = "j3vYEeCG3p";
                pwd = "GPpsVJJ4SK";
                break;
            case "servidor3":
                userBD = "BergjSfgJL";
                pwd = "2WkhdwjZWd";
                break;
        }
        cnx = new ConectarBD(userBD, pwd);
    }

    @FXML
    private void btnIniciarSesion_OnAction(ActionEvent event) {
        if(cnx.iniciarSesion(txtUsuario.getText(), txtClave.getText())){
            try {
                //Cargar en un loader todos los componentes xml de
                // la siguiente ventana
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VentanaChat.fxml"));
                AnchorPane ventanaDos = (AnchorPane)loader.load();
                //Construyo el escenario
                Stage escenario = new Stage();
                escenario.setTitle("Examen JavaFX Chat");
                Scene escena = new Scene(ventanaDos);
                escenario.setScene(escena);
                ControladorVentanaChat controlador2 = loader.getController();
                controlador2.lbUsuario.setText(txtUsuario.getText());
                controlador2.escenarioChat = escenario;
                escenario.setResizable(false);
                escenario.initStyle(StageStyle.UNDECORATED);
                escenario.show();
                //Cerrar ventana de login
                Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
                stage.close();
                
            } catch (IOException ex) {
                Logger.getLogger(ControladorPantallaLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            Alert alerta=new Alert(Alert.AlertType.ERROR,"Datos incorrectos");
            alerta.show();
        }
    }

    @FXML
    private void btnRegistrarse_OnAction(ActionEvent event) {
        
        String usuario = txtUsuario.getText();
        String clave = txtClave.getText();
        
        if(usuario.isEmpty()&&clave.isEmpty()){
            Alert alerta=new Alert(Alert.AlertType.ERROR,"Campos vacíos");
            alerta.show();
        }
        else{
            if(cnx.registrarUsuario(txtUsuario.getText(), txtClave.getText())){
                Alert alerta=new Alert(Alert.AlertType.INFORMATION,"Usuario registrado con éxito");
                alerta.show();
            }   
            else{
            Alert alerta=new Alert(Alert.AlertType.ERROR,"No se ha podido registrar ese usuario");
            alerta.show();
            }
        }
        
    }

    @FXML
    private void btnSalir_OnAction(ActionEvent event) {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Examen JavaFX Chat");
        alert.setHeaderText("¿Seguro que quieres salir?");
        alert.setContentText("¿Cerrar aplicación?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();
        }
    }
    
    
    
}
