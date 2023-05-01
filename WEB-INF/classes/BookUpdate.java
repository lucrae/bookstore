import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class BookUpdate extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        String idStr = req.getParameter("ID");

        PrintWriter toClient = res.getWriter();
        toClient.println(req.getParameter("cover"));

        BookData book = new BookData(
                    Integer.parseInt(req.getParameter("ID")),
                    req.getParameter("title"),
                    req.getParameter("author"),
                    Integer.parseInt(req.getParameter("publish_year")),
                    req.getParameter("blurb"),
                    req.getParameter("genre"),
                    req.getParameter("cover_image"),
                    Float.parseFloat(req.getParameter("price")),
                    Integer.parseInt(req.getParameter("stock"))
                );
        int n = BookData.updateBook(connection, book);
        res.sendRedirect("AdminBookEdit?id=" + idStr + "&a=" + "Updated");
    }
}