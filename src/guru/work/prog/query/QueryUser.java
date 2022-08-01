package guru.work.prog.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryUser {
    public static String checkIdNote(int rndId){
        return String.format("SELECT `id_note` FROM `notebooks_id` WHERE `id_note` = %d ", rndId);
    }

    public static String getIdNote(String login, StringBuilder password){
        return String.format("SELECT `id_note` FROM `users` WHERE `login` = '%s' and `password` = '%s' ", login, password);
    }

    public static void addUser(Connection connection, String name, String login, StringBuilder password, long id_note) throws SQLException {
        String sqlRequest = "INSERT INTO users(name,login,password,id_note) VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, login);
        preparedStatement.setString(3, new String(password));
        preparedStatement.setLong(4, id_note);
        int addedRows = preparedStatement.executeUpdate();

        if (addedRows > 0) System.out.println("Add user in db successful!");
    }

    public static String searchUser(String login, StringBuilder password) {
        return String.format("SELECT `id_user` FROM `users` WHERE `login` = '%s' AND `password` = '%s' ", login,password);
    }
}
