/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author David
 */
public class ConectarBD {
    
    Connection cnx;
    Statement st;
    PreparedStatement pstm;
    ResultSet rs;
    static String userBD, pwd;
    ObservableList<Chat> listaChat = FXCollections.observableArrayList();
    
    public ConectarBD(String usuario, String clave){
        ConectarBD.userBD = usuario;
        ConectarBD.pwd = clave;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cnx=DriverManager.getConnection("jdbc:mysql://remotemysql.com/"+userBD+"", userBD, pwd);
            st=cnx.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConectarBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConectarBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ConectarBD() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cnx=DriverManager.getConnection("jdbc:mysql://remotemysql.com/"+userBD+"", userBD, pwd);
            st=cnx.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConectarBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConectarBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getUserBD() {
        return userBD;
    }

    public void setUserBD(String userBD) {
        ConectarBD.userBD = userBD;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        ConectarBD.pwd = pwd;
    }

    public boolean iniciarSesion(String usuario, String clave) {
        boolean resultado = false;
        try {
            String cadenaSql = "select * from user where usuario=? and clave=?";
            pstm = cnx.prepareStatement(cadenaSql);
            pstm.setString(1, usuario);
            pstm.setString(2, clave);
            rs = pstm.executeQuery();
            if(rs.next()){
                resultado = true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConectarBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public boolean registrarUsuario(String usuario, String clave) {
        boolean resultado = false;
        try {
            String cadenaSql = "insert into user(usuario, clave) values(?, ?)";
            pstm = cnx.prepareStatement(cadenaSql);
            pstm.setString(1, usuario);
            pstm.setString(2, clave);
            int r = pstm.executeUpdate();
            if(r==1){
                resultado = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConectarBD.class.getName()).log(Level.SEVERE, null, ex);
        }
       return resultado;
    }

    public ObservableList<Chat> listarMensajes() {
        
        try {
            String cadenaSql = "select * from chat order by 1 desc";
            rs = st.executeQuery(cadenaSql);
            while(rs.next()){
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                String fecha = formato.format(rs.getTimestamp(3));
                Chat c = new Chat(rs.getInt(1), rs.getString(2), fecha, rs.getString(4));
                listaChat.add(c);
                System.out.println(fecha);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConectarBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaChat;
    }

    public boolean enviarMensaje(String usuario,  Timestamp fecha, String mensaje) {
        boolean resultado = false;
        try {
            String cadenaSql = "insert into chat(usuario, fecha, mensaje) values(?, ?, ?)";
            pstm = cnx.prepareStatement(cadenaSql);
            pstm.setString(1, usuario);
            pstm.setTimestamp(2, fecha);
            pstm.setString(3, mensaje);
            int r = pstm.executeUpdate();
            if(r==1){
                resultado = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConectarBD.class.getName()).log(Level.SEVERE, null, ex);
        }
       return resultado;
    }
    
    
    
}
