package DAO;

import Models.Book;
import Models.BorrowedBooks;
import Services.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BorrowedBookDAO {
    private static Connection connection;
    public BorrowedBookDAO() {
        this.connection = DBConnection.getInstance().getConnection();
    }

    //Records a borrowed book and decreases available copies
    public boolean borrowBook(int bookId, int borrowerId) {
        String query = "INSERT INTO borrowed_books (book_id, borrower_id,borrow_date) " +
                "VALUES (?, ?, ?)";
        boolean success = false;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookId);
            statement.setInt(2, borrowerId);
            statement.setString(3, LocalDate.now().toString());
            success = statement.executeUpdate() > 0;

            //adjust the available_copies
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.getBook(bookId);
            book.available_copies--;
            bookDAO.updateBook(book);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return success;
    }

    //Marks a book as returned and increases available copies.
    public boolean returnBook(int bookId, int borrowerId) {
        String query = "UPDATE borrowed_books set return_date = ? " +
                "where book_id = ? and borrower_id = ?";
        boolean success = false;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, LocalDate.now().toString());
            statement.setInt(2, bookId);
            statement.setInt(3, borrowerId);
            success = statement.executeUpdate() > 0;

            //adjust the available_copies
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.getBook(bookId);
            book.available_copies++;
            bookDAO.updateBook(book);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return success;
    }

    // Shows all borrowed books.
    public ArrayList<BorrowedBooks> getAllBorrowedBooks() {
        ArrayList<BorrowedBooks> borrowerBooks = new ArrayList<>();
        String query = "SELECT * FROM borrowed_books";
        //String query = "SELECT * FROM borrowers where return_date IS NOT NULL";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                BorrowedBooks newBorrowedBooks = new BorrowedBooks(
                        results.getInt("id"),
                        results.getInt("book_id"),
                        results.getInt("borrower_id"),
                        results.getString("borrow_date"),
                        results.getString("return_date")
                );
                borrowerBooks.add(newBorrowedBooks);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("failed to get books!");
        }

        return borrowerBooks;
    }

    public boolean getABookRecordNoReturn(int bookId, int borrowerId) {
        String query = "select * from borrowed_books " +
                "where book_id = ? and borrower_id = ? and return_date IS NULL";
        boolean success = false;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookId);
            statement.setInt(2, borrowerId);

            ResultSet results = statement.executeQuery();
            if (results.next())
                success = true;
            else
                success = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return success;
    }
}
