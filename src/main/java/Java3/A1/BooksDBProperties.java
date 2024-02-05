package Java3.A1;

/**
 * Store all the MariaDB connection properties here.
 * start
 */
public class BooksDBProperties {

    static final String DB_URL = "jdbc:mariadb://localhost:3308";

    static final String USER = "root";
    static final String PASS = "JavaImran2024";

    //Specific test database URL
    static final String JAVA_BOOKS_DB_URL = "jdbc:mariadb://localhost:3308/books?user=root&password=JavaImran2024";


    static final String QUERY_ALL_AUTHORS = "SELECT * FROM authors;";
    static final String QUERY_ALL_TITLES = "SELECT * FROM titles;";

}
