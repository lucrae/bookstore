import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
@SuppressWarnings("serial")
public class AdminUserUpdate extends HttpServlet {
    Connection connection;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        String idStr = req.getParameter("ID");
        System.out.println(req.getParameter("ID"));
        Integer admin = 0;
        if (req.getParameter("is_admin") == null) {
            admin = 0;
        } else{
            admin = 1;
        }
        Integer id = Integer.parseInt(req.getParameter("ID"));
        Integer postalCode = Integer.parseInt(req.getParameter("postal_code"));

        UserData user = new UserData(
                    id,
                    req.getParameter("email"),
                    req.getParameter("first_name"),
                    req.getParameter("last_name"),
                    req.getParameter("street"),
                    postalCode,
                    req.getParameter("city"),
                    req.getParameter("password"),
                    admin
                );
        int n = UserData.updateUser(connection, user);
        res.sendRedirect("AdminUserEdit?id=" + idStr + "&a=" + "Updated");
    }
}