import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class BookAdd extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
    

        String title = req.getParameter("title");
        String author = req.getParameter("author");
        Integer publish_year = Integer.parseInt(req.getParameter("publish_year"));
        String blurb = req.getParameter("blurb");
        String genre = req.getParameter("genre");
        String cover_image = req.getParameter("cover_image");
        Float price = Float.parseFloat(req.getParameter("price"));
        Integer stock = Integer.parseInt(req.getParameter("stock"));

        Vector<BookData> bookList = BookData.getBookList(connection);
        int newId = bookList.size() + 1;
        BookData newBook = new BookData(newId, title, author, publish_year, blurb, genre, cover_image, price, stock);
        int nAffected = BookData.addBook(connection, newBook);
        res.sendRedirect("AdminBooks");
    }
}