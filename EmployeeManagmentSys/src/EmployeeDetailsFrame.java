import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JTable timeCardsTable;
    private JComboBox comboBox3;
    private JButton filter;
    private JButton back;
    private JButton next;


    //Constants
    final static String DETAILS = "Employee Details";
    final static String NOTESP = "Notes";
    final static String SCHEDULEP = "Schedule";
    final static String TIMEP = "Time Cards";
    final static String BENEFITSP = "Benefits";

    public EmployeeDetailsFrame(Model model) throws SQLException {
        super("Employee Information");

        this.model = model;

        initMenu();
        initPage();

        initNotesPage();
        initSchedulePage();
        initTimeCardsPage();

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

        employeeName.setText("Tooba");

        details.addActionListener(this::showPage);
        notes.addActionListener(this::showPage);
        schedule.addActionListener(this::showPage);
        timeCards.addActionListener(this::showPage);
        benefits.addActionListener(this::showPage);
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

    private void initNotesPage(){

    }

    private void initTimeCardsPage() throws SQLException {
       Table t = new Table(model.getJdbc().getConnection());
       DefaultTableModel ttm = t.buildTableModel("select Employee_idEmployee from TimeTracking where DateWorked between date_sub(now(),INTERVAL 1 WEEK) and now() AND Employee_idEmployee = 2");
       timeCardsTable.setModel(ttm);

    }

    private void showPage(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        ((CardLayout)page.getLayout()).show(page, command);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) throws SQLException {
        EmployeeDetailsFrame start = new EmployeeDetailsFrame(new Model());
    }

}
