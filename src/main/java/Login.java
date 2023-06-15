import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Login JDialog class that holds the username and password of the teacher. Creates a JDialog when called that asks for
 * the username and password to access the teacher side.
 */
public class Login extends JDialog {
    private final JTextField userInputtedUsername;
    private final JPasswordField userInputtedPassword;
    private final String userName = "IAmTeacher";
    private final String password = "TeacherIAm";

    /**
     * Creates a JDialog when called that asks for the username and password to access the teacher side.
     * @param parent panel that the JDialog will be set relative to.
     */
    public Login(JPanel parent) {
        MainController mainController = new MainController();
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        JLabel lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);

        userInputtedUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(userInputtedUsername, cs);

        JLabel lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        userInputtedPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(userInputtedPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));

        JButton btnLogin = new JButton("Login");
        JButton btnCancel = new JButton("Cancel");
        btnLogin.addActionListener(mainController);
        btnCancel.addActionListener(mainController);

        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    /**
     * Get the username the user entered.
     * @return String of the username the user entered.
     */
    public String getUsername() {
        return userInputtedUsername.getText().trim();
    }

    /**
     * Get the password the user entered.
     * @return String of the password the user entered.
     */
    public String getPassword() {
        return new String(userInputtedPassword.getPassword());
    }

    /**
     * Check the user entered username/password against the teacher entered one.
     * @return true, if and only if, the user entered username/password is the same as the set username/password and
     * false otherwise.
     */
    public boolean isSucceeded() {
        boolean result = authenticate(getUsername(), getPassword());
        userInputtedUsername.setText("");
        userInputtedPassword.setText("");
        if(!result) {
            JOptionPane.showMessageDialog(Login.this,
                    "Invalid username or password",
                    "Login",
                    JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    private boolean authenticate(String username, String password) {
        return username.equals(this.userName) && password.equals(this.password);
    }
}
