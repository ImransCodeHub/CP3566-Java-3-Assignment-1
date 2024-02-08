package Java3_A1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDatabaseManager {
    static final String JAVA_BOOKS_DB_URL = "jdbc:mariadb://localhost:3308/books?user=root&password=JavaImran2024";
//    private static final String DB_URL = "jdbc:mysql://localhost:3308/books";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "JavaImran2024";

    private Connection connection;

    public BookDatabaseManager() {
        try {
            this.connection = DriverManager.getConnection(JAVA_BOOKS_DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle connection failure
        }
    }

    // Other methods for CRUD operations, managing relationships, etc.

    public void addBook(Book book) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO titles (isbn, title, editionNumber, copyright) VALUES (?, ?, ?, ?)");
            PreparedStatement stmt2 = connection.prepareStatement("INSERT INTO authorISBN (isbn, authorID) VALUES (?, ?)");
            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setInt(3, book.getEditionNumber());
            stmt.setString(4, book.getCopyright());
            stmt.executeUpdate();

            stmt2.setString(1, book.getIsbn());
            for (Author author : book.getAuthorList()) {
                stmt2.setInt(2, author.getAuthorID());
                stmt2.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    public void addAuthor(Author author) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO authors (firstName, lastName) VALUES (?, ?)");
            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

//    public List<Book> getAllBooks() {
//        List<Book> books = new ArrayList<>();
//        try {
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM titles");
//            ResultSet rs2 = stmt.executeQuery("SELECT * FROM authorISBN");
//            while (rs.next()) {
//                String isbn = rs.getString("isbn");
//                String title = rs.getString("title");
//                int editionNumber = rs.getInt("editionNumber");
//                String copyright = rs.getString("copyright");
//                Book book = new Book(isbn, title, editionNumber, copyright);
//                books.add(book);
//            }
//            while (rs2.next()) {
//                String isbn = rs2.getString("isbn");
//                int authorID = rs2.getInt("authorID");
//                for (Book book : books) {
//                    if (book.getIsbn().equals(isbn)) {
//                        for (Author author : getAllAuthors()) {
//                            if (author.getAuthorID() == authorID) {
//                                book.addAuthor(author);
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Handle SQL exception
//        }
//        return books;
//    }
//
//    public List<Author> getAllAuthors() {
//        List<Author> authors = new ArrayList<>();
//        try {
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM authors");
//            while (rs.next()) {
//                int authorID = rs.getInt("authorID");
//                String firstName = rs.getString("firstName");
//                String lastName = rs.getString("lastName");
//                Author author = new Author(authorID, firstName, lastName);
//                authors.add(author);
//            }
//            ResultSet rs_authorISBN = stmt.executeQuery("SELECT * FROM authorISBN");
//            while (rs_authorISBN.next()) {
//                int authorID = rs_authorISBN.getInt("authorID");
//                String isbn = rs_authorISBN.getString("isbn");
//                for (Author author : authors) {
//                    if (author.getAuthorID() == authorID) {
//                        for (Book book : getAllBooks()) {
//                            if (book.getIsbn().equals(isbn)) {
//                                author.addBook(book);
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Handle SQL exception
//
//        }
//        return authors;
//    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM titles");
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                int editionNumber = rs.getInt("editionNumber");
                String copyright = rs.getString("copyright");
                Book book = new Book(isbn, title, editionNumber, copyright);
                books.add(book);
            }

            // Now, populate the authors for each book
            for (Book book : books) {
                List<Author> authors = getAuthorsForBook(book.getIsbn());
                book.setAuthorList(authors);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
        return books;
    }

    private List<Author> getAuthorsForBook(String isbn) {
        List<Author> authors = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT a.authorID, a.firstName, a.lastName FROM authors a INNER JOIN authorISBN ai ON a.authorID = ai.authorID WHERE ai.isbn = ?");
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int authorID = rs.getInt("authorID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                Author author = new Author(authorID, firstName, lastName);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
        return authors;
    }

    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM authors");
            while (rs.next()) {
                int authorID = rs.getInt("authorID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                Author author = new Author(authorID, firstName, lastName);
                authors.add(author);
            }

            // Now, populate the books for each author
            for (Author author : authors) {
                List<Book> books = getBooksForAuthor(author.getAuthorID());
                author.setBookList(books);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
        return authors;
    }

    private List<Book> getBooksForAuthor(int authorID) {
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT t.isbn, t.title, t.editionNumber, t.copyright FROM titles t INNER JOIN authorISBN ai ON t.isbn = ai.isbn WHERE ai.authorID = ?");
            stmt.setInt(1, authorID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                int editionNumber = rs.getInt("editionNumber");
                String copyright = rs.getString("copyright");
                Book book = new Book(isbn, title, editionNumber, copyright);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
        return books;
    }



    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }
}



