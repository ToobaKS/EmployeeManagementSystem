public class Employee {

    String firstName;
    String lastName;
    String fullName;

    public Employee(String fname, String lname, String fullName) {
        this.firstName = fname;
        this.lastName = lname;
        this.fullName = fullName;


    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String lastName) {

        this.fullName = firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return fullName;
    }



    }

