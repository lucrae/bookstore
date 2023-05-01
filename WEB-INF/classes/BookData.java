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

    public static BookData getBook(Connection connection, Integer bookId) {
        String sql = "Select * FROM Books WHERE id=" + bookId;
        System.out.println("getBookList: " + sql);
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            result.next();

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

            return book;

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getBookList: " + sql + " Exception: " + e);
        }
        return null;
    }

    public static Integer buyBook(Connection connection, Integer bookId) {
        BookData myBook = getBook(connection, bookId);
        Integer newStock = myBook.stock - 1;
        String sql = "UPDATE Books SET stock = ?";
        System.out.println("buyBook: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate=connection.prepareStatement(sql);
            stmtUpdate.setInt(1, newStock);
            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getBookList: " + sql + " Exception: " + e);
        }
        return n;
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
        String sql = "SELECT * FROM Books WHERE (title LIKE '%"+text+"%' OR author LIKE '%"+text+"%')";

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
            System.out.println("Error in getBySearch: " + sql + " Exception: " + e);
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
        System.out.println("updateBook: " + sql);
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
        String sql = "SELECT * FROM books WHERE (title LIKE " + search + " OR author LIKE " + search + ") AND genre=" + genre;

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

    public static Vector<BookData> getBySort(Connection connection, String sortBy) {
        Vector<BookData> vec = new Vector<BookData>();
        String sql = "SELECT * FROM Books ORDER BY " + sortBy + " DESC";
        System.out.println("getBySort: " + sql);

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

    public static Vector<BookData> getByRatingDesc(Connection connection) {
        Vector<BookData> vec = new Vector<BookData>();
        String sql = "SELECT Books.ID, Avg(Reviews.rating) AS AvgOfrating ";
        sql = sql + "FROM Books INNER JOIN Reviews ON Books.ID = Reviews.book_id ";
        sql = sql + "GROUP BY Books.ID ";
        sql = sql + "ORDER BY Avg(Reviews.rating) DESC";

        //query of book ID and rating in descending order

        System.out.println("getByRatingDesc: " + sql);

        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                BookData book = BookData.getBook(connection, result.getString("ID"));
                vec.addElement(book);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getByRatingDesc: " + sql + " Exception: " + e);
        }
        return vec;
    }

    public static double getBookRating(Connection connection, int bookId) {
    double avgRating = 0;
    String sql = "SELECT Books.ID, Books.title, Avg(Reviews.rating) AS AvgOfrating ";
    sql = sql + "FROM Books INNER JOIN Reviews ON Books.ID = Reviews.book_id WHERE Reviews.rating <> 0 ";
    sql = sql + "GROUP BY Books.ID, Books.title ";
    sql = sql + "HAVING (((Books.ID)=" + bookId + ")) ";

    System.out.println("getBookRating: " + sql);

    try {
        Statement statement=connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while(result.next()) {
            avgRating = result.getDouble("AvgOfrating");
        }
    } catch(SQLException e) {
        e.printStackTrace();
        System.out.println("Error in getBookRating: " + sql + " Exception: " + e);
    }
    return avgRating;
}


    public static Vector<BookData> getByAll(Connection connection, String genre, String search, String orderBy) {
        Vector<BookData> vec = new Vector<BookData>();
        String sql = "SELECT * FROM books WHERE (title LIKE " + search + " OR author LIKE " + search + ") AND genre=" + genre + " ORDER BY " + orderBy + " DESC";

        System.out.println("getByAll: " + sql);

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
            System.out.println("Error in getByAll: " + sql + " Exception: " + e);
        }
        return vec;
    }

      public static Vector<BookData> getByGenreAndSort(Connection connection, String genre, String orderBy) {
        Vector<BookData> vec = new Vector<BookData>();
        String sql = "SELECT * FROM Books WHERE genre LIKE " + genre + " ORDER BY " + orderBy + " DESC";

        System.out.println("getByGenreAndSort: " + sql);

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
            System.out.println("Error in getByGenreAndSort: " + sql + " Exception: " + e);
        }
        return vec;
    }

    public static Vector<BookData> getBySearchAndSort(Connection connection, String text, String sortBy) {
        Vector<BookData> vec = new Vector<BookData>();
        String sql = "SELECT * FROM Books WHERE title LIKE " + text + " OR author LIKE " + text + " ORDER BY " + sortBy + " DESC";
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
            System.out.println("Error in getBySearch: " + sql + " Exception: " + e);
        }
        return vec;
    }

    public static Vector<BookData> getByGenreAndRating(Connection connection, String genre) {
        Vector<BookData> vec = new Vector<BookData>();
        String sql = "SELECT Books.ID, Avg(Reviews.rating) AS AvgOfrating, Books.genre ";
        sql = sql + "FROM Books INNER JOIN Reviews ON Books.ID = Reviews.book_id ";
        sql = sql + "GROUP BY Books.ID, Books.genre ";
        sql = sql + "HAVING (((Books.genre)=" + genre + ")) ";
        sql = sql + "ORDER BY Avg(Reviews.rating) DESC";

        System.out.println("getBySearch: " + sql);

        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                BookData book = BookData.getBook(connection, result.getString("ID"));
                vec.addElement(book);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getByGenreAndRating: " + sql + " Exception: " + e);
        }
        return vec;
    }

     public static Vector<BookData> getBySearchAndRating(Connection connection, String author_or_title) {
        Vector<BookData> vec = new Vector<BookData>();
        String sql = "SELECT Books.ID, Avg(Reviews.rating) AS AvgOfrating, Books.author ";
        sql = sql + "FROM Books INNER JOIN Reviews ON Books.ID = Reviews.book_id ";
        sql = sql + "WHERE Books.author = " + author_or_title + " OR Books.title LIKE " + author_or_title;
        sql = sql + " GROUP BY Books.ID, Books.author ";
        sql = sql + "ORDER BY Avg(Reviews.rating) DESC";

        System.out.println("getBySearch: " + sql);

        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                BookData book = BookData.getBook(connection, result.getString("ID"));
                vec.addElement(book);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getByGenreAndRating: " + sql + " Exception: " + e);
        }
        return vec;
    }

    public static Vector<BookData> getByAllRating(Connection connection, String genre, String author_or_title) {
        Vector<BookData> vec = new Vector<BookData>();
        String sql = "SELECT Books.ID, AVG(Reviews.rating) AS AvgOfrating, Books.author ";
        sql = sql + "FROM Books "; 
        sql = sql + "INNER JOIN Reviews ON Books.ID = Reviews.book_id ";
        sql = sql + "WHERE (Books.author LIKE " + author_or_title + " OR Books.title LIKE " + author_or_title +") ";
        sql = sql + "AND Books.genre =" + genre;
        sql = sql + " GROUP BY Books.ID, Books.author ORDER BY AVG(Reviews.rating) DESC";

        System.out.println("getByAllRating: " + sql);

        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                BookData book = BookData.getBook(connection, result.getString("ID"));
                vec.addElement(book);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getByAllRating: " + sql + " Exception: " + e);
        }
        return vec;
    }


    public static Vector<BookData> filterCriteria(Connection connection, String author_or_title, String genre, String sortFilter) {
  
        if(genre.equals("") && author_or_title.equals("") && sortFilter.equals("")) { //no filter
            Vector<BookData> bookList = BookData.getBookList(connection); 
            return bookList;
        }
        
        if(sortFilter.equals("")){
            if(author_or_title.equals("")){  //genre only

                Vector<BookData> bookList = BookData.getByGenre(connection, genre); 
                return bookList;

            }else if(genre.equals("")){ // author only

                Vector<BookData> bookList = BookData.getBySearch(connection, author_or_title); 
                return bookList;

            }else{
                Vector<BookData> bookList = BookData.getByGenreAndSearch(connection, "'" + genre + "'", "'%" + author_or_title + "%'"); 
                return bookList;
                
            }
        }else{ //there is an order filter


            if(author_or_title.equals("")){ //if there is no author 

                if(genre.equals("")) {// no author nor genre

                    if(sortFilter.equals("publish_year")){
                        Vector<BookData> bookList = BookData.getBySort(connection, sortFilter); 
                        return bookList;
                    }
                    else{
                        Vector<BookData> bookList = BookData.getByRatingDesc(connection); 
                        return bookList;
                    }
                   

                }else{ // genre + filter
                
                    if(sortFilter.equals("publish_year")){
                        Vector<BookData> bookList = BookData.getByGenreAndSort(connection, "'" + genre + "'", sortFilter); 
                        return bookList;
                    }else{
                        Vector<BookData> bookList = BookData.getByGenreAndRating(connection, "'" + genre + "'"); 
                        return bookList;
                    }
                    

                }  
            }else{// there is author 

                if(genre.equals("")) {   // author + filter

                    if(sortFilter.equals("publish_year")){
                        Vector<BookData> bookList = BookData.getBySearchAndSort(connection, "'%" + author_or_title + "%'", sortFilter);
                        return bookList;
                    }else{
                        Vector<BookData> bookList = BookData.getBySearchAndRating(connection, "'%" + author_or_title + "%'");
                        return bookList;
                    }
                    
                    

                }else{ //ALL FILTERS

                    if(sortFilter.equals("publish_year")){
                        Vector<BookData> bookList = BookData.getByAll(connection, "'" + genre + "'", "'%" + author_or_title + "%'", sortFilter); 
                        return bookList;
                    }else{
                        Vector<BookData> bookList = BookData.getByAllRating(connection, "'" + genre + "'", "'%" + author_or_title + "%'"); 
                        return bookList;

                    }
                    
                }

            }
        }

    }
           

}
    
   

