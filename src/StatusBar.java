import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class StatusBar extends JPanel implements Observer {
    JTextField statusBar;

    public StatusBar(int x){
        statusBar = new JTextField("Status", x);
        statusBar.setEditable(false);
        statusBar.setHorizontalAlignment(JTextField.CENTER);
        add(statusBar);
    }

    @Override
    public void update(Observable o, Object arg) {
        statusBar.setText((String) arg);
    }
}
