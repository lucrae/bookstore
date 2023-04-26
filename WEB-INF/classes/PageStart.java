import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class PageStart extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();

        toClient.println(Utils.header("Start"));

        // String city = req.getParameter("ciudad");

        toClient.println("<div class='header header-title'>DKL Bookstore</div>");

        toClient.println("<div class='body'>");
        toClient.println("<h2>Latest releases</h2>");
        toClient.println("<div class='cover-row'>");

        Vector<BookData> bookList = BookData.getLatestBooks(connection);
        for(int i=0; i< bookList.size(); i++){
                BookData book = bookList.elementAt(i);
                toClient.println("<img class='cover' src='" + book.cover_image + "'>");
        }

        toClient.println("</div>");

        toClient.println("<h2>Create an account to browse and buy!</h2");
        toClient.println("<form action='/Register'><input type='submit' value='Create an account' /></form>");
        toClient.println("<p>Already have an account? <a href='Login'>Log in</a></p>");

        toClient.println(Utils.footer());
        toClient.close();
    }
}