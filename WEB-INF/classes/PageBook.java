import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class PageBook extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();

        toClient.println(Utils.header("Book Info"));

        Integer bookId = Integer.parseInt(req.getParameter("bookId"));
        Integer userId = Integer.parseInt(req.getParameter("userId"));

        BookData book = BookData.getBook(connection, bookId);
        Double bookAvg = BookData.getBookRating(connection, bookId);
        int roundedRating = (int) Math.round(bookAvg);

        toClient.println("<div class='header header-title'>DKL Bookstore</div>");

        toClient.println("<div class='body'>");
        toClient.println("<a href='Main?userId=" + userId + "'>< Back</a><br></br>");
        
        toClient.println("<div class='left-right'>");


        toClient.println("<div>");
        toClient.println("<img class='cover' src='" + book.cover_image + "'>");
        toClient.println("<p><form action='Buy'>");
        toClient.println("<input type='hidden' name='bookId' value='" + bookId + "' /> ");
        toClient.println("<input type='hidden' name='userId' value='" + userId + "' /> ");
        toClient.println("<input type='submit' value='Buy' class='full-width' />");
        toClient.println("</form></p>");
        toClient.println("</div>");

        toClient.println("<div>");
        toClient.println("<h1>" + book.title + "</h1>");
        toClient.println("<i>" + book.author + " (" + book.publish_year + ")</i><i style='padding-left:100px;'>Available books:" + book.stock + "</i>");
        
        if (roundedRating > 0) {
            toClient.println("<p>");
            for (int j=0; j < roundedRating; j++){
                toClient.println("&#9733; ");
            }
            toClient.println("<i> (Average "+ bookAvg +")</i>");
            toClient.println("</p>");
        }

        toClient.println("<p>" + book.blurb + "</p>");
        toClient.println("</div>");

        toClient.println("</div>");



        Vector<ReviewData> reviews = ReviewData.getReviews(connection, bookId);

        toClient.println("<h2>User Reviews</h2>");
        toClient.println("<div class='review-column'>");

        for (int i=0; i < reviews.size(); i++) {
            ReviewData review = reviews.elementAt(i);

            UserData reviewUser = UserData.getUser(connection, String.valueOf(review.account_id));

            toClient.println("<div class='review'>");
            toClient.println("<span>");
            for (int j=0; j < review.rating; j++){
                toClient.println("&#9733; ");
            }
            toClient.println("</span>");
            toClient.println("<p>" + review.description + "</p>");
            toClient.println("<i>- " + reviewUser.first_name + " " + reviewUser.last_name + "</i>");
            toClient.println("</div>");
        }

        if (reviews.size() == 0) {
            toClient.println("<i>No reviews for this book yet...</i>");
        }

        toClient.println("</div>");
        toClient.println(Utils.footer());
    }
}