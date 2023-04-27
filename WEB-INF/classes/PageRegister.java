import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class PageRegister extends HttpServlet {
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
        toClient.println("<form action='RegisterUser'>");
        toClient.println("<h1>Make an account</h1>");
        toClient.println("<p><input type='text' placeholder='First Name' name='first_name' required></p>");
        toClient.println("<p><input type='text' placeholder='Last Name/s' name='last_name' required></p>");
        toClient.println("<p><input type='text' placeholder='Email' name='email' required></p>");
        toClient.println("<p><input type='text' placeholder='Your street name' name='street' required></p>");
        toClient.println("<p><input type='text' placeholder='Postal code' name='postal_code' required></p>");
        toClient.println("<p><input type='text' placeholder='City name' name='city' required></p>");
        toClient.println("<p><input type='password' placeholder='Password' name='password' required id='password'>");
        toClient.println("<button onclick='unhidePassword()'>Show Password</button></p>");
        toClient.println("<p><input type='password' placeholder='Repeat password' name='password2' required></p>");
        toClient.println("<input type='submit' value='Make an account'>");
        toClient.println("</form>");
        toClient.println("<p>Already have an account? <a href='Login'>Log in</a></p>");
      
        toClient.println("</div>");

        toClient.println("<script type='text/javascript'>");
        toClient.println("function unhidePassword() {");
        toClient.println("var passwordInput = document.getElementById('password');");
        toClient.println("if (passwordInput.type === 'password' ){");
        toClient.println("passwordInput.type = 'text'");
        toClient.println("} else {");
        toClient.println("passwordInput.type = 'password'");
        toClient.println("}");
        toClient.println("}");
        toClient.println("</script>");
        toClient.println(Utils.footer());
        toClient.close();

    }
}