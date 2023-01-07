package OldGUICode;

/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OldAddNotePage extends JFrame implements ActionListener {

    private String writtenFor;
    private String writtenBy;


    private JPanel topPanel;

    private JButton done;
    private JLabel date;
    private JLabel writer;
    private JTextField title;
    private JTextField description;

    public OldAddNotePage(String writtenBy, String writtenFor){
        super("Add Note");

        this.writtenBy = writtenBy;
        this.writtenFor = writtenFor;

        topPanel = new JPanel();
        date = new JLabel(String.valueOf(java.time.LocalDate.now()));
        writer = new JLabel(writtenBy);
        title = new JTextField();
        description = new JTextField();

        init();
    }

    public OldAddNotePage(Note existingNote){
        super("Add Note");

        this.writtenBy = existingNote.getCreatedby();
        this.writtenFor = existingNote.getCreatedfor();

        topPanel = new JPanel();
        date = new JLabel(existingNote.getDate());
        writer = new JLabel(existingNote.getCreatedby());
        title = new JTextField(existingNote.getTitle());
        description = new JTextField(existingNote.getNote());

        init();

    }

    private void init(){
        this.setLayout(new BorderLayout(10,10));

        done = new JButton("Done");
        done.addActionListener(this :: createNote);

        topPanel.add(writer);
        topPanel.add(date);

        this.add(topPanel,BorderLayout.NORTH);
        this.add(title,BorderLayout.CENTER);
        this.add(description,BorderLayout.CENTER);
        this.add(done,BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,500);
        this.setVisible(true);
    }

    private void createNote(ActionEvent actionEvent) {
        Note note = new Note(writtenBy, writtenFor, String.valueOf(java.time.LocalDate.now()), title.getText(), description.getText());
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}

 */
