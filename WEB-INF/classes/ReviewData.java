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
        String sql = "SELECT * FROM Reviews WHERE book_id=" + bookID;
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

}