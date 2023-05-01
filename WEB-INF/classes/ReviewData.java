import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class ReviewData {
    Integer ID;
    Integer account_id;
    Integer book_id;
    Integer rating;
    String description;

    ReviewData(Integer ID, Integer account_id, Integer book_id, Integer rating, String description){
        this.ID = ID;
        this.account_id = account_id;
        this.book_id = book_id;
        this.rating = rating;
        this.description = description;
    }

    public static Vector<ReviewData> getReviews(Connection connection, Integer bookID) { // by book
        Vector<ReviewData> vec = new Vector<ReviewData>();
        String sql = "SELECT * FROM Reviews WHERE book_id=" + bookID + " AND rating <> 0";
        System.out.println("getReviews: " + sql);
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                ReviewData review = new ReviewData(
                    Integer.parseInt(result.getString("ID")),
                    Integer.parseInt(result.getString("account_id")),
                    bookID,
                    Integer.parseInt(result.getString("rating")),
                    result.getString("description")
                );
                vec.addElement(review);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getReviews: " + sql + " Exception: " + e);
        }
        return vec;
    }

    public static Vector<ReviewData> getUserReviews(Connection connection, Integer userId) { // by book
        Vector<ReviewData> vec = new Vector<ReviewData>();
        String sql = "SELECT * FROM Reviews WHERE account_id=" + userId;
        System.out.println("getReviews: " + sql);
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                ReviewData review = new ReviewData(
                    Integer.parseInt(result.getString("ID")),
                    Integer.parseInt(result.getString("account_id")),
                    Integer.parseInt(result.getString("book_id")),
                    Integer.parseInt(result.getString("rating")),
                    result.getString("description")
                );
                vec.addElement(review);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getUserReviews: " + sql + " Exception: " + e);
        }
        return vec;
    }

    public static int addReview(Connection connection, ReviewData review) {
        String sql ="INSERT INTO Reviews (ID, account_id, book_id, rating, description)"
            + " VALUES (?, ?, ?, ?, ?)";
        System.out.println("addReview: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setInt(1, review.ID);
            stmtUpdate.setInt(2, review.account_id);
            stmtUpdate.setInt(3, review.book_id);
            stmtUpdate.setInt(4, review.rating);
            stmtUpdate.setString(5, review.description);
            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in addReview: " + sql + " Exception: " + e);
        }
        return n;
    }

    public static int updateReview(Connection connection, ReviewData review) {
        String sql ="UPDATE Reviews "
            + "SET rating = ?, description = ?"
            + " WHERE ID = ?";
        System.out.println("updateUser: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setInt(1, review.rating);
            stmtUpdate.setString(2, review.description);
            stmtUpdate.setInt(3, review.ID);
            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in updateReview: " + sql + " Exception: " + e);
        }
        return n;
    }
}