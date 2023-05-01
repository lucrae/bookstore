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
        String idStr = req.getParameter("ID");
        System.out.println(req.getParameter("ID"));
        UserData user = new UserData(
                    req.getParameter("ID"),
                    req.getParameter("email"),
                    req.getParameter("first_name"),
                    req.getParameter("last_name"),
                    req.getParameter("street"),
                    req.getParameter("postal_code"),
                    req.getParameter("city"),
                    req.getParameter("password"),
                    req.getParameter("is_admin")
                );
        int n = UserData.updateUser(connection, user);
        res.sendRedirect("AdminUserEdit?id=" + idStr + "&a=" + "Updated");
    }
}