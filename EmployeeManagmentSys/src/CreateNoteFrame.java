import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class CreateNoteFrame extends JFrame implements View {
    private JTextField noteText;
    private JLabel writer;
    private JLabel date;
    private JButton create;
    private JPanel mainPanel;
    private JTextField noteTitle;
    private JLabel notesTitle;
    private int employeeId;
    private int writerId;
    private int notesNo;
    private Model model;
    private Controller controller;

    private HashMap<String, String> note = new HashMap<>();

    public CreateNoteFrame(Model model, int employeeId, int writerId) throws SQLException {
        this.model = model;
        model.addView(this);
        this.controller = new Controller(model, this);;

        this.employeeId = employeeId;
        this.writerId = writerId;

        writer.setText("Written By: " + model.getEmployeeName(writerId));
        date.setText(String.valueOf(LocalDate.now()));

        noteTitle.setEditable(true);
        noteText.setEditable(true);

        create.setVisible(true);
        create.enable();
        create.addActionListener(controller);

        init();
    }

    public CreateNoteFrame(Model model, String NotesNo) throws SQLException {
        this.model = model;
        model.addView(this);
        this.controller = new Controller(model, this);

        this.notesNo = Integer.parseInt(NotesNo);

        note = model.getRow("Notes","NotesNo",notesNo);

        noteTitle.setText(note.get("NotesTitle"));
        noteText.setText(note.get("NotesContent"));
        writer.setText(model.getEmployeeName(Integer.parseInt(note.get("WrittenBy"))));
        date.setText(note.get("NotesDate"));

        noteTitle.setEditable(false);
        noteText.setEditable(false);

        create.setVisible(false);
        create.disable();

        init();
    }

    private void init() {
        this.add(mainPanel);
        this.setSize(600,500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }


    @Override
    public void systemUpdate(String info) {
        JOptionPane.showMessageDialog(this, info);
        this.dispose();
    }

    @Override
    public String getCubicleCombo() {
        return null;
    }

    @Override
    public String getDateCombo() {
        return null;
    }

    @Override
    public int getNotifNo() {
        return 0;
    }

    @Override
    public String getNote() {
        String newNote = "";
        newNote = noteTitle.getText() + " " + date.getText() + " " + employeeId + " " + noteText.getText();

        return newNote;
    }

    @Override
    public String getLeaveType() {
        return null;
    }

    @Override
    public String getEquipmentType() {
        return null;
    }

    @Override
    public String getEquipmentVer() {
        return null;
    }

    @Override
    public Date getStartDate() {
        return null;
    }

    @Override
    public Date getEndDate() {
        return null;
    }

    @Override
    public int getLeaveDays() {
        return 0;
    }
}
