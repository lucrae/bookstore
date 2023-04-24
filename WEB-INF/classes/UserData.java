import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
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

    public static UserData getUser(Connection connection, String idStr) {
        String sql = "Select * FROM Accounts";
        sql += " WHERE ID=?";
        System.out.println("getUser: " + sql);
        UserData user = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, idStr);
            ResultSet result = pstmt.executeQuery();
            if(result.next()) {
                user = new UserData(
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
            }
            result.close();
            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getUser: " + sql + " Exception: " + e);
        }
        return user;
    }

    public static int updateUser(Connection connection, UserData user) {
        String sql ="UPDATE Accounts "
            + "SET email = ?, first_name = ?, last_name = ?, street = ?, postal_code = ?, city = ?, password = ?, is_admin = ?"
            + " WHERE ID = ?";
        System.out.println("updateUser: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setString(1,user.email);
            stmtUpdate.setString(2,user.first_name);
            stmtUpdate.setString(3,user.last_name);
            stmtUpdate.setString(4,user.street);
            stmtUpdate.setInt(5,user.postal_code);
            stmtUpdate.setString(6,user.city);
            stmtUpdate.setString(7,user.password);
            stmtUpdate.setBoolean(8,user.is_admin);
            stmtUpdate.setInt(9,user.ID);
            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in updateUser: " + sql + " Exception: " + e);
        }
        return n;
    }

    public static int registerUser(Connection connection, UserData user) {
        String sql ="INSERT INTO Accounts (ID, email, first_name, last_name, street, postal_code, city, password, is_admin)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println("registerUser: " + sql);
        int n = 0;
        try {
           PreparedStatement stmtUpdate= connection.prepareStatement(sql);
           stmtUpdate.setInt(1,user.ID);
            stmtUpdate.setString(2,user.email);
            stmtUpdate.setString(3,user.first_name);
            stmtUpdate.setString(4,user.last_name);
            stmtUpdate.setString(5,user.street);
            stmtUpdate.setInt(6,user.postal_code);
            stmtUpdate.setString(7,user.city);
            stmtUpdate.setString(8,user.password);
            stmtUpdate.setBoolean(9,user.is_admin);
            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in registerUser: " + sql + " Exception: " + e);
        }
        return n;
    }

    public static Boolean loginUser(Connection connection, String email, String password){

        UserData myUser = getUserByEmail(connection, email);
        
        if (myUser != null && myUser.password.equals(password)){
            return true;
        }else{
            return false;
        }

    }

    public static UserData getUserByEmail(Connection connection, String email) {
        String sql = "Select * FROM Accounts";
        sql += " WHERE email=?";
        System.out.println("getUserByEmail: " + sql);
        UserData user = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet result = pstmt.executeQuery();
            if(result.next()) {
                user = new UserData(
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
            }
            result.close();
            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getUserByEmail: " + sql + " Exception: " + e);
        }
        return user;
    }
}