import javax.swing.*;

public class StatusBar extends JPanel{

    public StatusBar(){
        JTextField statusBar = new JTextField("Status", 70);
        statusBar.setEditable(false);
        statusBar.setHorizontalAlignment(JTextField.CENTER);
        add(statusBar);
    }
}
