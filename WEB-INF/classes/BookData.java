import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookData {
    Integer ID;
    String title;
    String author;
    Integer publish_year;
    String blurb;
    String genre;
    String cover_image;
    Float price;
    Integer stock;
    
    BookData(Integer ID, String title, String author, Integer publish_year, String blurb, String genre, String cover_image, Float price, Integer stock) {
        this.ID = ID;
        this.title = title;
        this.author = author;
        this.publish_year = publish_year;
        this.blurb = blurb;
        this.genre = genre;
        this.cover_image = cover_image;
        this.price = price;
        this.stock = stock;
    }

    public static Vector<BookData> getBookList(Connection connection) {
        Vector<BookData> vec = new Vector<BookData>();
        String sql = "Select * FROM Books";
        System.out.println("getBookList: " + sql);
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                BookData book = new BookData(
                    Integer.parseInt(result.getString("ID")),
                    result.getString("title"),
                    result.getString("author"),
                    Integer.parseInt(result.getString("publish_year")),
                    result.getString("blurb"),
                    result.getString("genre"),
                    result.getString("cover_image"),
                    Float.parseFloat(result.getString("price")),
                    Integer.parseInt(result.getString("stock"))
                );
                vec.addElement(book);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getBookList: " + sql + " Exception: " + e);
        }
        return vec;
    }
}
