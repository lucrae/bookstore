import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class PageMain extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();

        toClient.println(Utils.header("Start"));

        Integer userId = Integer.parseInt(req.getParameter("userId"));

        toClient.println("<div class='header header-title'>DKL Bookstore</div>");

        toClient.println("<div class='body'>");
        toClient.println("<h2>Latest releases</h2>");
        toClient.println("<div class='cover-row'>");

        Vector<BookData> bookList = BookData.getLatestBooks(connection);
        for(int i=0; i< bookList.size(); i++){
            BookData book = bookList.elementAt(i);
            String link = "Book?bookId=" + book.ID + "&userId=" + userId;
            toClient.println("<a href='" + link + "'><img class='cover' src='" + book.cover_image + "'></a>");
        }
        toClient.println("</div>");

        toClient.println("<h2>Search books</h2>");
        toClient.println("<form action='FilterBooks'>");
        toClient.println("<input type='hidden' name='userId' value=" + userId +">");
        toClient.println("<input type='text' placeholder='Author/title (optional)' name='author_or_title' value=''> ");
        toClient.println("<select name='genre'>");
        toClient.println("<option value=''>Genre (optional)</option>");
        toClient.println("<option value='Horror'>Horror</option>");
        toClient.println("<option value='Romance'>Romance</option>");
        toClient.println("<option value='Science Fiction'>Science-fiction</option>");
        toClient.println("<option value='Young Adult'>Young Adult</option>");
        toClient.println("<option value='Children'>Children</option>");
        toClient.println("</select>");

        toClient.println("<select name='sortFilter'>");
        toClient.println("<option value=''>Order By (optional)</option>");
        toClient.println("<option value=rating>Sort by rating</option>");
        toClient.println("<option value=publish_year>Sort by year</option>");
        toClient.println(" </select>");
        
        toClient.println("<input type='submit' value='Search'>");
        toClient.println(" </form>");

        
    }
}