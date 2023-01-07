package OldGUICode;

/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OldEmployeeDetailFrame extends JFrame implements View, ActionListener {

    private Model model;

    private JPanel mainPanel;
    private JPanel menu;

    private JPanel page;

    //Cardlayout pages
    private JPanel detailsPage, notesPage, schedulePage, timeCardPage, benefitsPage;

    //Menu Panel Conetents
    private JButton employeeDetails, notes, schedule, timeCards, benefits;
    private JLabel employeeName;


    //Constants
    final static String DETAILS = "Employee Details";
    final static String NOTESP = "Notes";
    final static String SCHEDULEP = "Schedule";
    final static String TIMEP = "Time Cards";
    final static String BENEFITSP = "Benefits";
    public OldEmployeeDetailFrame(Model model) {
        super("Employee Information");
        
        this.model = model;
        
        mainPanel = new JPanel();
        menu = new JPanel();
        page = new JPanel();

        //Layout
        mainPanel.setLayout(new BorderLayout(0,0));

        initMenu();
        initPage();

        mainPanel.add(menu, BorderLayout.NORTH);
        mainPanel.add(page,BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,500);
        this.setContentPane(mainPanel);
        this.setVisible(true);
    }
    private void initMenu() {

        employeeName = new JLabel("Tooba");

        employeeDetails = new JButton("Employee Details");
        employeeDetails.addActionListener(this::showPage);

        notes = new JButton("Notes");
        notes.addActionListener(this::showPage);

        schedule = new JButton("Schedule");
        schedule.addActionListener(this::showPage);

        timeCards = new JButton("Time Cards");
        timeCards.addActionListener(this::showPage);

        benefits = new JButton("Benefits");
        benefits.addActionListener(this::showPage);

        menu.add(employeeName);
        menu.add(employeeDetails);
        menu.add(notes);
        menu.add(schedule);
        menu.add(timeCards);
        menu.add(benefits);
    }

    private void initPage() {
        page.setLayout(new CardLayout());

        detailsPage = new JPanel();
        notesPage = new JPanel();
        schedulePage = new JPanel();
        timeCardPage = new JPanel();
        benefitsPage = new JPanel();

        initDetailsPage();
        initNotesPage(); 
        initSchedulePage(); 
        initTimeCardPage(); 
        initBenefitsPage();
        

        // adding components to the panel
        page.add(detailsPage, DETAILS);
        page.add(notesPage, NOTESP);
        page.add(schedulePage, SCHEDULEP);
        page.add(timeCardPage, TIMEP);
        page.add(benefitsPage, BENEFITSP);
    }

    private void initDetailsPage() {
    }

    private void initNotesPage() {
      notesPage.add(new OldNotesPage());
    }
    private void initSchedulePage() {
        //Tutorial following: https://www.youtube.com/watch?v=YivaMCfichQ&ab_channel=RaVen

    }
    private void initTimeCardPage() {
    }



    private void initBenefitsPage() {
    }

    private void showPage(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        ((CardLayout)page.getLayout()).show(page, command);

    }

    @Override
    public void systemUpdate(String command, String info) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        OldEmployeeDetailFrame start = new OldEmployeeDetailFrame(new Model());
    }
}


 */