/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Chat;
import modelo.ConectarBD;

/**
 * FXML Controller class
 *
 * @author David
 */
public class ControladorVentanaChat implements Initializable {
    
    public ConectarBD cnx = new ConectarBD();
    public String userBD, pwd;
    public Stage escenarioChat;
    ObservableList<Chat> listaChat = FXCollections.observableArrayList();

    @FXML
    public Label lbUsuario;
    @FXML
    private Button btnVolver;
    @FXML
    private Button btnListar;
    @FXML
    private Button btnEnviar;
    @FXML
    private TextField txtMensaje;
    @FXML
    private TableView<Chat> tableViewChat;
    @FXML
    private TableColumn<Chat, Integer> tableColumID;
    @FXML
    private TableColumn<Chat, String> tableColumUsuario;
    @FXML
    private TableColumn<Chat, String> tableColumFecha;
    @FXML
    private TableColumn<Chat, String> tableColumMensaje;
    public Label lbUno;
    public Label lbDos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableColumID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        tableColumFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tableColumMensaje.setCellValueFactory(new PropertyValueFactory<>("mensaje"));
        listaChat = cnx.listarMensajes();
        tableViewChat.setItems(listaChat);
        txtMensaje.requestFocus();
    }    

    @FXML
    private void btnVolver_OnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Examen JavaFX Chat");
        alert.setHeaderText("¿Seguro que quieres salir del chat?");
        alert.setContentText("¿Volver atrás?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            
            try {
                //Cargar en un loader todos los componentes xml de
                // la siguiente ventana
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PantallaLogin.fxml"));
                AnchorPane ventanaDos = (AnchorPane)loader.load();
                //Construyo el escenario
                Stage escenario = new Stage();
                escenario.setTitle("Examen JavaFX Chat");
                Scene escena = new Scene(ventanaDos);
                escenario.setScene(escena);
                ControladorPantallaLogin controlador2 = loader.getController();
                controlador2.escenarioLogin = escenario;
                escenario.setResizable(false);
                escenario.initStyle(StageStyle.UNDECORATED);
                escenario.show();
                //Cerrar ventana de login
                Stage stage = (Stage) btnVolver.getScene().getWindow();
                stage.close();
                
            } catch (IOException ex) {
                Logger.getLogger(ControladorPantallaLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void btnListar_OnAction(ActionEvent event) {
        recargarChat();
    }

    @FXML
    private void btnEnviar_OnAction(ActionEvent event) {
        Date fechaDate = new Date();
        Timestamp fechaStamp = new Timestamp(fechaDate.getTime());
        if(!cnx.enviarMensaje(lbUsuario.getText(), fechaStamp, txtMensaje.getText())){
            Alert alerta=new Alert(Alert.AlertType.ERROR,"Error al enviar mensaje");
            alerta.show();
        }
        recargarChat();
        txtMensaje.setText("");
        txtMensaje.requestFocus();
    }

    public ConectarBD getCnx() {
        return cnx;
    }

    public void setCnx(ConectarBD cnx) {
        this.cnx = cnx;
    }
    
    private void recargarChat(){
        tableViewChat.getItems().clear();
        listaChat = cnx.listarMensajes();
        tableViewChat.setItems(listaChat);
    }
    
}
