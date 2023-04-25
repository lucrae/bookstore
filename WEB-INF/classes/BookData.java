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

    public static int addBook(Connection connection, BookData book) {
        String sql ="INSERT INTO Books"
            + "(author, title, publish_year, blurb, genre, cover_image, price, stock) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println("addBook: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setString(1,book.title);
            stmtUpdate.setString(2,book.author);
            stmtUpdate.setInt(3,book.publish_year);
            stmtUpdate.setString(4,book.blurb);
            stmtUpdate.setString(5,book.genre);
            stmtUpdate.setString(6,book.cover_image);
            stmtUpdate.setFloat(7,book.price);
            stmtUpdate.setInt(8,book.stock);
            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in addBook: " + sql + " Exception: " + e);
        }
        return n;
    }

    public static int updateBook(Connection connection, BookData book) {
        String sql ="UPDATE Books "
            + "SET author = ?, title = ?, publish_year = ?, blurb = ?, genre = ?, cover_image = ?, price = ?, stock = ?"
            + " WHERE ID = ?";
        System.out.println("updateUser: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setString(1,book.title);
            stmtUpdate.setString(2,book.author);
            stmtUpdate.setInt(3,book.publish_year);
            stmtUpdate.setString(4,book.blurb);
            stmtUpdate.setString(5,book.genre);
            stmtUpdate.setString(6,book.cover_image);
            stmtUpdate.setFloat(7,book.price);
            stmtUpdate.setInt(8,book.stock);
            stmtUpdate.setInt(9,book.ID);
            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in updateBook: " + sql + " Exception: " + e);
        }
        return n;
    }

    public static BookData getBook(Connection connection, String idStr) {
        String sql = "Select * FROM Books";
        sql += " WHERE ID=?";
        System.out.println("getBook: " + sql);
        BookData book = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, idStr);
            ResultSet result = pstmt.executeQuery();
            if(result.next()) {
                book = new BookData(
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
            }
            result.close();
            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getBook: " + sql + " Exception: " + e);
        }
        return book;
    }

    public static Vector<BookData> getByGenreAndSearch(Connection connection, String genre, String search) {
        Vector<BookData> vec = new Vector<BookData>();
        String sql = "SELECT * FROM books WHERE title IN("+ search + ") OR author IN("+ search + ") AND genre=" + genre;

        System.out.println("getByGenreAndSearch: " + sql);

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
    
   
}
