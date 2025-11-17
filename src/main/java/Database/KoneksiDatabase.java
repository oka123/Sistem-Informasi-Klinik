/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
//import java.sql.*;
 
public class KoneksiDatabase {

    Connection conn;
    public KoneksiDatabase(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://klinik-klinik.c.aivencloud.com:12241/klinik?useCompression=true", "avnadmin", "AVNS_5e6NcoUQA0Q-vk_YdAs");
        }
        catch(ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    // Atribut untuk koneksi database
    //private final String hostname;
//    private final String database;
//    private final String port;
//    private final String username;
//    private final String password;
    
//    public KoneksiDatabase () {
//        this.hostname = "ywhq0t.h.filess.io";
//        this.database = "klinik_uncleroll";
//        this.port = "61002";
//        this.username = "klinik_uncleroll";
//        this.password = "31f424b42139b5f830f0f408f0892aaf30d7f095";
//    }
    
    // Metode untuk membuat koneksi ke database
//    public Connection connect() throws SQLException {
//        // Menyusun URL koneksi dengan menggunakan atribut
//        String url = String.format("jdbc:mysql://%s:%s/%s?useCompression=true", hostname, port, database);
//        // Mengembalikan koneksi
//        return DriverManager.getConnection(url, username, password);
//    }
    
    public Connection getConnection() throws SQLException{
        return conn;
    }
}
