
import javax.swing.*;

public class StartUp extends JPanel {
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
