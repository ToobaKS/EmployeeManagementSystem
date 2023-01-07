import javax.swing.*;

public class CreateNoteFrame extends JFrame {
    private JTextField noteText;
    private JLabel writer;
    private JLabel date;
    private JButton create;
    private JPanel mainPanel;

    public CreateNoteFrame(String writtenBy, String tDate){
        this.writer = new JLabel();
        this.date = new JLabel(tDate);

        writer.setText(writtenBy);
        date.setText(tDate);
        this.add(mainPanel);

        this.setSize(600,500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
