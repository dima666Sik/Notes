package guru.work.prog.swing;

import guru.work.prog.controller.DBConnector;
import guru.work.prog.iface.Checker;
import guru.work.prog.query.QueryNotes;
import guru.work.prog.query.QueryUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NoteForm extends JDialog implements Checker {
    private JTextPane textPaneTitle;
    private JTextPane textText;
    private JPanel createNotePanel;
    private JButton finishButton;
    private JButton cancelButton;

    public NoteForm(JFrame jFrameParent, String login, StringBuilder password) {
        super(jFrameParent);
        setUndecorated(true);
        setContentPane(createNotePanel);
        setMinimumSize(new Dimension(450, 375));
        setModal(true);
        setLocationRelativeTo(jFrameParent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        finishButton.addActionListener(e -> {
            dispose();
            //
            try {
                ResultSet resultSetCountNotes = check(QueryUser.getIdNote(login, password));
                resultSetCountNotes.next();

                int id_note = resultSetCountNotes.getInt(1);
                createNote(id_note);
                new NotesMenu(null, id_note, login, password);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    ResultSet resultSetCountNotes = check(QueryUser.getIdNote(login, password));
                    resultSetCountNotes.next();

                    int id_note = resultSetCountNotes.getInt(1);
                    new NotesMenu(null, id_note, login, password);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        setVisible(true);
    }

    private void createNote(int id_note) throws SQLException {
        Connection connection = DBConnector.getConnector();
        QueryNotes.addNote(connection,textPaneTitle.getText(),textText.getText(),id_note);
    }

    public NoteForm(JFrame jFrameParent, String title, String text, String login, StringBuilder password,long id_notes) {
        super(jFrameParent);
        textPaneTitle.setText(title);
        textText.setText(text);
        setUndecorated(true);
        setContentPane(createNotePanel);
        setMinimumSize(new Dimension(450, 375));
        setModal(true);
        setLocationRelativeTo(jFrameParent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        finishButton.addActionListener(e -> {
            dispose();
            //
            try {
                ResultSet resultSetCountNotes = check(QueryUser.getIdNote(login, password));
                resultSetCountNotes.next();
                int id_note = resultSetCountNotes.getInt(1);
                readAndPushText(id_notes);
                new NotesMenu(null, id_note, login, password);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    ResultSet resultSetCountNotes = check(QueryUser.getIdNote(login, password));
                    resultSetCountNotes.next();

                    int id_note = resultSetCountNotes.getInt(1);
                    new NotesMenu(null, id_note, login, password);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        setVisible(true);
    }

    private void readAndPushText(long id_notes) throws SQLException {
        Connection connection = DBConnector.getConnector();
        Statement st = connection.createStatement();
        int value = st.executeUpdate(QueryNotes.updateNote(textPaneTitle.getText(), textText.getText(), id_notes));
        if (value == 0) JOptionPane.showMessageDialog(this,
                "Update not successful!",
                "Try again",
                JOptionPane.ERROR_MESSAGE);
    }

}
