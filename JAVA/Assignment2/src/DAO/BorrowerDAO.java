package DAO;

import Models.Borrower;
import Services.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BorrowerDAO {
    private Connection connection;
    public BorrowerDAO() {
        this.connection = (Connection) DBConnection.getInstance().getConnection();
    }

    public boolean addBorrower(Borrower borrower ) {
        String query = "INSERT INTO borrowers (name, email) " +
                "VALUES (?, ?)";
        boolean success = false;

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, borrower.name);
            statement.setString(2, borrower.email);
            success = statement.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return success;
    }

    public boolean  updateBorrower(Borrower borrower) {
        String query = "UPDATE borrower SET name = ?, email = ? WHERE id = ?";
        boolean success = false;

        try(PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, borrower.name);
            statement.setString(2, borrower.email);
            success = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Connection failed to close!");
        }

        return success;
    }

    public boolean deleteBorrower(int borrowerId) {
        String query = "DELETE FROM borrower WHERE id = ?";
        boolean success = false;
        try(PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, borrowerId);
            success = statement.executeUpdate() > 0;

        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Connection failed to close!");
        }

        return success;
    }

    public ArrayList<Borrower> getAllBorrowers() {
        ArrayList<Borrower> borrower = new ArrayList<>();
        String query = "SELECT * FROM borrowers";

        try(PreparedStatement statement = connection.prepareStatement(query))
        {
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                Borrower newBorrower = new Borrower(
                        results.getInt("id"),
                        results.getString("name"),
                        results.getString("email")
                );
                borrower.add(newBorrower);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("failed to get books!");
        }

        return borrower;
    }
}
