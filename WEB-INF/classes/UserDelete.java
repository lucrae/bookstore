import java.io.*;
import java.util.*;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class UserDelete extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        Integer id = Integer.parseInt(req.getParameter("userId"));

        int n = UserData.deleteUser(connection, id);

        res.sendRedirect("AdminUsers");
    }
}