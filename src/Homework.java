import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Homework {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty subject;
    private final SimpleStringProperty dueDate;
    private final SimpleStringProperty priority;

    public Homework(int id, String subject, String dueDate, String priority) {
        this.id = new SimpleIntegerProperty(id);
        this.subject = new SimpleStringProperty(subject);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.priority = new SimpleStringProperty(priority);
    }

    public int getId() { return id.get(); }
    public SimpleStringProperty subjectProperty() { return subject; }
    public SimpleStringProperty dueDateProperty() { return dueDate; }
    public SimpleStringProperty priorityProperty() { return priority; }
}
