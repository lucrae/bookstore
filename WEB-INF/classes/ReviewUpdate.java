import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class ReviewUpdate extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");

        Integer accountId = Integer.parseInt(req.getParameter("accountId"));        

        ReviewData review = new ReviewData(
                    Integer.parseInt(req.getParameter("reviewId")),
                    accountId,
                    Integer.parseInt(req.getParameter("bookId")),
                    Integer.parseInt(req.getParameter("rating")),
                    req.getParameter("description")
                );
        int n = ReviewData.updateReview(connection, review);
        res.sendRedirect("Account?userId=" + accountId);
    }
}