import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class PageBuy extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();

        Integer bookId = Integer.parseInt(req.getParameter("bookId"));
        Integer userId = Integer.parseInt(req.getParameter("userId"));

        // Custom head for card entry styling
        toClient.println("<!DOCTYPE html>");
        toClient.println("<html lang='en'>");
        toClient.println("<head>");
        toClient.println("<link rel='stylesheet' href='style.css'>");
        toClient.println("<title>Buy</title>");
        toClient.println("</head>");
        toClient.println("<style>.card {background-color: white;padding: 20px;border-radius: 5px;box-shadow: 0 2px 4px rgba(0,0,0,0.1);max-width: 500px;margin: 0 auto;margin-top: 50px;}label {display: block;margin-bottom: 5px;font-weight: bold;}");
        toClient.println("input[type='text'], input[type='number'], select {width: 100%;padding: 10px;border-radius: 5px;border: 1px solid #ccc;box-sizing: border-box;margin-bottom: 15px;font-size: 16px;}.expiry-date-group {display: flex;align-items: center;}.expiry-date-group label {margin-bottom: 0;}.expiry-date-group input[type='number'] width: 48%margin-right: 2%;}</style>");
        toClient.println("<body>");

        toClient.println("<div class='frame frame-small'>");

        toClient.println("<div class='header header-title'>DKL Bookstore</div>");


        
        toClient.println("<h1>Card Details</h1>");

        toClient.println("<form action='BuyBook'>");
        toClient.println("<input type='hidden' name='userId' value='" + userId + "' /> ");
        toClient.println("<input type='hidden' name='bookId' value='" + bookId + "' /> ");
        toClient.println("<label for='card-number'>Card Number</label>");
        toClient.println("<input type='text' id='card-number' name='card-number' placeholder='1234 1234 1234 1234' required>");
        toClient.println("<label for='cardholder-name'>Cardholder Name</label>");
        toClient.println("<input type='text' id='cardholder-name' name='cardholder-name' placeholder='Name' required>");
        toClient.println("<label for='expiry-date-group'>Expiry Date</label>");
        toClient.println("<div class='expiry-date-group'>");
        toClient.println("<input style='width: 60px;' type='number' id='expiry-month' name='expiry-month' min='1' max='12' value='1' required>");
        toClient.println("<input style='width: 60px;' type='number' id='expiry-year' name='expiry-year' min='22' max='99' value='24' required>");
        toClient.println("</div>");
        toClient.println("<label for='cvv'>CVV</label>");
        toClient.println("<input style='width:10ch' type='number' id='cvv' name='cvv' min='100' max='999' placeholder='123' required>");
        toClient.println("<br>");
        toClient.println("<input style='background-color:#4CAF50;color: white;' type='submit' value='Submit'>	");
        toClient.println("</form>");
        // toClient.println("<form action='Account'>");
        // toClient.println("<input style='background-color: #af4c4c' type='submit' value='Cancel'>");
        // toClient.println("</form>");

        toClient.println("</div>");

        toClient.println("</body></html>");
    }
}