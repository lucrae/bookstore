import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class PageAccount extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();

        toClient.println(Utils.header("Account"));
        Integer userId = Integer.parseInt(req.getParameter("userId"));



        Vector<ReviewData> reviews = ReviewData.getUserReviews(connection, userId);

        toClient.println("<div class='header'>");
        toClient.println("<div class='header-title'>DKL Bookstore</div>");
        toClient.println("<div><a style='padding-right:15px' href='Edit?userId="+userId+"'>Settings</a><a href='Start''>Sign Out</a></div>");
        toClient.println("</div>");

        toClient.println("<div class='body'>");
        toClient.println("<a href='Main?userId="+userId+"'>< Back</a>");
        toClient.println("<br>");
        toClient.println("<br>");
        toClient.println("<br>");
        toClient.println("<h2>Purchased books</h2>");
        toClient.println("<div class='account-books'>");

        // Previews

        toClient.println("<div style='flex:2' class='book-preview-column'>");

        for (int i=0; i < reviews.size(); i++) {
            ReviewData review = reviews.elementAt(i);

            BookData book = BookData.getBook(connection, review.book_id);

            toClient.println("<div class='book-preview'>");
            toClient.println("<div><img class='cover' src='" + book.cover_image + "'></div>");
            toClient.println("<div class='info'>");
            toClient.println("<a href='Book?bookId=" + book.ID + "&userId=" + userId + "'><b>" + book.title + "</b></a><br>");
            toClient.println("<i>" + book.author + " (" + book.publish_year + ")</i>");
            toClient.println("<p>" + book.blurb + "</p>");
            toClient.println("</div>");
            toClient.println("</div>");

            if (review.rating > 0) {
                toClient.println("<b>Your review:</b>");

                toClient.println("<span>");
                for (int j=0; j < review.rating; j++){
                    toClient.println("&#9733; ");
                }
                toClient.println("</span>");
                toClient.println("<p>" + review.description + "</p>");  

                toClient.println("<b>Edit Review:</b>");
            } else {
                toClient.println("<b>Write Review:</b>");
            }

            toClient.println("<form action='ReviewUpdate'>");

            toClient.println("<input type='hidden' name='reviewId' value='" + review.ID + "' /> ");
            toClient.println("<input type='hidden' name='bookId' value='" + review.book_id + "' /> ");
            toClient.println("<input type='hidden' name='accountId' value='" + review.account_id + "' /> ");
            
            toClient.println("<select id='rating' name='rating'>");
            
            toClient.println("<option value='1'>&#9733;</option>");
            toClient.println("<option value='2'>&#9733;&#9733;</option>");
            toClient.println("<option value='3'>&#9733;&#9733;&#9733;</option>");
            toClient.println("<option value='4'>&#9733;&#9733;&#9733;&#9733;</option>");
            toClient.println("<option value='5'>&#9733;&#9733;&#9733;&#9733;&#9733;</option>");
            toClient.println("</select>");

            toClient.println("<br>");

            toClient.println("<textarea name='description' rows='3' cols='50'>" + review.description + "</textarea>");

            toClient.println("<br>");

            toClient.println("<input type='submit' value='Update'>");
            toClient.println("</form>");
        }

        toClient.println("</div>");

        toClient.println("</div>");
        toClient.println(Utils.footer());
    }
}
