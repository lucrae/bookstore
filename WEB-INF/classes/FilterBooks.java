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
        Integer userId = Integer.parseInt(req.getParameter("userId"));
        String userIdStr = req.getParameter("userId");
        UserData myUser = UserData.getUser(connection, userIdStr);

        Vector<BookData> resultFilter = BookData.filterCriteria(connection, author_or_title, genre, sortFilter);

      toClient.println("<div class='header'>");
        toClient.println("<div class='header-title'>DKL Bookstore</div>");
        toClient.println("<div><a style='padding-right:15px' href='Account?userId="+userId+"'>Account</a></div>");
        
        if(myUser.is_admin == 1){ // only appears if you are an admin
            
            toClient.println("<a style='padding-right:15px' href='Admin'>Admin</a>");

        }

        toClient.println("</div>");
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


   //print results from previous page
    toClient.println("<p>" + (resultFilter.size())+  "   Results:</p>");
    toClient.println("<div class='book-preview-column'>");

        for(int i=0; i< resultFilter.size(); i++){
            BookData book = resultFilter.elementAt(i);

            String link = "Book?bookId=" + book.ID + "&userId=" + userId;
            
            toClient.println("<div class='book-preview'>");
            toClient.println("<div><div class='cover'><img class='cover' src='" + book.cover_image + "'></div></div>");
            toClient.println("<div class='info'>");
            toClient.println("<a href='" + link + "'><b>" + book.title + "</b></a><br><br>");
            toClient.println("<i>" + book.author + "   " + book.publish_year + "</i><i style='padding-left:450px;'>Available books:  " + book.stock + "</i>");
            Double bookAvg = BookData.getBookRating(connection, book.ID);
            int roundedRating = (int) Math.round(bookAvg);
        
            toClient.println("<p>");
            for (int j=0; j < roundedRating; j++){
                toClient.println("&#9733; ");
            }

            toClient.println("<i> (Average "+ bookAvg +")</i></p>");
            toClient.println("<p>" + book.blurb + "</p> ");
            toClient.println("</div>");
            toClient.println("</div>");

        }

        
        toClient.println("</div>");

    
        toClient.println(Utils.footer());
        toClient.close();
    }
}