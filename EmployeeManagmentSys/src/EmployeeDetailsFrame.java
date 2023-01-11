import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EmployeeDetailsFrame extends JFrame implements ActionListener {

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
    private JLabel week;
    private JPanel sun;
    private JPanel mon;
    private JPanel tue;
    private JPanel wed;
    private JPanel thur;
    private JPanel fri;
    private JPanel sat;
    private JTable table1;
    private JComboBox comboBox1;
    private JComboBox comboBox3;
    private JButton filter;
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
    private String name;


    //Constants
    final static String DETAILS = "Employee Details";
    final static String NOTESP = "Notes";
    final static String SCHEDULEP = "Schedule";
    final static String TIMEP = "Time Cards";
    final static String BENEFITSP = "Benefits";

    private JDBCHolder jdbcHolder;
    private Frame frame;


    public EmployeeDetailsFrame(Model model, String name) throws SQLException {
        super("Employee Information");

        this.model = model;
        employeeName.setText(name);
        jdbcHolder = new JDBCHolder();
        jdbcHolder.initializer();
        frame = new Frame(model);




        initMenu();
        initPage();
        fillEmPDetails();

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,500);
        this.setVisible(true);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateNoteFrame("Written by: Tooba", "");
            }
        });
    }
    private void initMenu() {

        //employeeName.setText("Tooba");

        details.addActionListener(this::showPage);
        notes.addActionListener(this::showPage);
        schedule.addActionListener(this::showPage);
        timeCards.addActionListener(this::showPage);
        benefits.addActionListener(this::showPage);
    }


    private void fillEmPDetails() throws SQLException {



        firstNametextField.setText(jdbcHolder.getValue(1, "idEmployee","FirstName", "Employee"));
        //middleNameTextField.setText(jdbcHolder.getValue(idEmployee, "idEmployee","MiddleName", "Employee"));
        //LastNametextField.setText(jdbcHolder.getValue(, "idEmployee","LastName", "Employee"));


        // get the first, midde,  and last name from the jlist

        // use one of the names to find the id
        // use the id to get the rest of the information


    }
    private void initPage() {
        // adding components to the panel
        page.add(detailsPage, DETAILS);
        page.add(notesPage, NOTESP);
        page.add(schedulePage, SCHEDULEP);
        page.add(timeCardPage, TIMEP);
        page.add(benefitsPage, BENEFITSP);
    }

    private void initSchedulePage() {
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

    public static void main(String[] args) throws SQLException {
        EmployeeDetailsFrame start = new EmployeeDetailsFrame(new Model(), "");
    }

}
