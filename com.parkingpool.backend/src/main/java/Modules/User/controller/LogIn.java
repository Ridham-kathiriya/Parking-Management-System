package Modules.User.controller;


import Modules.User.Utils.Hash;
import Modules.User.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogIn{

    public User user;

    public boolean isCredentialCorrect(Statement stmt, String email, char rl, String password) throws SQLException {

        String role="";
        USER_TYPE ut=null;
        if(rl=='v'){role="vendor";ut=USER_TYPE.VENDOR;}
        if(rl=='c'){role="customer";ut=USER_TYPE.CUSTOMER;}
        if(rl=='a'){role="admin";ut=USER_TYPE.ADMIN;}

        String pass = password;
        password = Hash.getHashCode(password);

        String query = "select * from user where password=\""+password.trim()+"\" and email=\""+email.trim()+"\" and role=\""+role+"\";";

        ResultSet rs = stmt.executeQuery(query);

        if(rs.next()){


            user = new User(rs.getString("name"),rs.getString("email"),ut,rs.getString("address"),pass, rs.getInt("user_id"));

            return true;
        }

        return false;
    }
}