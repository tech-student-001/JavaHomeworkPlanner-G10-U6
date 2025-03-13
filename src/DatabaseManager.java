import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:homework.db";

    // Static block to initialize the database and table
    static {
        createTable();
    }

    // Create the homework table if it doesn't exist
    private static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS homework ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "subject TEXT NOT NULL,"
                + "due_date TEXT NOT NULL,"
                + "priority TEXT NOT NULL)";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            if (conn != null) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a new homework task
    public static void addHomework(String subject, String dueDate, String priority) {
        String sql = "INSERT INTO homework (subject, due_date, priority) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subject);
            pstmt.setString(2, dueDate);
            pstmt.setString(3, priority);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all homework tasks sorted by due date
    public static ResultSet getAllHomework() {
        String sql = "SELECT * FROM homework ORDER BY due_date ASC";
        try {
            Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Delete a homework task by ID
    public static void deleteHomework(int id) {
        String sql = "DELETE FROM homework WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
