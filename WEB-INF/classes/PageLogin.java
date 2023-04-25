import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class PageLogin extends HttpServlet {
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
        toClient.println("<form action='CheckLogin'>");
        toClient.println("<h1>Log in</h1>");
        toClient.println("<p><input type='text' placeholder='Email' name='email'></p>");
        toClient.println("<p><input type='password'  placeholder='Password' name='password'></p>");
        toClient.println("<input type='submit' value='Log in'>");
        toClient.println("</form>");
        toClient.println("<p>Need an account? <a href='/register.html'>Make an account!</a></p>");
        toClient.println("</div>");


        toClient.println(Utils.footer());
        toClient.close();
    }
}