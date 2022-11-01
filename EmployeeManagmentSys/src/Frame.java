import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements View, ActionListener {

    //MenuBar and items for the horizontal bar
    private JMenuBar mainMB;
    private JMenu employees, Reports, projects, requests, salary;
    private JMenuItem listEmp, timeTracking, history, tasks, vReq, wfoReq, eReq, T4, stub, cForms, benefits, payScale, clockIn;


    //The model
    private Model model;

    public Frame(){
        super("ERP");

        //adding the view to the model
        model = new Model();
        model.addView(this);

        //To communicate with the model
        Controller c = new Controller(model);

        mainMB = new JMenuBar();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add menu bar to the frame
        this.setJMenuBar(mainMB);

        //Display the window
        this.setSize(400,400);
        this.setVisible(true);
    }

    @Override
    public void systemUpdate(String command, String info) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String args[])
    {
    }

}
