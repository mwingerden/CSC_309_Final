
import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ProblemDescription extends JPanel implements Observer {
    JTextField problemDescText;
    Repository repository = Repository.getInstance();

    public ProblemDescription() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Color.LIGHT_GRAY);
        problemDescText = new JTextField();
        JLabel text = new JLabel("Problem Description:");
        add(text);
        add(problemDescText);
        repository.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        String update = (String) arg;
        if(update != null) {
            switch (update) {
                case "Save Description" -> repository.saveProblemDescription(problemDescText.getText());
                case "Load Description" -> problemDescText.setText(repository.loadProblemDescription());
                case "Clear Description" -> problemDescText.setText("");
            }

        }
    }
}
