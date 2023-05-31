
import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ProblemDescription extends JPanel implements Observer {
    JTextArea problemDescText;
    Repository repository = Repository.getInstance();

    public ProblemDescription() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Color.LIGHT_GRAY);
        problemDescText = new JTextArea(Repository.getInstance().getLoadedProblem().getProblemDescription());
        JLabel text = new JLabel("Problem Description:");
        add(text);
        add(problemDescText);
        repository.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        /*String update = (String) arg;
        if(update != null) {
            switch (update) {
                case "Saved" -> repository
                        .getLoadedProblem()
                        .setProblemDescription(problemDescText.getText());
                case "Loaded" -> problemDescText
                        .setText(repository.getLoadedProblem().getProblemDescription());
                case "Clear Description" -> problemDescText.setText("");
            }

        }*/
    }
}
