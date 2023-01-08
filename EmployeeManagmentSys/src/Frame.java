
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Frame extends JFrame implements View, ActionListener{
    private JPanel FramePage;
    private JPanel MainPage;
    private JPanel menubarPanel;
    private JButton Dashboard;
    private JPanel sidebarPanel;
    private JButton Profile;
    private JButton Notifications;
    private JButton Settings;
    private JPanel cardlayoutHolder;
    private JMenuBar menubar;
    private JMenu Employees;
    private JMenu Reports;
    private JMenu Projects;
    private JMenu Requests;
    private JMenu Salary;
    private JMenuItem listEmp, timeTracking, history, tasks, vReq, wfoReq, eReq, T4, stub, cForms, benefits, payScale, clockIn, addEmp;
    private JPanel dashboardPage;
    private JPanel newEmpPage;
    private JPanel timeTrackingPage;
    private JPanel listofEmployees;
    private JPanel payScalePage;
    private JPanel timeChartPage;
    private JPanel historyPage;
    private JPanel tasksPage;
    private JPanel vacationPage;
    private JPanel wfoPage;
    private JPanel equipmentPage;
    private JPanel t4Page;
    private JPanel payStub;
    private JPanel benefitPage;
    private JPanel contractPage;
    private JMenu Schedule;
    private JList empList;
    private JLabel listEmpLabel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField4;
    private JButton submitButton;
    private JTextField textField3;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JLabel totalHoursLabel;
    private JLabel vacationTypeLabel;
    private JComboBox vacationComboBox;
    private JLabel vacationRequestLabel;
    private JPanel headlinePanel;
    private JPanel componentsPanel;
    private JPanel addNewEmpPage;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton SubmitButton;
    private JLabel typeLabel;
    private JPanel equipmentLabel;
    private JLabel versionLabel;
    private JLabel reasonLabel;
    private JPanel equipmentComponentPanel;
    private JLabel EquipmentReqLabel;
    private JTable T4Table;
    private JLabel T4Label;
    private JPanel contractsHeaderPanel;
    private JPanel searchBarPanel;
    private JPanel listFromsPanel;
    private JLabel contractsLabel;
    private JButton searchButton;
    private JTextField searchTextField;
    private JList formsList;
    private JLabel resultsLabel;
    private JList list1;
    private JList list2;
    private ArrayList<String> Names; // this array list will store the names of the employees
    //private DefaultListModel listModel;

    final static String SHOW_LIST = "listEmp";
    final static String TIME_TRACK = "timeTracking";
    final static String PAYSCALE = "Payscale Charts";
    final static String TIME_CHARTS = "Time Charts";
    final static String HISTORY = "History";
    final static String TASKS = "Tasks";
    final static String VACATION_REQUESTS = "Vacation Requests";
    final static String WFO_REQUESTS = "WFO Requests";
    final static String EQUIPMENT_REQUESTS = "Equipment Requests";
    final static String T4_FORM = "T4";
    final static String PAYSTUB = "PayStub";
    final static String CONTRACT_FORM = "Contract Forms";
    final static String BENEFITS = "Benefits";
    final static String ADD = "Add New Employee";





    //The models
    private Controller control;
    private Model model;
    private Table table;
    private JDBCHolder jdbcHolder;
    private List list;

    public Frame(Model model) {
        super("ERP");

        //establishing communication to model and controller
        this.model = model;
        model.addView(this);
        this.control = new Controller(model, this);


       // testing populating table from the schema -> failed
        jdbcHolder = new JDBCHolder();
        jdbcHolder.initializer();
        table = new Table(jdbcHolder.getConnection());
        try {
            table.buildTableModel("select * from Employee");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        T4Table = new JTable(table);
        // did not work!

        //testing populating list of employees from database
        try {
            jdbcHolder.fillEmpJList(empList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Notifications = new JButton();

        //initializing the drop down menues
        initMainMenu();

        // adding components to the panel
        cardlayoutHolder.add(listofEmployees, SHOW_LIST);
        cardlayoutHolder.add(timeTrackingPage, TIME_TRACK);
        cardlayoutHolder.add(payScalePage, PAYSCALE);
        cardlayoutHolder.add(timeChartPage, TIME_CHARTS);
        cardlayoutHolder.add(historyPage, HISTORY);
        cardlayoutHolder.add(tasksPage, TASKS);
        cardlayoutHolder.add(vacationPage, VACATION_REQUESTS);
        cardlayoutHolder.add(wfoPage, WFO_REQUESTS);
        cardlayoutHolder.add(equipmentPage, EQUIPMENT_REQUESTS);
        cardlayoutHolder.add(t4Page, T4_FORM);
        cardlayoutHolder.add(payStub, PAYSTUB);
        cardlayoutHolder.add(benefitPage, BENEFITS);
        cardlayoutHolder.add(contractPage, CONTRACT_FORM);
        cardlayoutHolder.add(newEmpPage, ADD);

        //Display the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setContentPane(FramePage);
        this.setVisible(true);

        empList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //when you click on any element name it will take you to the employee frame
                // problem here when you click on it displays two frames instead of one
                if (!e.getValueIsAdjusting()) {
                    int empNumber = empList.getSelectedIndex();
                    System.out.println(empNumber);
                    if (empNumber >= 0) {
                        EmployeeDetailsFrame myEmpFrame = new EmployeeDetailsFrame(model);
                    }
                }
            }
        });
        Notifications.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NotificationsFrame();
            }
        });
    }






    private void initMainMenu(){


        //Initializing the Employee DropDown Menu
        //Employees = new JMenu("Employees");

        listEmp = new JMenuItem("List of Employees");
        listEmp.addActionListener(this::showListEmp);
        // add a new action listenr to add the populate list methid
        // make sure it gets the latest list from database
        timeTracking = new JMenuItem("TimeTracking");
        timeTracking.addActionListener(this::showTimeTrack);
        addEmp = new JMenuItem("Add New Employee");
        addEmp.addActionListener(this::addNewEmp);



        Employees.add(listEmp);
        Employees.add(timeTracking);
        Employees.add(addEmp);


        //Initializing the Report DropDown Menu
        //Reports = new JMenu("Reports");

        payScale = new JMenuItem("Payscale Charts");
        payScale.addActionListener(this::showPayScale);
        clockIn = new JMenuItem("Time Charts");
        clockIn.addActionListener(this::showTimeChart);

        Reports.add(payScale);
        Reports.add(clockIn);

        //Initializing the Employee DropDown Menu
        //Projects = new JMenu("Projects");

        history = new JMenuItem("History");
        history.addActionListener(this::showHistory);
        tasks = new JMenuItem("Tasks");
        tasks.addActionListener(this::showTasks);

        Projects.add(history);
        Projects.add(tasks);

        //Initializing the Employee DropDown Menu
        //Requests = new JMenu("Requests");

        vReq = new JMenuItem("Vacation Requests");
        vReq.addActionListener(this::showVacation);
        wfoReq = new JMenuItem("WFO Requests");
        wfoReq.addActionListener(this::showWFO);
        eReq = new JMenuItem("Equipment Requests");
        eReq.addActionListener(this::showEquipment);

        Requests.add(vReq);
        Requests.add(wfoReq);
        Requests.add(eReq);


        //Initializing the Employee DropDown Menu
        //Salary = new JMenu("Salary");

        T4 = new JMenuItem("T4");
        T4.addActionListener(this::showT4);
        stub = new JMenuItem("PayStub");
        stub.addActionListener(this::showPaystub);
        cForms = new JMenuItem("Contract Forms");
        cForms.addActionListener(this::showBenefit);
        benefits = new JMenuItem("Benefits");
        benefits.addActionListener(this::showContract);

        Salary.add(T4);
        Salary.add(stub);
        Salary.add(cForms);
        Salary.add(benefits);



    }

    private void showView(String name) {
        ((CardLayout)cardlayoutHolder.getLayout()).show(cardlayoutHolder, name);
    }


    public void showListEmp(ActionEvent event) {
        showView(SHOW_LIST);
    }

    public void addNewEmp(ActionEvent event) {
        showView(ADD);
    }

    public void showTimeTrack(ActionEvent event) {
        showView(TIME_TRACK);
    }

    public void showPayScale(ActionEvent event) {
        showView(PAYSCALE);
    }

    public void showTimeChart(ActionEvent event) {
        showView(TIME_CHARTS);
    }

    public void showHistory(ActionEvent event) {
        showView(HISTORY);
    }

    public void showTasks(ActionEvent event) {
        showView(TASKS);
    }

    public void showVacation(ActionEvent event) {
        showView(VACATION_REQUESTS);
    }

    public void showWFO(ActionEvent event) {
        showView(WFO_REQUESTS);
    }

    public void showEquipment(ActionEvent event) {
        showView(EQUIPMENT_REQUESTS);
    }

    public void showT4(ActionEvent event) {
        showView(T4_FORM);
    }

    public void showPaystub(ActionEvent event) {
        showView(PAYSTUB);
    }

    public void showBenefit(ActionEvent event) {
        showView(BENEFITS);
    }

    public void showContract(ActionEvent event) {
        showView(CONTRACT_FORM);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public static void main(String[] args) throws SQLException {
    }


    @Override
    public void systemUpdate(String info) {
    }
}
