import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class NotesPage extends JPanel {

    private ArrayList<Note> notes = new ArrayList<>();
    private ArrayList<JButton> buttons = new ArrayList<>();

    private JButton create;

    public NotesPage() {
        this.setLayout(new BorderLayout(0,0));


        create = new JButton("Create");
        create.addActionListener(this::addNote);
        this.add(create, BorderLayout.NORTH);


        for(Note n : notes){
            JButton b = new JButton(n.getTitle());
            b.addActionListener(this ::viewNote);
            this.add(b, BorderLayout.CENTER);
        }

    }

    private void addNote(ActionEvent actionEvent) {
        new AddNotePage("Tooba", "Zinah", this);
        this.repaint();
    }

    private void viewNote(ActionEvent actionEvent) {
        new AddNotePage(notes.get(0), this);

    }
    public void addNote(Note note){
        notes.add(note);
    }
}
