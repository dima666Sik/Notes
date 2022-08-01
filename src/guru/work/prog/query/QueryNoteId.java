package guru.work.prog.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryNoteId {
    public static void addNoteId(Connection connection, long id_note) throws SQLException {
        String sqlRequest1 = "INSERT INTO notebooks_id(id_note) VALUES(?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sqlRequest1);
        preparedStatement1.setLong(1, id_note);
        int addedRows1 = preparedStatement1.executeUpdate();

        if (addedRows1 > 0) System.out.println("Add id note user in db successful!");
    }
}
