package DAO;

import Models.Book;
import Services.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDAO {
    private Connection connection;
    public BookDAO() {
        this.connection = (Connection) DBConnection.
                getInstance().getConnection();
        //this.deleteAllBook();
    }

    public Book getBook(int id) {
        String query = "SELECT * FROM books WHERE id = ?";
        Book book = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                book = new Book(
                        results.getInt("id"),
                        results.getString("title"),
                        results.getString("author"),
                        results.getInt("available_copies")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed to close!");
        }

        return book;
    }

    public boolean addBook(Book book) {
        String query = "INSERT INTO books (title, author, available_copies) " +
                "VALUES (?, ?, ?)";
        boolean success = false;

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, book.title);
            statement.setString(2, book.author);
            statement.setInt(3,book.available_copies);
            success = statement.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return success;
    }

    public boolean updateBook(Book book) {
        String query = "UPDATE books SET title = ?, author = ?, " +
                "available_copies = ? WHERE id = ?";
        boolean success = false;

        try(PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, book.title);
            statement.setString(2, book.author);
            statement.setInt(3,book.available_copies);
            statement.setInt(4, book.id);
            success = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Connection failed to close!");
        }

        return success;
    }

    public boolean deleteBook(int bookId) {
        String query = "DELETE FROM books WHERE id = ?";
        boolean success = false;
        try(PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, bookId);
            success = statement.executeUpdate() > 0;

        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Connection failed to close!");
        }

        return success;
    }

    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";

        try(PreparedStatement statement = connection.prepareStatement(query))
        {
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                Book newBook = new Book(
                        results.getInt("id"),
                        results.getString("title"),
                        results.getString("author"),
                        results.getInt("available_copies")
                         );
                books.add(newBook);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("failed to get books!");
        }

        return books;
    }

    public boolean deleteAllBook() {
        String query = "DELETE FROM books";
        boolean success = false;
        try(PreparedStatement statement = connection.prepareStatement(query))
        {
            success = statement.executeUpdate() > 0;
            System.out.println("Table-books successfully deleted");

        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Connection failed to close!");
        }

        return success;
    }
}


