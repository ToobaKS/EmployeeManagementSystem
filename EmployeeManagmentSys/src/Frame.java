import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements View, ActionListener {

    //MenuBar and items for the horizontal bar
    private JMenuBar mainMB;
    private JMenu employees, reports, projects, requests, salary;
    private JMenuItem listEmp, timeTracking, history, tasks, vReq, wfoReq, eReq, T4, stub, cForms, benefits, payScale, clockIn;

    public Frame(Model model){
        super("ERP");

        model.addView(this);

        //To communicate with the model
        Controller c = new Controller(model);

        //Initializing the menu bar
        mainMB = new JMenuBar();
        initMainMenu();

        //Add menu bar to the frame
        this.setJMenuBar(mainMB);

        //Display the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,400);
        this.setVisible(true);
    }

    private void initMainMenu(){

        //Initializing the Employee DropDown Menu
        employees = new JMenu("Employees");

        listEmp = new JMenuItem("List of Employees");
        timeTracking = new JMenuItem("TimeTracking");

        employees.add(listEmp);
        employees.add(timeTracking);

        //Initializing the Report DropDown Menu
        reports = new JMenu("Reports");

        payScale = new JMenuItem("Payscale Charts");
        clockIn = new JMenuItem("Time Charts");

        reports.add(payScale);
        reports.add(clockIn);

        //Initializing the Employee DropDown Menu
        projects = new JMenu("Projects");

        history = new JMenuItem("History");
        tasks = new JMenuItem("Tasks");

        projects.add(history);
        projects.add(tasks);

        //Initializing the Employee DropDown Menu
        requests = new JMenu("Requests");

        vReq = new JMenuItem("Vacation Requests");
        wfoReq = new JMenuItem("WFO Requests");
        eReq = new JMenuItem("Equipment Requests");

        requests.add(vReq);
        requests.add(wfoReq);
        requests.add(eReq);

        //Initializing the Employee DropDown Menu
        salary = new JMenu("Salary");

        T4 = new JMenuItem("T4");
        stub = new JMenuItem("PayStub");
        cForms = new JMenuItem("Contract Forms");
        benefits = new JMenuItem("Benefits");

        salary.add(T4);
        salary.add(stub);
        salary.add(cForms);
        salary.add(benefits);

        //Adding all the menus to the menu bar
        mainMB.add(employees);
        mainMB.add(reports);
        mainMB.add(projects);
        mainMB.add(requests);
        mainMB.add(salary);
    }

    @Override
    public void systemUpdate(String command, String info) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
