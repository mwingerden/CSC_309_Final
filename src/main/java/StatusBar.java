
import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * StatusBar class that shows the user what action is being performed at the bottom of the window.
 */
public class StatusBar extends JPanel implements Observer {
    JTextField statusBar;
    /**
     * Constructor method sets up the status bar attributes.
     * @param x, coordinate where bar will go
     */
    public StatusBar(int x){
        statusBar = new JTextField("Status", x);
        statusBar.setEditable(false);
        statusBar.setHorizontalAlignment(JTextField.LEFT);
        add(statusBar);
        Repository repository = Repository.getInstance();
        repository.addObserver(this);
    }
    /**
     * update method sets the current action to be performed.
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        statusBar.setText((String) arg);
    }
}
