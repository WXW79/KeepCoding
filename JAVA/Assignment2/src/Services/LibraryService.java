package Services;

import DAO.BookDAO;
import DAO.BorrowedBookDAO;
import DAO.BorrowerDAO;
import Models.Book;
import Models.BorrowedBooks;
import Models.Borrower;

import java.util.ArrayList;
import java.util.Scanner;

public class LibraryService {

    //not check is the book is same
    public static void addABook(Scanner scanner) {
        //Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the book: ");
        String name = scanner.nextLine();
        System.out.print("Enter the author of the book: ");
        String author = scanner.nextLine();
        System.out.print("Enter the available copies of the book: ");
        int copies = scanner.nextInt();
        Book book = new Book(name, author, copies);
        BookDAO bookDAO = new BookDAO();
        bookDAO.addBook(book);
        System.out.println("======Models.Book added");
        System.out.println();

        //scanner.close();
    }

    public static void listAllBooks() {
        BookDAO bookDAO = new BookDAO();
        ArrayList<Book> books = bookDAO.getAllBooks();
        for(Book book : books) {
            System.out.println(book);
        }
        System.out.println("======Books listed");
        System.out.println();
    }

    public static void updateBook(Scanner scanner) {
        boolean success = false;
        //Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the book id in the book list: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the updated name of the book: ");
        String bookName = scanner.nextLine();
        System.out.print("Enter the updated author of the book: ");
        String author = scanner.nextLine();
        System.out.print("Enter the updated copies of the book: ");
        int copies = scanner.nextInt();

        Book book = new Book(bookId, bookName, author, copies);
        BookDAO bookDAO = new BookDAO();
        success = bookDAO.updateBook(book);

        if(success) {
        System.out.println("======Models.Book updated");
        }else{
            System.out.println("======Models.Book update failed(no such book)");
        }
        System.out.println();
        //scanner.close();
    }

    //show message if delete the unavailable book_id
    public static void deleteBook(Scanner scanner) {
        boolean success = false;
        //Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the book id in the book list: ");
        int bookId = scanner.nextInt();
        BookDAO bookDAO = new BookDAO();
        success = bookDAO.deleteBook(bookId);

        if(success) {
            System.out.println("======Models.Book deleted successfully");
        }else{
            System.out.println("======Models.Book id not available or deleted failed");
        }
        System.out.println();
        //scanner.close();
    }

    //not check if borrower is same
    public static void addBorrower(Scanner scanner) {
        //Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the new borrower name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the borrower email: ");
        String email = scanner.nextLine();

        Borrower newBorrower = new Borrower(name, email);
        BorrowerDAO borrowerDAO = new BorrowerDAO();
        borrowerDAO.addBorrower(newBorrower);

        System.out.println("======Models.Borrower added");
        System.out.println();
        //scanner.close();
    }

    public static void listAllBorrowers() {
        BorrowerDAO borrowerDAO = new BorrowerDAO();
        ArrayList<Borrower> borrowerList = borrowerDAO.getAllBorrowers();
        for(Borrower borrower : borrowerList) {
            System.out.println(borrower);
        }
        System.out.println("======Borrowers listed");
        System.out.println();
    }

    // same borrower and same book is not available
    public static void borrowABook(Scanner scanner) {
        //Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the book id in the book list: ");
        int bookId = scanner.nextInt();
        boolean success = false;

        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.getBook(bookId);
        if(book == null) {
            System.out.println("Models.Book not found");
        }
        else if (book.available_copies < 1) {
            System.out.println("Models.Book available_copies less than 1");
        }
        else {
            System.out.print("Please input the borrower id: ");
            int borrowerId = scanner.nextInt();
            BorrowedBookDAO borrowedBookDAO = new BorrowedBookDAO();
            success = borrowedBookDAO.getABookRecordNoReturn(bookId, borrowerId);
            if(!success) {
                borrowedBookDAO.borrowBook(book.id, borrowerId);
                System.out.println("======Borrowed book added");
            }else{
                System.out.println("======Models.Borrower can not borrow the same book");
            }
        }
        System.out.println();
        //scanner.close();
    }

    //if already returned before operation, not add new item and show the message
    public static void returnABook(Scanner scanner) {
        //Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the book id in the book list: ");
        int bookId = scanner.nextInt();
        System.out.print("Enter the borrower id: ");
        int borrowerId = scanner.nextInt();
        boolean success = false;

        BorrowedBookDAO borrowedBookDAO = new BorrowedBookDAO();
        success = borrowedBookDAO.getABookRecordNoReturn(bookId,borrowerId);
        if(success) {
            borrowedBookDAO.returnBook(bookId, borrowerId);
            System.out.println("======Models.Book returned");
        }else{
            System.out.println(
                    "======The info was not available or book had already returned");
        }

        System.out.println();
        //scanner.close();
    }

    public static void viewAllBorrowedBooks() {
        BorrowedBookDAO borrowedBookDAO = new BorrowedBookDAO();
        ArrayList<BorrowedBooks> borrowedBooks = borrowedBookDAO.getAllBorrowedBooks();
        for(BorrowedBooks borrowedBook : borrowedBooks) {
            System.out.println(borrowedBook);
        }

        System.out.println("======Borrowed books listed");
        System.out.println();
    }
}
