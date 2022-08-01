package guru.work.prog.controller;

import guru.work.prog.dataenv.Environment;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    static private Connection connection;

    public static Connection getConnector(){
        if(connection==null) {
            try {
                connection = DriverManager.getConnection(Environment.DB_URL.getEnvironmentValue(),
                        Environment.USERNAME.getEnvironmentValue(),
                        Environment.PASSWORD.getEnvironmentValue()
                );
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,
                        "Connect to database not successful!",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        return connection;
    }
}
