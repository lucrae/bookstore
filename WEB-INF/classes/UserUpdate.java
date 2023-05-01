import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class UserUpdate extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        String idStr = req.getParameter("userId");
        UserData userOld = UserData.getUser(connection, idStr);
        Integer id = userOld.ID;
        Integer admin = userOld.is_admin;

        UserData user = new UserData(
                    id,
                    req.getParameter("email"),
                    req.getParameter("first_name"),
                    req.getParameter("last_name"),
                    req.getParameter("street"),
                    Integer.parseInt(req.getParameter("postal_code")),
                    req.getParameter("city"),
                    req.getParameter("password"),
                    admin
                );
        int n = UserData.updateUser(connection, user);
        res.sendRedirect("Account?userId="+user.ID);
    }
}