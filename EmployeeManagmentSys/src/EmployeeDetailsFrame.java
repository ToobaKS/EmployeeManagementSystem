import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeDetailsFrame extends JFrame implements View, ActionListener {

    private int ID;
    private JButton notes;
    private JButton details;
    private JButton schedule;
    private JButton timeCards;
    private JButton benefits;


    private JLabel employeeName;


    private JPanel detailsPage;
    private JPanel notesPage;
    private JPanel schedulePage;
    private JPanel timeCardPage;
    private JPanel benefitsPage;

    private Model model;

    private JPanel mainPanel;
    private JPanel page;
    private JPanel menuPanel;
    private JButton create;
    private JList notesList;
    private JButton previousW;
    private JButton nextW;
    private JLabel weekSchedule;
    private JComboBox timeCardDates;
    private JButton filter;
    private JButton backTimeCards;
    private JButton nextTimeCards;

    private JTextField firstNametextField;
    private JTextField empNumTextField;
    private JTextField middleNameTextField;
    private JTextField DepartmentTestField;
    private JTextField LastNametextField;
    private JTextField PositiontextField;
    private JTextField EmailtextField;
    private JTextField BirthdaytextField;
    private JTextField phoneNumtextField;
    private JTextField hireDatetextField;
    private JTextField AddresstextField;
    private JTextField SalarytextField;
    private JLabel firstNamelabel;
    private JLabel middlenameLabel;
    private JLabel lastNameLabel;
    private JLabel empIDLabel;
    private JLabel departmentLabel;
    private JLabel PositionLabel;
    private JLabel emailLabel;
    private JLabel phoneNumLabel;
    private JLabel addressLabel;
    private JLabel birthdayLabel;
    private JLabel hireDateLabel;
    private JLabel salaryLabel;
    private JTable timeCardsTable;
    private JLabel weekTimeCards;
    private JTable weeklySchedule;
    private String name;


    final JTextArea textArea = new JTextArea();

    //Constants
    final static String DETAILS = "Employee Details";
    final static String NOTESP = "Notes";
    final static String SCHEDULEP = "Schedule";
    final static String TIMEP = "Time Cards";
    final static String BENEFITSP = "Benefits";

    private ArrayList<HashMap> notesAttributes;
    private Controller controller;

    private HashMap<String, DefaultTableModel> weeklyTimeCards;

    private JDBCHolder jdbcHolder;

    public EmployeeDetailsFrame(Model model, Controller controller, int ID) throws SQLException {
        super("Employee Information");

        this.model = model;
        model.addView(this);
        this.controller = controller;

        this.ID = ID;

        employeeName.setText(name);
        jdbcHolder = new JDBCHolder();
        jdbcHolder.initializer();

        notesAttributes = new ArrayList<>();
        notesAttributes = model.getNotesList(ID);
        weeklyTimeCards = model.setTimeCards(ID);

        for ( String key : weeklyTimeCards.keySet() ) {
            timeCardDates.addItem(key);
        }

        initMenu();
        initPage();
        fillEmPDetails();

        initNotesPage();
        initSchedulePage(LocalDate.now());
        initTimeCardsPage(LocalDate.now());

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(700,600);
        this.setVisible(true);

        backTimeCards.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] cur = weekTimeCards.getText().split(" ");
                LocalDate currentDate = LocalDate.parse(cur[cur.length - 1]);

                LocalDate previousMonday = currentDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
                try {
                    initTimeCardsPage(previousMonday);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        nextTimeCards.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] cur = weekTimeCards.getText().split(" ");
                LocalDate currentDate = LocalDate.parse(cur[cur.length - 1]);

                LocalDate nextMonday = currentDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                try {
                    initTimeCardsPage(nextMonday);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new CreateNoteFrame(model, ID, model.getEmployeeID());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        notesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String data = notesList.getSelectedValue().toString();
                    String t = String.valueOf(data.charAt(data.length()-2));

                    for(HashMap h : notesAttributes){
                        if(h.get("NotesNo").equals(t)){
                            try {
                                new CreateNoteFrame(model, t);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }

                    try{
                        notesList.clearSelection();
                    } catch (NullPointerException exception){

                    }
                }
            }
        });

        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate selected = LocalDate.parse((CharSequence) timeCardDates.getSelectedItem());
                try {
                    initTimeCardsPage(selected);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        previousW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] cur = weekSchedule.getText().split(" ");
                LocalDate currentDate = LocalDate.parse(cur[cur.length - 1]);

                LocalDate previousMonday = currentDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
                try {
                    initSchedulePage(previousMonday);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        nextW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] cur = weekSchedule.getText().split(" ");
                LocalDate currentDate = LocalDate.parse(cur[cur.length - 1]);
                LocalDate nextMonday = currentDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                try {
                    initSchedulePage(nextMonday);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    private void initMenu() {

        employeeName.setText("Tooba");

        details.addActionListener(this::showPage);
        notes.addActionListener(this::showPage);
        schedule.addActionListener(this::showPage);
        timeCards.addActionListener(this::showPage);
        benefits.addActionListener(this::showPage);
    }


    private void fillEmPDetails() throws SQLException {

        firstNametextField.setText(jdbcHolder.getValue(this.ID, "idEmployee","FirstName", "Employee"));
        middleNameTextField.setText(jdbcHolder.getValue(this.ID, "idEmployee","MiddleName", "Employee"));
        LastNametextField.setText(jdbcHolder.getValue(this.ID, "idEmployee","LastName", "Employee"));
        empNumTextField.setText(jdbcHolder.getValue(this.ID, "idEmployee","idEmployee", "Employee"));
        DepartmentTestField.setText(jdbcHolder.getValue(this.ID, "idEmployee","DepartmentNum", "Employee"));
        PositiontextField.setText(jdbcHolder.getValue(this.ID, "idEmployee","Position", "Employee"));
        EmailtextField.setText(jdbcHolder.getValue(this.ID, "idEmployee","Email", "Employee"));
        phoneNumtextField.setText(jdbcHolder.getValue(this.ID, "idEmployee","PhoneNum", "Employee"));
        jdbcHolder.filltextField(AddresstextField, this.ID);
        BirthdaytextField.setText(jdbcHolder.getValue(this.ID, "idEmployee","DateofBirth", "Employee"));
        hireDatetextField.setText(jdbcHolder.getValue(this.ID, "idEmployee","HireDate", "Employee"));
        SalarytextField.setText(jdbcHolder.getEmpInfo(this.ID, "Salary", "HourlyRate"));


    }
    private void initPage() {
        // adding components to the panel
        page.add(detailsPage, DETAILS);
        page.add(notesPage, NOTESP);
        page.add(schedulePage, SCHEDULEP);
        page.add(timeCardPage, TIMEP);
        page.add(benefitsPage, BENEFITSP);
    }

    private void initSchedulePage(LocalDate date) throws SQLException {
        LocalDate previousMonday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        DefaultTableModel dtm = model.setSchedule(ID, date);

        weekSchedule.setText("Week of: " + previousMonday);
        weeklySchedule.setModel(dtm);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);

        for(int i = 0; i < 7; i++) {
            weeklySchedule.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }

        weeklySchedule.setRowHeight(100);


    }

    private void initNotesPage(){

        HashMap<String,String> temp = new HashMap<>();

        DefaultListModel listModel = new DefaultListModel();
        String data = "";

        if (notesAttributes.size() == 0){
            data = "Nothing to show here";
            listModel.addElement(data);
        }else{
            for (int i = 0; i < notesAttributes.size(); i++){
                temp = notesAttributes.get(i);
                data = temp.get("NotesTitle");
                data += " (Note No: " + temp.get("NotesNo") + ")";

                listModel.addElement(data);
            }
        }

        notesList.setModel(listModel);

    }

    private void initTimeCardsPage(LocalDate date) throws SQLException {
        LocalDate previousMonday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        DefaultTableModel dtm = weeklyTimeCards.get(String.valueOf(previousMonday));
        if(dtm == null){
            dtm = new DefaultTableModel();
            dtm.addColumn("Date");
            dtm.addColumn("Hours");
            dtm.addColumn("Start Time");
            dtm.addColumn("End Time");

        }

        weekTimeCards.setText("Week of: " + previousMonday);
        timeCardsTable.setModel(dtm);

    }

    private void showPage(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        ((CardLayout)page.getLayout()).show(page, command);

    }

    public String getName(){
        return name;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void systemUpdate(String info) {
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
        return null;
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

    public static void main(String[] args) throws SQLException {
        EmployeeDetailsFrame start = new EmployeeDetailsFrame(new Model(),null , 1);
    }
}
