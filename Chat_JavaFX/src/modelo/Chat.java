/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


/**
 *
 * @author David
 */
public class Chat {
    
    private SimpleIntegerProperty id;
    private SimpleStringProperty usuario;
    private SimpleStringProperty fecha;
    private SimpleStringProperty mensaje;

    public Chat() {
    }

    public Chat(int id, String usuario, String fecha, String mensaje) {
        this.id = new SimpleIntegerProperty(id);
        this.usuario = new SimpleStringProperty(usuario);
        this.fecha = new SimpleStringProperty(fecha);
        this.mensaje = new SimpleStringProperty(mensaje);
    }

    public int getId() {
        return id.get();
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario.get();
    }

    public void setUsuario(SimpleStringProperty usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha.get();
    }

    public void setFecha(SimpleStringProperty fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje.get();
    }

    public void setMensaje(SimpleStringProperty mensaje) {
        this.mensaje = mensaje;
    }

    

    
    
    
    
}
