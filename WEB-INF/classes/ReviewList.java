import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class ReviewList extends HttpServlet {
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
        toClient.println("<td>account</td>");
        toClient.println("<td>book</td>");
        toClient.println("<td>rating</td>");
        toClient.println("<td>description</td>");
        toClient.println("</tr>");

        Vector<ReviewData> reviewList = ReviewData.getReviews(connection, 1); 


        for(int i=0; i< reviewList.size(); i++){
                ReviewData review = reviewList.elementAt(i);
                toClient.println("<tr>");
                toClient.println("<td>" + review.ID + " </td>");
                toClient.println("<td>" + review.account_id + " </td>");
                toClient.println("<td>" + review.book_id + " </td>");
                toClient.println("<td>" + review.rating + " </td>");
                toClient.println("<td>" + review.description + " </td>");
                
                toClient.println("</tr>");
        }

        toClient.println("</table>");

        toClient.close();
    }
}