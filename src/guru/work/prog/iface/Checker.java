package guru.work.prog.iface;

import guru.work.prog.controller.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface Checker {
    default ResultSet check(String query) throws SQLException {
        Connection connection = DBConnector.getConnector();
        Statement st = connection.createStatement();
        return st.executeQuery(query);
    }

    default boolean checkNext(String query) throws SQLException {
        return check(query).next();
    }

}
