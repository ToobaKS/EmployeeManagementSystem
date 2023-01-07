package OldGUICode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HumanResourceOldCode extends JFrame implements ActionListener {
    private JPanel FramePage;
    private JPanel mainPage;
    private JPanel menubarPanel;
    private JButton Dashboard;
    private JPanel sidebarPanel;
    private JPanel CardholderPanel;
    private JButton profileButton;
    private JButton notificationsButton;
    private JButton settingsButton;
    private JMenuBar menubar;
    private JMenu Employees;
    private JMenu Salary;
    private JMenu Requests;
    private JMenu Reports;
    private JPanel ListofEmpPage;
    private JPanel addEmpPage;
    private JPanel empDetailsPage;
    private JPanel addNotesPage;
    private JPanel empSchedulePage;
    private JPanel notesPage;
    private JPanel benefitsPage;
    private JPanel payScalePage;
    private JPanel clockInOutPage;
    private JPanel vacationPage;
    private JPanel wfoPage;
    private JPanel equipmentPage;
    private JPanel T4Page;
    private JPanel payStubPage;
    private JPanel formsPage;
    private JPanel BenefitsPage;
    private JSpinner spinner1;
    private JMenuItem listEmp,addEmp, vReq, wfoReq, eReq, T4, stub, cForms, benefits, payScale, clockIn, payScaleChart, timeTrackingChart;

    final static String SHOW_LIST = "List of Employees";
    final static String ADD_EMP = "Add Employees";
    final static String PAYSCALE = "Payscale Charts";
    final static String TIME_CHARTS = "Time Charts";
    final static String VACATION_REQUESTS = "Vacation Requests";
    final static String WFO_REQUESTS = "WFO Requests";
    final static String EQUIPMENT_REQUESTS = "Equipment Requests";
    final static String T4_FORM = "T4";
    final static String PAYSTUB = "PayStub";
    final static String CONTRACT_FORM = "Contract Forms";
    final static String BENEFITS = "Benefits";

    //The model
    //private Model model;

    public HumanResourceOldCode(){

        //initializing the drop down menues
        initMainMenu();


        // adding components to the panel
        CardholderPanel.add(ListofEmpPage, SHOW_LIST);
        CardholderPanel.add(addEmpPage, ADD_EMP);
        CardholderPanel.add(payScalePage, PAYSCALE);
        CardholderPanel.add(clockInOutPage, TIME_CHARTS);
        CardholderPanel.add(vacationPage, VACATION_REQUESTS);
        CardholderPanel.add(wfoPage, WFO_REQUESTS);
        CardholderPanel.add(equipmentPage, EQUIPMENT_REQUESTS);
        CardholderPanel.add(T4Page, T4_FORM);
        CardholderPanel.add(payStubPage, PAYSTUB);
        CardholderPanel.add(BenefitsPage, BENEFITS);
        CardholderPanel.add(formsPage, CONTRACT_FORM);

        //Display the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setContentPane(FramePage);
        this.setVisible(true);



    }

    private void initMainMenu(){



        //Initializing the Employee DropDown Menu
        //Employees = new JMenu("Employees");

        listEmp = new JMenuItem("List of Employees");
        listEmp.addActionListener(this::showListEmp);
        addEmp = new JMenuItem("Add Employee");
        addEmp.addActionListener(this::addEmp);


        Employees.add(listEmp);
        Employees.add(addEmp);


        //Initializing the Report DropDown Menu
        //Reports = new JMenu("Reports");

        payScale = new JMenuItem("Payscale Charts");
        payScale.addActionListener(this::showPayScale);
        clockIn = new JMenuItem("Time Charts");
        clockIn.addActionListener(this::showTimeChart);

        Reports.add(payScale);
        Reports.add(clockIn);


        vReq = new JMenuItem("Vacation Requests");
        vReq.addActionListener(this::showVacation);
        wfoReq = new JMenuItem("WFO Requests");
        wfoReq.addActionListener(this::showWFO);
        eReq = new JMenuItem("Equipment Requests");
        eReq.addActionListener(this::showEquipment);

        Requests.add(vReq);
        Requests.add(wfoReq);
        Requests.add(eReq);


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
        ((CardLayout)CardholderPanel.getLayout()).show(CardholderPanel, name);
    }


    public void showListEmp(ActionEvent event) {
        showView(SHOW_LIST);
    }

    public void addEmp(ActionEvent event) {
        showView(ADD_EMP);
    }


    public void showPayScale(ActionEvent event) {
        showView(PAYSCALE);
    }

    public void showTimeChart(ActionEvent event) {
        showView(TIME_CHARTS);
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



    public void systemUpdate(String command, String info) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        HumanResourceOldCode myHumanResourceOldCode = new HumanResourceOldCode();
    }

}
