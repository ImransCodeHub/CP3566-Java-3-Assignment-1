package Java3_A1;

import java.util.Scanner;

public class BookApplication {
    private static final BookDatabaseManager dbManager = new BookDatabaseManager();
    private static final Scanner scanner = new Scanner(System.in);

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
            scanner.nextLine(); // Consume newline

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
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void printAllBooks() {
        // Implement logic to print all books
    }

    private static void printAllAuthors() {
        // Implement logic to print all authors
    }

    private static void addBookForExistingAuthor() {
        // Implement logic to add a book for an existing author
    }

    private static void addNewAuthor() {
        // Implement logic to add a new author
    }
}
