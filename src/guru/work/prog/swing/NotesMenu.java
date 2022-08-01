package guru.work.prog.swing;

import guru.work.prog.controller.DBConnector;
import guru.work.prog.iface.Checker;
import guru.work.prog.query.QueryNotes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NotesMenu extends JDialog implements Checker {
    private final static int COUNT_COLUMN = 3;
    private JPanel notesMenuPanel;
    private JTable table;
    private JLabel buttonAddNote;
    private JButton exitButton;
    private JButton chooseButton;
    private JLabel buttonRemoveNote;

    public NotesMenu(JFrame jFrameParent, long id_note, String login, StringBuilder password) {
        super(jFrameParent);
        setUndecorated(true);
        setContentPane(notesMenuPanel);
        createTable(id_note, login, password);
        buttonAddNote.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new NoteForm(null, login, password);
            }
        });
        setMinimumSize(new Dimension(600, 305));
        setModal(true);
        setLocationRelativeTo(jFrameParent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        exitButton.addActionListener(e -> {
            dispose();
            new MainMenuForm(null);
        });
        setVisible(true);
    }

    private void createTable(long id_note, String login, StringBuilder password) {
        Object[][] data = null;

        try {
            ResultSet resultSetCountNotes = check(QueryNotes.getContNotes(id_note));
            resultSetCountNotes.next();
            int countRow = resultSetCountNotes.getInt(1);
            data = new Object[countRow][COUNT_COLUMN];

            ResultSet resultSetNotes = check(QueryNotes.getNotes(id_note));

            for (int i = 0; resultSetNotes.next(); i++) {
                data[i][0] = resultSetNotes.getLong(1);
                data[i][1] = resultSetNotes.getString(2);
                data[i][2] = resultSetNotes.getString(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        DefaultTableModel defaultTableModel = new DefaultTableModel(
                data, new String[]{"ID", "Title", "Note"}
        );

        table.setModel(defaultTableModel);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chooseButton.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {
                dispose();
                new NoteForm(null,
                        (String) defaultTableModel.getValueAt(table.getSelectedRow(), 1),
                        (String) defaultTableModel.getValueAt(table.getSelectedRow(), 2),
                        login,
                        password,
                        (Long) defaultTableModel.getValueAt(table.getSelectedRow(), 0)
                );
            }
        });

        buttonRemoveNote.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (table.getSelectedRow() != -1) {
                    dispose();
                    Connection connection = DBConnector.getConnector();
                    Statement st;
                    try {
                        st = connection.createStatement();
                        int value = st.executeUpdate(QueryNotes.deleteNote((Long) defaultTableModel.getValueAt(table.getSelectedRow(), 0)));
                        if (value == 0) JOptionPane.showMessageDialog(null,
                                "Delete not successful!",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    new NotesMenu(null, id_note, login, password);
                }
            }
        });
    }
}
