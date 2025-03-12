import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeworkUI {
    private TextField subjectField = new TextField();
    private DatePicker dueDatePicker = new DatePicker();
    private ComboBox<String> priorityBox = new ComboBox<>();
    private TableView<Homework> table = new TableView<>();
    private ObservableList<Homework> homeworkList = FXCollections.observableArrayList();

    public HomeworkUI() {
        setupTable();
        loadHomework(); // Load existing homework from database
        applyStyles();  // Apply inline styles
    }

    private void setupTable() {
        TableColumn<Homework, String> subjectColumn = new TableColumn<>("Subject");
        subjectColumn.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());

        TableColumn<Homework, String> dueDateColumn = new TableColumn<>("Due Date");
        dueDateColumn.setCellValueFactory(cellData -> cellData.getValue().dueDateProperty());

        TableColumn<Homework, String> priorityColumn = new TableColumn<>("Priority");
        priorityColumn.setCellValueFactory(cellData -> cellData.getValue().priorityProperty());

        table.getColumns().addAll(subjectColumn, dueDateColumn, priorityColumn);
        table.setItems(homeworkList);

        priorityBox.getItems().addAll("Low", "Medium", "High");
    }

    private void loadHomework() {
        homeworkList.clear();
        ResultSet rs = DatabaseManager.getAllHomework();
        try {
            while (rs.next()) {
                homeworkList.add(new Homework(
                        rs.getInt("id"),
                        rs.getString("subject"),
                        rs.getString("due_date"),
                        rs.getString("priority")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public VBox getLayout() {
        Button addButton = new Button("Add Task");
        addButton.setOnAction(e -> addHomework());

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> deleteSelectedHomework());

        VBox layout = new VBox(10,
                new Label("Subject:"), subjectField,
                new Label("Due Date:"), dueDatePicker,
                new Label("Priority:"), priorityBox,
                addButton, table, deleteButton);

        layout.setStyle("-fx-padding: 20px; -fx-background-color: #f4f4f4;"); // Light gray background
        applyStyles(); // Apply inline styles

        return layout;
    }

    private void applyStyles() {
        // Styling for buttons
        String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-border-radius: 5px;";

        // Apply styles to buttons
        Button addButton = new Button("Add Task");
        addButton.setStyle(buttonStyle);

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setStyle(buttonStyle.replace("#4CAF50", "#E74C3C")); // Red delete button

        // Styling for input fields
        subjectField.setStyle("-fx-border-color: #333; -fx-border-width: 1px; -fx-padding: 5px;");
        dueDatePicker.setStyle("-fx-border-color: #333; -fx-border-width: 1px;");
        priorityBox.setStyle("-fx-border-color: #333; -fx-border-width: 1px;");

        // Table styling
        table.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-width: 1px;");
    }

    private void addHomework() {
        String subject = subjectField.getText();
        String dueDate = dueDatePicker.getValue().toString();
        String priority = priorityBox.getValue();

        DatabaseManager.addHomework(subject, dueDate, priority);
        loadHomework(); // Refresh table
    }

    private void deleteSelectedHomework() {
        Homework selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            DatabaseManager.deleteHomework(selected.getId());
            loadHomework();
        }
    }
}
