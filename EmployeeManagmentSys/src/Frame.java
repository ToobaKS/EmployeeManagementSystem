
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Frame extends JFrame implements View, ActionListener {
    private JPanel FramePage;
    private JPanel MainPage;
    private JPanel menubarPanel;
    private JButton dashboard;
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
    private JButton submitButton;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JLabel totalHoursLabel;
    private JLabel vacationTypeLabel;
    private JComboBox vacationComboBox;
    private JLabel vacationRequestLabel;
    private JPanel headlinePanel;
    private JPanel componentsPanel;
    private JPanel addNewEmpPage;
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
    private JList tasksList;
    private JComboBox dateCombo;
    private JButton submitWFO;
    private JComboBox cubicleCombo;

    private JPanel background;
    private JPanel contactInfo;
    private JPanel hireDetails;
    private JButton contactDetailsButton;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JButton hireDetailsButton;
    private JButton submitNewEmp;
    private JTextField textField11;
    private JTextField textField12;
    private JTextField textField13;
    private JTextField textField14;
    private JPanel addEmpCardLayout;
    private JList unreadNotesList;
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
    final static String DASHBOARD = "Dashboard";

    final static String BACKGROUND = "Submit New Employee";
    final static String CONTACTINFO = "Next: Contact Details";
    final static String HIREDETAILS = "Next: Hire Details";

    private String name;

    private JTextField startDateText;
    private JTextField endDateText;
    private JTextField ReasonTextField;
    private JLabel totalDaysLabel;
    private JComboBox EquipmentType;
    private JButton SubmitButtonEq;
    private JButton EqSubmitButton;
    //private JTextField textField5;
    private JComboBox EqVersionComboBox;
    private JTextField StartTextField;
    private JTextField TotalDaysTextField;
    private JTextField endTextField;
    private JTable empListTable;
    private JPanel typePanel;
    private JPanel versionPanel;
    private JPanel reasonPanel;
    private JTextField totalDaysText;
    private JTextField EqVerText;
    private JTable linksTable;


    //The models
    private Controller control;
    private Model model;
    private Table table;
    private Table table2;
    private Table table3;
    private JDBCHolder jdbcHolder;
    private List list;

    ArrayList<HashMap> notificationAttributes = new ArrayList<>();

    public Frame(Model model) throws SQLException {
        super("ERP");

        //establishing communication to model and controller
        this.model = model;
        model.addView(this);
        this.control = new Controller(model, this);

        // testing populating table from the schema -> failed
        jdbcHolder = new JDBCHolder();
        jdbcHolder.initializer();
        table = new Table(jdbcHolder.getConnection());
        table2 = new Table(jdbcHolder.getConnection());
        table3 = new Table(jdbcHolder.getConnection());

        T4Table = new JTable(table);
        // did not work!

        // creates instance of JButton
        //login.addActionListener(this);
        submitButton.addActionListener(control);
        EqSubmitButton.addActionListener(control);

        int idEmployee = 0;

        //initializing the drop-down menues
        initMainMenu();
        initComboBox();

        //Setting the views
        setView();

        // adding components to the panel
        cardlayoutHolder.add(dashboardPage, DASHBOARD);
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
        cardlayoutHolder.add(addNewEmpPage, ADD);

        addEmpCardLayout.add(background, BACKGROUND);
        addEmpCardLayout.add(contactInfo, CONTACTINFO);
        addEmpCardLayout.add(hireDetails, HIREDETAILS);

        hireDetailsButton.addActionListener(this::showHireDetails);
        contactDetailsButton.addActionListener(this::showContactDetails);
        submitNewEmp.addActionListener(this::showBackground);

        dashboard.doClick();

        //Display the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setContentPane(FramePage);
        this.setVisible(true);

        // need to keep the list open
        empListTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //JTable target = (JTable)e.getSource();
                int row = empListTable.getSelectedRow();
                int column = empListTable.getSelectedColumn();
                String stringId = String.valueOf(empListTable.getModel().getValueAt(row, 2));
                int id = Integer.parseInt(stringId);
                try {
                    EmployeeDetailsFrame myEmpFrame = new EmployeeDetailsFrame(model, control, id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        EquipmentType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EqVersionComboBox.removeAllItems();
                EqVersionComboBox.addItem("");
                try {
                    ArrayList<String> eq = jdbcHolder.getAvailableEqVer(String.valueOf(EquipmentType.getSelectedItem()));
                    for (String e2: eq){
                        EqVersionComboBox.addItem(e2);
                    }
                } catch(Exception e3){System.err.println("cannot get versions");}

            }
        });
        Notifications.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new NotificationsFrame(model, control);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        fillDashboardNotificationList();
        unreadNotesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String data = unreadNotesList.getSelectedValue().toString();
                    System.out.println(unreadNotesList.getSelectedValue().toString());
                    String t = String.valueOf(data.charAt(data.length() - 2));

                    for (HashMap h : notificationAttributes) {
                        if (h.get("NotificationNo").equals(t)) {
                            try {
                                new NotificationDetailFrame(model, control, h, t);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }

                    try {
                        unreadNotesList.clearSelection();
                    } catch (NullPointerException exception) {

                    }
                }
            }
        });
        submitWFO.addActionListener(control);
        dashboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fillDashboardNotificationList();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void setView(){
        String employeeLevel = model.getEmployeeLevel();

        if(employeeLevel.contains("HR")){
            Projects.setVisible(false);
        }else if(employeeLevel.contains("manager")){
            addEmp.setVisible(false);
        }else{
            Employees.setVisible(false);
            Reports.setVisible(false);

        }
    }

    public void setName(String name){
        this.name = (String)empList.getSelectedValue();
    }

    public String getName(){
        return name;
    }

    private void initWFOPage() throws SQLException {
        DefaultComboBoxModel cbm = model.getDateList();
        dateCombo.setModel(cbm);
        cubicleCombo.setModel(model.getCubicles(String.valueOf(cbm.getElementAt(0))));

        dateCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String selectedDate = "";
                selectedDate = String.valueOf(dateCombo.getSelectedItem());

                cubicleCombo.setModel(model.getCubicles(selectedDate));
            }
        });

    }

    private void fillDashboardNotificationList() throws SQLException {
        notificationAttributes = model.getNotificationList();
        try {

            HashMap<String, String> temp = new HashMap<>();

            DefaultListModel listModel = new DefaultListModel();
            String data = "";

            if (notificationAttributes.size() == 0) {
                data = "Nothing to show here";
                listModel.addElement(data);
            } else {
                for (int i = 0; i < notificationAttributes.size(); i++) {
                    temp = notificationAttributes.get(i);
                    if (temp.get("NotificationStatus").equals("unread")) {
                        data = temp.get("NotificationTitle");
                        data += " from ";
                        data += model.getEmployeeName(Integer.parseInt(temp.get("Employee_idEmployee")));
                        data += " (Notification Id: " + temp.get("NotificationNo") + ")";
                        listModel.addElement(data);
                    }

                }
            }

            unreadNotesList.setModel(listModel);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    private void initMainMenu() {

        dashboard.addActionListener(this::showDashboard);

        //Initializing the Employee DropDown Menu
        //Employees = new JMenu("Employees");


        listEmp = new JMenuItem("List of Employees");
        listEmp.addActionListener(this::showListEmp);
        // add a new action listener to add the populate list method
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
        EquipmentType.addItem("");
        try {
            ArrayList<String> eq = jdbcHolder.getAvailableEqType();
            for (String e: eq){
                EquipmentType.addItem(e);
            }
        } catch(Exception e){System.err.println("cannot get types");}
        /*
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
*/


        /*
        // adding versions to equipment
        EqVersionComboBox.addItem(1.0);
        EqVersionComboBox.addItem(2.0);
        EqVersionComboBox.addItem(3.0);
        EqVersionComboBox.addItem(4.0);


         */


    }

    private void showView(String name) {
        ((CardLayout) cardlayoutHolder.getLayout()).show(cardlayoutHolder, name);
    }

    private void showNewEmpView(String name) {
        ((CardLayout) addEmpCardLayout.getLayout()).show(addEmpCardLayout, name);
    }

    public void showContactDetails(ActionEvent event) {
        showNewEmpView(CONTACTINFO);
    }

    public void showBackground(ActionEvent event) {
        showNewEmpView(BACKGROUND);
    }

    public void showHireDetails(ActionEvent event) {
        showNewEmpView(HIREDETAILS);
    }

    public void showDashboard(ActionEvent event) {
        showView(DASHBOARD);
    }

    public void showListEmp(ActionEvent event) {
        try {
            table3.buildTableModel("select FirstName as 'First Name', LastName as 'Last Name', idEmployee as 'Employee Number' from Employee");  // select firstName as 'First Name' from Employee
            empListTable.setModel(table3);
            empListTable.setFillsViewportHeight(true);
            empListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            empListTable.setVisible(true);


            System.out.println("IN Show T4");
        } catch (Exception e) {
            System.out.println("something happened.");
        }
        showView(SHOW_LIST);
    }

    public void addNewEmp(ActionEvent event) {
        showView(ADD);
        submitNewEmp.doClick();
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
        try {
            initWFOPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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


    @Override
    public String getCubicleCombo() {
        return String.valueOf(cubicleCombo.getSelectedItem());
    }

    @Override
    public String getDateCombo() {
        return String.valueOf(dateCombo.getSelectedItem());
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
    public void actionPerformed(ActionEvent e) {
    }

    public static void main(String[] args) throws SQLException {
        Frame f = new Frame(new Model());
    }

    @Override
    public void systemUpdate(String info) {
        if (info.contains("WFO")) {
            JOptionPane.showMessageDialog(this, info);
            try {
                initWFOPage();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getLeaveType() {
        return String.valueOf(vacationComboBox.getSelectedItem());
    }

    public String getEquipmentType(){
        return String.valueOf(EquipmentType.getSelectedItem());
    }

    public String getEquipmentVer(){
        return String.valueOf(EqVersionComboBox.getSelectedItem());
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












