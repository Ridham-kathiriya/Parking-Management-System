package Modules.User.controller;
import Modules.User.Utils.Hash;

import java.sql.*;

public class SignUp{


    public boolean checkIsUserExist(Statement stmt,String email,char rl) throws SQLException {

        String role="";
        if(rl=='v')role="vendor";
        if(rl=='c')role="customer";
        if(rl=='a')role="admin";


        String query = "select * from user where email=\""+email.trim()+"\" and role=\""+role+"\";";

        ResultSet rs = stmt.executeQuery(query);

        if(rs.next()){
            return true;
        }

        return false;



    }

    public int register(Statement stmt,String email,char rl,String name,String password,String address) throws SQLException
    {
        String role="";
        if(rl=='v')role="vendor";
        if(rl=='c')role="customer";
        if(rl=='a')role="admin";

        password = Hash.getHashCode(password);

        String query = "INSERT INTO `user` (`email`, `role`, `name`, `password`, `address`) VALUES ('"+email.trim()+"', '"+role.trim()+"', '"+name.trim()+"', '"+password.trim()+"', '"+address.trim()+"');";

        int rowAffected = stmt.executeUpdate(query);


        return rowAffected;
    }



}
