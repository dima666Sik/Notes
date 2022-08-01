package guru.work.prog.swing;

import javax.swing.*;
import java.awt.*;

public class MainMenuForm extends JDialog {
    private JButton registrationButton;
    private JButton signInButton;
    private JButton exitButton;
    private JPanel menuPanel;

    public MainMenuForm(JFrame jFrameParent) {
        super(jFrameParent);
        setUndecorated(true);
        setContentPane(menuPanel);
        setMinimumSize(new Dimension(360, 250));
        setModal(true);
        setLocationRelativeTo(jFrameParent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        registrationButton.addActionListener(e -> {
            dispose();
            new RegisterForm(null);
        });
        signInButton.addActionListener(e -> {
            dispose();
            new LoginForm(null);
        });
        exitButton.addActionListener(e -> {
            dispose();
        });
        setVisible(true);
    }
}
