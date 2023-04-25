import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class FilterBooks extends HttpServlet {
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

        toClient.println("<h2>Search books</h2>");
        toClient.println("<form action='FilterBooks'>");
        toClient.println("<input type='text' placeholder='Author/title (optional)' name='author_or_title'> ");
        toClient.println("<select name='genre'>");
        toClient.println("<option value=''>Genre (optional)</option>");
        toClient.println("<option value='horror'>Horror</option>");
        toClient.println("<option value='romance'>Romance</option>");
        toClient.println("<option value='science-fiction'>Science-fiction</option>");
        toClient.println("<option value='young-adult'>Young Adult</option>");
        toClient.println("<option value='children'>Children</option>");
        toClient.println("</select>");

        toClient.println("<select name='sortFilter'>");
        toClient.println(" <option value='sortRating'>Sort by rating</option>");
        toClient.println("<option value='sortYear'>Sort by year</option>");
        toClient.println(" </select>");
        
        toClient.println("<input type='submit' value='Search'>");
        toClient.println(" </form>");
   

        toClient.println(Utils.footer());
        toClient.close();
    }
}