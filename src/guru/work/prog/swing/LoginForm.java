package guru.work.prog.swing;

import guru.work.prog.iface.Checker;
import guru.work.prog.iface.Encryption;
import guru.work.prog.query.QueryNotes;
import guru.work.prog.query.QueryUser;

import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends JDialog implements Encryption, Checker {
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel loginPanel;
    private JButton registrationButton;

    public LoginForm(JFrame jFrameParent) {
        super(jFrameParent);
        setUndecorated(true);
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450,305));
        setModal(true);
        setLocationRelativeTo(jFrameParent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        registrationButton.addActionListener(e -> {
            dispose();
            new RegisterForm(null);
        });

        okButton.addActionListener(e -> {
            authorizationUser();
        });

        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
    }

    private void authorizationUser() {
        try {
            searchUserInDB(loginField.getText(),encryptionSHA256(passwordField.getPassword()));
        } catch (NoSuchAlgorithmException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchUserInDB(String login, StringBuilder password) throws SQLException {
        if(checkNext(QueryUser.searchUser(login, password))) {
            dispose();
            ResultSet resultSetCountNotes = check(QueryUser.getIdNote(login,password));
            resultSetCountNotes.next();
            int countRow = resultSetCountNotes.getInt(1);
            new NotesMenu(null,countRow,login,password);
        }else JOptionPane.showMessageDialog(this,
                "User isn`t defined",
                "Try again",
                JOptionPane.ERROR_MESSAGE);
    }
}
