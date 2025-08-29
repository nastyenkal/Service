package model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Настя
 */

public class DatabaseManager {

    private Connection connection = null;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:user_sessions.db");
            System.out.println("Подключение к БД установлено.");
            initDatabase();
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к БД: " + e.getMessage());
        }
    }

    private void initDatabase() throws SQLException {
        String createSessionsTable = "CREATE TABLE IF NOT EXISTS sessions ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "start_time TIMESTAMP NOT NULL,"
                + "description VARCHAR(255) NOT NULL)";

        String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                + "id VARCHAR(36) PRIMARY KEY,"
                + "session_id INTEGER NOT NULL,"
                + "name VARCHAR(255) NOT NULL,"
                + "email VARCHAR(255) NOT NULL,"
                + "user_type VARCHAR(50) NOT NULL,"
                + "data_source VARCHAR(50) NOT NULL,"
                + "FOREIGN KEY (session_id) REFERENCES sessions(id))";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSessionsTable);
            stmt.execute(createUsersTable);
            System.out.println("Таблицы успешно созданы/проверены.");
        }
    }

    public int startNewSession() {
        String description = "Сеанс от " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String sql = "INSERT INTO sessions(start_time, description) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(2, description);
            
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int sessionId = rs.getInt(1);
                        System.out.println("Новый сеанс начат с ID: " + sessionId);
                        return sessionId;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при создании нового сеанса: " + e.getMessage());
        }
        return -1;
    }

    public void addUser(User user, int sessionId, String dataSource) {
        String sql = "INSERT INTO users(id, session_id, name, email, user_type, data_source) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getId().toString());
            pstmt.setInt(2, sessionId);
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getUserType().name());
            pstmt.setString(6, dataSource);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении пользователя в БД: " + e.getMessage());
        }
    }

    public void resetDatabase() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM users");
            stmt.execute("DELETE FROM sessions");
            System.out.println("База данных успешно очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке БД: " + e.getMessage());
        }
    }
    
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Соединение с БД закрыто.");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при закрытии соединения с БД: " + e.getMessage());
        }
    }
}
