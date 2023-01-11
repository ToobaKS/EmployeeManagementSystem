import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Frame extends JFrame implements View {
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
    private JTextField startDateText;
    private JTextField endDateText;
    private JButton submitButton;
    private JTextField ReasonTextField;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JLabel totalDaysLabel;
    private JLabel vacationTypeLabel;
    private JComboBox vacationComboBox;
    private JLabel vacationRequestLabel;
    private JPanel headlinePanel;
    private JPanel componentsPanel;
    private JPanel addNewEmpPage;
    private JComboBox EquipmentType;
    private JButton SubmitButtonEq;
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
    private JButton EqSubmitButton;
    private JTextField textField5;
    private JComboBox EqVersionComboBox;
    private JTextField StartTextField;
    private JTextField TotalDaysTextField;
    private JTextField endTextField;
    private JTextField totalDaysText;
    private JTextField EqVerText;
    private JTable linksTable;
    private ArrayList<String> Names; // this array list will store the names of the employees
    private String name;
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
    private Model model;
    private Table table;
    private Table table2;
    private JDBCHolder jdbcHolder;
    private List list;
    private Controller control;

    public Frame(Model model) throws SQLException {
        super("ERP");
        this.model = model;
        model.addView(this);
        control = new Controller(model, this);

        // creates instance of JButton
        //login.addActionListener(this);
        submitButton.addActionListener(control);
        EqSubmitButton.addActionListener(control);

        jdbcHolder = new JDBCHolder();
        jdbcHolder.initializer();
        table = new Table(jdbcHolder.getConnection());
        table2 = new Table(jdbcHolder.getConnection());
        //table.buildTableModel("select * from Employee");  // select firstName as 'First Name' from Employee
        //T4Table = new JTable(table);
        //T4Table.setModel(table);
        //T4Table.setFillsViewportHeight(true);
        //T4Table.setVisible(true);
        int idEmployee = 0;



        // did not work!

        //testing populating list of employees from database
        jdbcHolder.fillEmpJList(empList);


        //To communicate with the model
        //Controller c = new Controller(model);


        //initializing the drop down menues
        initMainMenu();

        //adding items to combobox
        initComboBox();




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


        //listSelection Listener
        empList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //when you click on any element name it will take you to the employee frame
                // problem here when you click on it it displays two frames instead of one
                if (!e.getValueIsAdjusting()) {
                    int empNumber = empList.getSelectedIndex();
                    System.out.println(empNumber);
                    if (empNumber >= 0) {
                        try {
                            EmployeeDetailsFrame myEmpFrame = new EmployeeDetailsFrame(model, (String)(empList.getSelectedValue()));
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        //myEmpFrame.setName();
                    }
                }
            }
        });




        //Display the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setContentPane(FramePage);
        this.setVisible(true);


    }



    public void setName(String name){
        this.name = (String)empList.getSelectedValue();

    }

    public String getName(){
        return name;
    }

    public int getlistID() throws SQLException {

        int idEmployee=0;
        //int result = 0;
        String[] temp2 = ((String)(empList.getSelectedValue())).split(" ");
        int temp = jdbcHolder.getEmpID(idEmployee, "Employee","FirstName",temp2[1]);

        return temp;

    }




/*
    private void saveToEquipmentTable() {
        // unfinished: need the current employee logged into the system to set the value
        try {

            jdbcHolder.insertData("insert into Equipment(Employee_idEmployee)" +
                    "Values ('" + 8 + "')");
            System.out.println("submitted to database");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
*/


    private void initComboBox() {
        //adding items to the vacation combobox
        vacationComboBox.addItem("Paid time off leave");
        vacationComboBox.addItem("Sick leave");
        vacationComboBox.addItem("Maternity leave");
        vacationComboBox.addItem("Paternity leave");
        vacationComboBox.addItem("Bereavement leave");
        vacationComboBox.addItem("Compensatory leave");
        vacationComboBox.addItem("Sabbatical leave");
        vacationComboBox.addItem("Unpaid leave");

        // adding items to EquipmentType
        EquipmentType.addItem("Microsoft surface book");
        EquipmentType.addItem("Tablet");
        EquipmentType.addItem("Mouse");
        EquipmentType.addItem("Keyboard");
        EquipmentType.addItem("Headset");
        EquipmentType.addItem("Phone");
        EquipmentType.addItem("USB Cabel");
        EquipmentType.addItem("Adapter");
        EquipmentType.addItem("surface book Battery");
        EquipmentType.addItem("Tablet Charger");
        EquipmentType.addItem("Phone Charger");
        EquipmentType.addItem("Other");


    }

    private void initMainMenu() {


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
        ((CardLayout) cardlayoutHolder.getLayout()).show(cardlayoutHolder, name);
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

        // this is to make sure that we reload the table everythime we click the menu item to get the latest data not when we open the frame
        try {
            table.buildTableModel("select FormType as 'Type of Form', FormYar from Forms");  // select firstName as 'First Name' from Employee
            T4Table.setModel(table);
            table2.buildTableModel("select FormAttachment as 'Link' from Forms");
            linksTable.setModel(table2);
            T4Table.setFillsViewportHeight(true);
            T4Table.setVisible(true);
            linksTable.setFillsViewportHeight(true);
            linksTable.setVisible(true);

            System.out.println("IN Show T4");
        } catch (Exception e) {
            System.out.println("something happened.");
        }
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




    public static void main(String[] args) throws SQLException {
        Frame myManagerFrame = new Frame(new Model());


    }


    @Override
    public String getLeaveType() {
        return String.valueOf(vacationComboBox.getSelectedItem());
    }

    public String getEquipmentType(){
        return String.valueOf(EquipmentType.getSelectedItem());
    }

    @Override
    public Date getStartDate() {
        return Date.valueOf(StartTextField.getText());
    }

    @Override
    public Date getEndDate() {
        return Date.valueOf(endTextField.getText());
    }

    @Override
    public int getLeaveDays() {
        return Integer.parseInt(TotalDaysTextField.getText());
    }
}




