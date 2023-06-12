import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Login extends JDialog {
    private final JTextField tfUsername;
    private final JPasswordField pfPassword;
    private final String userName = "IAmTeacher";
    private final String password = "TeacherIAm";

    public Login(JPanel parent) {
//        super(parent, "Login", true);

        MainController mainController = new MainController();
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        JLabel lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);

        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);

        JLabel lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
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

    public String getUsername() {
        return tfUsername.getText().trim();
    }

    public String getPassword() {
        return new String(pfPassword.getPassword());
    }

    public boolean isSucceeded() {
        boolean result = authenticate(getUsername(), getPassword());
        tfUsername.setText("");
        pfPassword.setText("");
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
