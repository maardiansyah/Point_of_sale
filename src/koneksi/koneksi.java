/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author aldia
 */
public class koneksi {
    
    public static String PathReport=System.getProperty("user.dir")+"src/laporan";
    static Connection con;
    
    public static Connection koneksi(){
        if (con==null){
            //postgresqlDataSource data = new PostgresqlDataSource();
           MysqlDataSource data = new MysqlDataSource();
            data.setDatabaseName("port_of_sale");
            data.setUser("root");
            data.setPassword("");
            try{
                con = data.getConnection();
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return con;
    }

    
    
    
}
