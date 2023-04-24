import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

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

    public static Vector<BookData> getLatestBooks(Connection connection) {
        Vector<BookData> vec = new Vector<BookData>();
        String sql = "SELECT TOP 5 * FROM Books ORDER BY ID DESC";
        System.out.println("getLatestBooks: " + sql);
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
            System.out.println("Error in getLatestBooks: " + sql + " Exception: " + e);
        }
        return vec;
    }

    public static Vector<BookData> getByGenre(Connection connection, String genre) {
        Vector<BookData> vec = new Vector<BookData>();
        String sql = "SELECT * FROM Books WHERE genre=?";
        System.out.println("getByGenre: " + sql);

        try {
            PreparedStatement pstmt=connection.prepareStatement(sql);
            pstmt.setString(1, genre);
            ResultSet result = pstmt.executeQuery();
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
            System.out.println("Error in getByGenre: " + sql + " Exception: " + e);
        }
        return vec;
    }

    public static Vector<BookData> getBySearch(Connection connection, String text) {
        Vector<BookData> vec = new Vector<BookData>();
        String sql = "SELECT * FROM Books WHERE " + text + " IN(title, author)";
        System.out.println("getBySearch: " + sql);

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
            System.out.println("Error in getByGenre: " + sql + " Exception: " + e);
        }
        return vec;
    }

    public static Vector<BookData> getByYear(Connection connection) {
        Vector<BookData> vec = new Vector<BookData>();
        String sql = "SELECT * FROM Books ORDER BY publish_year DESC";
        System.out.println("getByYear: " + sql);

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
            System.out.println("Error in getByYear: " + sql + " Exception: " + e);
        }
        return vec;
    }

   
}
