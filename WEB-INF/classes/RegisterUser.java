import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class RegisterUser extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
    

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");
        String first_name = req.getParameter("first_name");
        String street = req.getParameter("street");
        Integer postal_code = Integer.parseInt(req.getParameter("postal_code"));
        String city = req.getParameter("city");
        String last_name = req.getParameter("last_name");

        UserData myUser = UserData.getUserByEmail(connection, email);

        if(myUser == null && password.equals(password2)) {
            Vector<UserData> userList = UserData.getUserList(connection);
            int newId = userList.size() + 1;
            UserData newUser = new UserData(newId, email, first_name, last_name, street, postal_code, city, password, 0);
            int nAffected = UserData.registerUser(connection, newUser);
            res.sendRedirect("Main?userId=" + newId);

        }else{
            res.sendRedirect("Register");
    

        }


     
    }
}