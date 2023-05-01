public class Utils {
    public static String header(String title) {
        StringBuilder str = new StringBuilder();
        str.append("<!DOCTYPE HTML>");
        str.append("<html>");
        str.append("<head><title>" + title + "</title>");
        str.append("<link rel='stylesheet' href='style.css'>");
        str.append("<script src='getImage.js'></script>");
        str.append("</head>");
        str.append("<body>");
        str.append("<div class='frame'>");
        return str.toString();
    }

    public static String footer() {
        StringBuilder str = new StringBuilder();
        str.append("</div>");
        str.append("</body>");
        str.append("</html>");
        return str.toString();
    }
}