package Java3_A1;

import java.util.List;
import java.util.Scanner;

/**
 * This class is responsible for managing the book application.
 * It provides a command-line interface for interacting with the Book and Author classes.
 */
public class BookApplication {
    private static final BookDatabaseManager dbManager = new BookDatabaseManager();
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Main method to run the book application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("Choose an option:");
            System.out.println("1. Print all books (showing authors)");
            System.out.println("2. Print all authors (showing books)");
            System.out.println("3. Add a book for an existing author");
            System.out.println("4. Add a new author");
            System.out.println("5. Quit");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    printAllBooks();
                    break;
                case 2:
                    printAllAuthors();
                    break;
                case 3:
                    addBookForExistingAuthor();
                    break;
                case 4:
                    addNewAuthor();
                    break;
                case 5:
                    System.out.println("...Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        } while (choice != 5);

        scanner.close();
    }

    /**
     * Prints all books and their associated authors.
     */
    public static void printAllBooks() {
        List<Book> books = dbManager.getAllBooks();
        for (Book book : books) {
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Edition Number: " + book.getEditionNumber());
            System.out.println("Copyright: " + book.getCopyright());
            System.out.println("Authors:" + book.getAuthorList().size());
            for (Author author : book.getAuthorList()) {
                System.out.println("- " + author.getFirstName() + " " + author.getLastName());
            }
            System.out.println();
        }
    }

    /**
     * Prints all authors and their associated books.
     */
    public static void printAllAuthors() {
        List<Author> authors = dbManager.getAllAuthors();
        for (Author author : authors) {
            System.out.println("Author ID: " + author.getAuthorID());
            System.out.println("Name: " + author.getFirstName() + " " + author.getLastName());
            System.out.println("Books: " + author.getBookList().size());
            for (Book book : author.getBookList()) {
                System.out.println("- " + book.getTitle());
            }
            System.out.println();
        }
    }


    /**
     * Adds a book for an existing author.
     * The user will be prompted to select an author and then enter book details.
     * The book will be added to the database and associated with the selected author.
     */
    private static void addBookForExistingAuthor() {
        List<Author> authors = dbManager.getAllAuthors();
        System.out.println("Select an author to add a book:");
        for (int i = 0; i < authors.size(); i++) {
            System.out.println((i + 1) + ". " + authors.get(i).getFirstName() + " " + authors.get(i).getLastName());
        }

        int authorIndex = scanner.nextInt();
        scanner.nextLine();
        if (authorIndex < 1 || authorIndex > authors.size()) {
            System.out.println("Invalid author index.");
            return;
        }

        Author selectedAuthor = authors.get(authorIndex - 1);

        System.out.println("Enter ISBN:");
        String isbn = scanner.nextLine();
        System.out.println("Enter title:");
        String title = scanner.nextLine();
        System.out.println("Enter edition number:");
        int editionNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter copyright:");
        String copyright = scanner.nextLine();

        Book newBook = new Book(isbn, title, editionNumber, copyright);

        selectedAuthor.addBook(newBook);
        newBook.addAuthor(selectedAuthor);

        dbManager.addBook(newBook);

        System.out.println("Book added successfully for author: " + selectedAuthor.getFirstName() + " " + selectedAuthor.getLastName());
    }

    /**
     * Adds a new author to the database.
     * The user will be prompted to enter the author's first and last name.
     */
    private static void addNewAuthor() {
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();

        Author newAuthor = new Author(0, firstName, lastName);

        dbManager.addAuthor(newAuthor);

        System.out.println("New author added successfully: " + newAuthor.getFirstName() + " " + newAuthor.getLastName());
    }
}
