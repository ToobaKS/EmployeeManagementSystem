import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.sql.SQLException;

public class Controller implements ActionListener{

    private Model model;
    private View view;
    private View loginView;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command){
            case "Login":
        }
    }
}
