import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.util.Random;

@SuppressWarnings("serial")
public class BuyBook extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        Random rand = new Random(); 

        res.setContentType("text/html");
        Integer userId = Integer.parseInt(req.getParameter("userId"));
        Integer bookId = Integer.parseInt(req.getParameter("bookId"));

        ReviewData review = new ReviewData(
                rand.nextInt(999999),
                userId,
                bookId,
                0,
                ""
            );
        int n = ReviewData.addReview(connection, review);
        res.sendRedirect("Account?userId=" + userId.toString());
    }
}