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

        String genre = req.getParameter("genre");
        String author_or_title = req.getParameter("author_or_title");
        String sortFilter = req.getParameter("sortFilter");

        Vector<BookData> resultFilter = BookData.filterCriteria(connection, author_or_title, genre, sortFilter);

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
        toClient.println("<option value='rating'>Sort by rating</option>");
        toClient.println("<option value=publish_year>Sort by year</option>");
        toClient.println(" </select>");
        
        toClient.println("<input type='submit' value='Search'>");
        toClient.println(" </form>");

    // FilterBooks?author_or_title=&genre=&sortFilter=sortRating
    //obtain vector depending on parameters
   

   //print results
    toClient.println("<p>" + (resultFilter.size())+  "   Results:</p>");
    toClient.println("<div class='book-preview-column'>");

        for(int i=0; i< resultFilter.size(); i++){
            BookData book = resultFilter.elementAt(i);
            
            toClient.println("<div class='book-preview'>");
            toClient.println("<div><div class='cover'><img src='" + book.cover_image + "'></div></div>");
            toClient.println("<div class='info'>");
            toClient.println("<a href='book.html'><b>What is a book?</b></a><br><br>");
            toClient.println("<i>" + book.author + "   " + book.publish_year + "</i><i style='padding-left:450px;'>Available books:  " + book.stock + "</i>");
            toClient.println("<p>★★★★ <i>(Avg. 3.1)</i></p>");
            toClient.println("<p>" + book.blurb + "</p> ");
            toClient.println("</div>");
            toClient.println("</div>");
            
/*
                toClient.println("<tr>");
                toClient.println("<td>" + book.ID + " </td>");
                toClient.println("<td>" + book.title + " </td>");
                toClient.println("<td>" + book.author + " </td>");
                toClient.println("<td><img src='" + book.cover_image + "'> </td>");
                toClient.println("</tr>");

                */
        }

        
        toClient.println("</div>");

    
        toClient.println(Utils.footer());
        toClient.close();
    }
}