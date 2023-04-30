import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class BookList extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        // String city = req.getParameter("ciudad");

        toClient.println("<table border='1'>");
        toClient.println("<tr>");
        toClient.println("<td>ID</td>");
        toClient.println("<td>title</td>");
        toClient.println("<td>author</td>");
        toClient.println("<td>cover_image</td>");
        toClient.println("</tr>");

        Vector<BookData> bookList = BookData.getByRatingDesc(connection); 


        for(int i=0; i< bookList.size(); i++){
                BookData book = bookList.elementAt(i);
                toClient.println("<tr>");
                toClient.println("<td>" + book.ID + " </td>");
                toClient.println("<td>" + book.title + " </td>");
                toClient.println("<td>" + book.author + " </td>");
                toClient.println("<td><img src='" + book.cover_image + "'> </td>");
                toClient.println("</tr>");
        }

        toClient.println("</table>");

        Double avgRating = BookData.getBookRating(connection, 1);
        toClient.println("<h1>Rating:  " + avgRating + "</h1>");

        toClient.close();
    }
}