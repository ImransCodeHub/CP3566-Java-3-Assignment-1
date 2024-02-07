package Java3_A1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookDatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3308/books";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "JavaImran2024";

    private Connection connection;

    public BookDatabaseManager() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle connection failure
        }
    }

    // Other methods for CRUD operations, managing relationships, etc.

    public void addBook(Book book) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO titles (isbn, title, editionNumber, copyright) VALUES (?, ?, ?, ?)");
            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setInt(3, book.getEditionNumber());
            stmt.setString(4, book.getCopyright());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

        // Other CRUD methods
}


