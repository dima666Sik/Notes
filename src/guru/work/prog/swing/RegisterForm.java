package guru.work.prog.swing;

import guru.work.prog.controller.DBConnector;
import guru.work.prog.iface.Checker;
import guru.work.prog.iface.Encryption;
import guru.work.prog.iface.Randomize;
import guru.work.prog.model.User;
import guru.work.prog.query.QueryNoteId;
import guru.work.prog.query.QueryUser;

import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Arrays;

public class RegisterForm extends JDialog implements Randomize, Encryption, Checker {
    private JTextField nameField;
    private JPasswordField passwordField;
    private JTextField loginField;
    private JPasswordField confirmPasswordField;
    private JButton cancelBtn;
    private JButton registrationBtn;
    private JPanel registrationPanel;
    private JButton signInButton;
    private User user = null;

    public RegisterForm(JFrame jFrameParent) {
        super(jFrameParent);
        setUndecorated(true);
        setContentPane(registrationPanel);
        setMinimumSize(new Dimension(480, 375));
        setModal(true);
        setLocationRelativeTo(jFrameParent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        registrationBtn.addActionListener(e -> registerUser());
        cancelBtn.addActionListener(e -> dispose());
        signInButton.addActionListener(e -> {
            dispose();
            new LoginForm(null);
        });
        setVisible(true);
    }

    private int generatorId(int min, int max) throws SQLException {
        if (min <= 0) min = 1;
        int genRndVal = generateRandomize(min, max);
        while (true) {
            if (!checkNext(QueryUser.checkIdNote(genRndVal))) break;
            else genRndVal = generateRandomize(min, max);
        }
        return genRndVal;
    }

    private void registerUser() {
        if (user == null) {
            if (!nameField.getText().isEmpty() && !loginField.getText().isEmpty() && passwordField.getPassword().length != 0) {
                if (Arrays.equals(passwordField.getPassword(), confirmPasswordField.getPassword())) {
                    if (passwordField.getPassword().length >= 6) {
                        try {
                            StringBuilder passwordEnc = encryptionSHA256(passwordField.getPassword());
                            user = new User(nameField.getText(), loginField.getText(), passwordEnc, generatorId(1, 1_000_000));
                            if (!checkNext(QueryUser.searchUser(loginField.getText(), passwordEnc))) {
                                addUserInDB(user.getName(), user.getLogin(), user.getPassword(), user.getId_note());
                                user.clearPassword();
                                dispose();
                                //Go to home page
                                new MainMenuForm(null);
                            } else {
                                JOptionPane.showMessageDialog(this,
                                        "User please change login and password!",
                                        "Try again",
                                        JOptionPane.ERROR_MESSAGE);
                                dispose();
                                new RegisterForm(null);
                            }
                        } catch (NoSuchAlgorithmException | SQLException e) {
                            e.printStackTrace();
                        }
                    } else JOptionPane.showMessageDialog(this,
                            "Password length less 6 symbols...",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                } else JOptionPane.showMessageDialog(this,
                        "Password and confirm password not equals!",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            } else JOptionPane.showMessageDialog(this,
                    "Please fill all fields...",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addUserInDB(String name, String login, StringBuilder password, long id_note) throws SQLException {
        Connection connection = DBConnector.getConnector();

        QueryUser.addUser(connection, name, login, password, id_note);
        QueryNoteId.addNoteId(connection, id_note);

    }
}
