package Modules.User.Utils;

import Modules.User.model.USER_TYPE;
import Modules.User.model.User;
import Utils.Constants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserUtils {
    Connection con = Constants.conn;

    public User getUserById(int userID) throws SQLException {
        Statement stmt = con.createStatement();
        String query = "SELECT * from user where user_id=" + userID + ";";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int user_id = rs.getInt("user_id");
        System.out.println(user_id);
        String email = rs.getString("email");
        String name = rs.getString("name");
        USER_TYPE role = USER_TYPE.valueOf((rs.getString("role").toUpperCase()));
        String password = rs.getString("password");
        String address = rs.getString("address");
        return new User(name, email, role, address, password, user_id);
    }
}
