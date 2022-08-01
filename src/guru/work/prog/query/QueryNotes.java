package guru.work.prog.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryNotes {
    public static String getContNotes(long id_note) {
        return String.format("SELECT COUNT(`id_notes`) FROM `notebooks` WHERE `id_note`=%d", id_note);
    }

    public static String getNotes(long id_note) {
        return String.format("SELECT `id_notes`,`header`,`text` FROM `notebooks` WHERE `id_note`=%d", id_note);
    }

    public static void addNote(Connection connection, String title, String text, long id_note) throws SQLException {
        String sqlRequest = "INSERT INTO notebooks(header,text,id_note) VALUES(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, text);
        preparedStatement.setLong(3, id_note);
        int addedRows = preparedStatement.executeUpdate();

        if (addedRows > 0) System.out.println("Add user in db successful!");
    }

    public static String updateNote(String title, String text, long id_notes) throws SQLException {
        return String.format("UPDATE notebooks SET `header` = '%s', `text` = '%s' WHERE `id_notes` = %d", title, text, id_notes);
    }

    public static String deleteNote(long id_notes) throws SQLException {
        return String.format("DELETE FROM notebooks WHERE `id_notes` = %d", id_notes);
    }
}
