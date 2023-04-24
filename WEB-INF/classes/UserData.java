import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserData {
    Integer ID;
    String email;
    String first_name;
    String last_name;
    String street;
    Integer postal_code;
    String city;
    String password;
    Boolean is_admin;
    
    UserData(Integer ID, String email, String first_name, String last_name, String street, Integer postal_code, String city, String password, Boolean is_admin) {
        this.ID = ID;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.street = street;
        this.postal_code = postal_code;
        this.city = city;
        this.password = password;
        this.is_admin = is_admin;
    }

    public static Vector<UserData> getUserList(Connection connection) {
        Vector<UserData> vec = new Vector<UserData>();
        String sql = "Select * FROM Accounts";
        System.out.println("getUserList: " + sql);
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                UserData user = new UserData(
                    Integer.parseInt(result.getString("ID")),
                    result.getString("email"),
                    result.getString("first_name"),
                    result.getString("last_name"),
                    result.getString("street"),
                    Integer.parseInt(result.getString("postal_code")),
                    result.getString("city"),
                    result.getString("password"),
                    Boolean.parseBoolean(result.getString("is_admin"))
                );
                vec.addElement(user);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getUserList: " + sql + " Exception: " + e);
        }
        return vec;
    }
}