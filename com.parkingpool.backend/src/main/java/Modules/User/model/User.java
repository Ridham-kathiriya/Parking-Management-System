package Modules.User.model;

public class User {
    public int user_id;
    public String name;
    public String email;
    public USER_TYPE role;
    public String address;
    private String password;

    public User(String name, String email, USER_TYPE role, String address, String password, int user_id) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.address = address;
        this.password = password;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public USER_TYPE getRole() {
        return role;
    }

    public void setRole(USER_TYPE role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
