import javax.swing.*;

public class Model {

    private final String ADD = "add";                   //to add an employee
    private final String REM = "remove";                //to remove an employee
    private final String UPDATE = "update";             //to update the employees information
    private final String LOGIN = "login";               //to check the login
    private final String ACCESS = "access";             //to check the level of access to the system
    private final String ADDNOTE = "add note";          //to add notes to employee file
    private final String REMNOTE = "remove note";       //to remove notes from employee file
    private final String EQUIPREQ = "request equipment";//to request equipment
    private final String WFOREQ = "request WFO";        //to request WFO
    private final String VACREQ = "request vacation";   //to request vacation

    public Model(){

    }

    public void run(String command){
        if(ADD.equals(command)){

        }
        if(REM.equals(command)){

        }
        if(UPDATE.equals(command)){

        }
        if(LOGIN.equals(command)){

        }
        if(ACCESS.equals(command)){

        }
        if(ADDNOTE.equals(command)){

        }
        if(REMNOTE.equals(command)){

        }
        if(EQUIPREQ.equals(command)){

        }
        if(WFOREQ.equals(command)){

        }
        if(VACREQ.equals(command)){

        }
    }

    private void notifyView(){

    }

    public void addView(JFrame frame) {
    }
}
