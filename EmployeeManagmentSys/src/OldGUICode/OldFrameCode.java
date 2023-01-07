package OldGUICode;
/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OldFrameCode extends JFrame implements View, ActionListener {

    //The model
    private Model model;

   // panel that will contain the borderlayout
    //private JPanel pagePanel;
    private JPanel menuPanel;
    private JPanel sidePanel;
    private JPanel centerPanel;
    private JPanel pagePanel;
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
    private JMenuBar mainMB;
    private JMenu dashboard, employees, reports, projects, requests, salary;
    private JMenuItem listEmp, timeTracking, history, tasks, vReq, wfoReq, eReq, T4, stub, cForms, benefits, payScale, clockIn, addNewEmp;
    private CardLayout cardLayout;
    private JButton buttonTest;
    private JLabel test;
    final static String ADDNEWEMP = "addNewEmp";
    final static String DASHBOARD = "dashboard";
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



    public OldFrameCode(Model model){
        super("ERP");

        model.addView(this);

        //To communicate with the model
        Controller c = new Controller(model);

        // initializing
        menuPanel = new JPanel();
        sidePanel = new JPanel();
        centerPanel = new JPanel();
        pagePanel = new JPanel();
        dashboardPage = new JPanel();
        newEmpPage = new JPanel();
        listofEmployees = new JPanel();
        timeTrackingPage = new JPanel();
        cardLayout = new CardLayout();
        payScalePage = new JPanel();
        timeChartPage = new JPanel();
        historyPage = new JPanel();
        tasksPage = new JPanel();
        vacationPage = new JPanel();
        wfoPage = new JPanel();
        equipmentPage = new JPanel();
        t4Page = new JPanel();
        payStub = new JPanel();
        benefitPage = new JPanel();
        contractPage = new JPanel();

        //test stuff initilizer(i only created these to test the cardlayout)
        buttonTest = new JButton();
        test = new JLabel("test");
        listofEmployees.add(buttonTest);
        timeTrackingPage.add(test);
        dashboardPage.add(test);

        // setting the layout
        pagePanel.setLayout(new BorderLayout(0,0));

        //setting the size and dimensions for the components
        menuPanel.setBackground(Color.blue);
        menuPanel.setPreferredSize(new Dimension(100, 50));
        sidePanel.setBackground(Color.blue);
        sidePanel.setPreferredSize(new Dimension(40, 100));

        //Initializing the menu bar
        mainMB = new JMenuBar();
        initMainMenu();

        //Add menu bar to the frame
        menuPanel.add(mainMB);



        //setting the layout for the center panel
        centerPanel.setLayout(cardLayout);

        // adding components to the panel
        centerPanel.add(dashboardPage, DASHBOARD);
        centerPanel.add(newEmpPage, ADDNEWEMP);
        centerPanel.add(listofEmployees, SHOW_LIST);
        centerPanel.add(timeTrackingPage, TIME_TRACK);
        centerPanel.add(payScalePage, PAYSCALE);
        centerPanel.add(timeChartPage, TIME_CHARTS);
        centerPanel.add(historyPage, HISTORY);
        centerPanel.add(tasksPage, TASKS);
        centerPanel.add(vacationPage, VACATION_REQUESTS);
        centerPanel.add(wfoPage, WFO_REQUESTS);
        centerPanel.add(equipmentPage, EQUIPMENT_REQUESTS);
        centerPanel.add(t4Page, T4_FORM);
        centerPanel.add(payStub, PAYSTUB);
        centerPanel.add(benefitPage, BENEFITS);
        centerPanel.add(contractPage, CONTRACT_FORM);



        // adding the panels to the pane
       pagePanel.add(menuPanel,BorderLayout.NORTH);
       pagePanel.add(sidePanel,BorderLayout.WEST);
       pagePanel.add(centerPanel,BorderLayout.CENTER);

        //Display the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setContentPane(pagePanel);
        this.setVisible(true);

    }

    private void initMainMenu(){

        dashboard = new JMenu("Dashboard");
        dashboard.addActionListener(this::showDashBoard);

        //Initializing the Employee DropDown Menu
        employees = new JMenu("Employees");

        listEmp = new JMenuItem("List of Employees");
        listEmp.addActionListener(this::showListEmp);
        timeTracking = new JMenuItem("TimeTracking");
        timeTracking.addActionListener(this::showTimeTrack);
        addNewEmp = new JMenuItem("Add Employee");
        addNewEmp.addActionListener(this::showNewEmp);

        employees.add(listEmp);
        employees.add(timeTracking);
        employees.add(addNewEmp);
        //Initializing the Report DropDown Menu
        reports = new JMenu("Reports");

        payScale = new JMenuItem("Payscale Charts");
        payScale.addActionListener(this::showPayScale);
        clockIn = new JMenuItem("Time Charts");
        clockIn.addActionListener(this::showTimeChart);

        reports.add(payScale);
        reports.add(clockIn);

        //Initializing the Employee DropDown Menu
        projects = new JMenu("Projects");

        history = new JMenuItem("History");
        history.addActionListener(this::showHistory);
        tasks = new JMenuItem("Tasks");
        tasks.addActionListener(this::showTasks);

        projects.add(history);
        projects.add(tasks);

        //Initializing the Employee DropDown Menu
        requests = new JMenu("Requests");

        vReq = new JMenuItem("Vacation Requests");
        vReq.addActionListener(this::showVacation);
        wfoReq = new JMenuItem("WFO Requests");
        wfoReq.addActionListener(this::showWFO);
        eReq = new JMenuItem("Equipment Requests");
        eReq.addActionListener(this::showEquipment);

        requests.add(vReq);
        requests.add(wfoReq);
        requests.add(eReq);


        //Initializing the Employee DropDown Menu
        salary = new JMenu("Salary");

        T4 = new JMenuItem("T4");
        T4.addActionListener(this::showT4);
        stub = new JMenuItem("PayStub");
        stub.addActionListener(this::showPaystub);
        cForms = new JMenuItem("Contract Forms");
        cForms.addActionListener(this::showBenefit);
        benefits = new JMenuItem("Benefits");
        benefits.addActionListener(this::showContract);

        salary.add(T4);
        salary.add(stub);
        salary.add(cForms);
        salary.add(benefits);


        //Adding all the menus to the menu bar
        mainMB.add(dashboard);
        mainMB.add(employees);
        mainMB.add(reports);
        mainMB.add(projects);
        mainMB.add(requests);
        mainMB.add(salary);

    }

    private void newEmployeePageDisplay(){

    }

    private void wfhPageDisplay(){

    }

    private void notificationPageDisplay(){

    }




    private void showView(String name) {
        ((CardLayout)centerPanel.getLayout()).show(centerPanel, name);
    }
    private void showNewEmp(ActionEvent event) {
        showView(ADDNEWEMP);
    }
    public void showDashBoard(ActionEvent event) {
        showView(DASHBOARD);
    }

    public void showListEmp(ActionEvent event) {
        showView(SHOW_LIST);
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
    public void systemUpdate(String command, String info) {
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //could be replaced wit a switch case here
    }
}

*/
