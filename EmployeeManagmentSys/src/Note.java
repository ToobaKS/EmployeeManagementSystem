public class Note {

    private String createdby;
    private String createdfor;
    private String date;
    private String title;
    private String note;

    public Note(String createdby, String createdfor, String date, String title, String note) {
        this.createdby = createdby;
        this.createdfor = createdfor;
        this.date = date;
        this.title = title;
        this.note = note;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getCreatedfor() {
        return createdfor;
    }

    public void setCreatedfor(String createdfor) {
        this.createdfor = createdfor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

