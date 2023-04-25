import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class CheckLogin extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
    

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserData myUser = UserData.getUserByEmail(connection, email);

        if(myUser != null && UserData.loginUser(connection, email, password) == true) {
            Integer userId = myUser.ID;
            res.sendRedirect("Main?userId=" + userId);

        }else{
            res.sendRedirect("Login");

        }


     
    }
}