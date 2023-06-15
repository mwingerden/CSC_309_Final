
import javax.swing.*;

/**
 * The first panel viewed when the program is loaded. Asks if teacher or student.
 */
public class StartUp extends JPanel {

    /**
     * Constructor for the first panel viewed when the program is loaded. Asks if teacher or student.
     */
    public StartUp() {
        MainController controller = new MainController();
        JLabel jLabel1 = new JLabel("Are you a");
        JButton teacher = new JButton("Teacher");
        teacher.addActionListener(controller);
        JLabel jLabel2 = new JLabel(" or ");
        JButton student = new JButton("Student");
        student.addActionListener(controller);
        JLabel jLabel3 = new JLabel("?");

        add(jLabel1);
        add(teacher);
        add(jLabel2);
        add(student);
        add(jLabel3);
    }
}
