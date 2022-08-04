package Modules.User.controller;

import Modules.User.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class LogInTest {

    public Statement st;
    public ResultSet rs;
    public LogIn si;
    public User u;

    @BeforeEach
    void setUp() throws Exception{
        st = Mockito.mock(Statement.class);
        rs = Mockito.mock(ResultSet.class);
        u = Mockito.mock(User.class);
        si = new LogIn();
    }

    @Test
    void isCredentialCorrectTest() throws SQLException {

        Mockito.when(st.executeQuery(Mockito.anyString())).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(false);
        Mockito.when(rs.getString(Mockito.anyString())).thenReturn("abc");
        Assertions.assertFalse(si.isCredentialCorrect(st,"abc@gmail.com",'v',"abc"));
    }

    @Test
    void isCredentialCorrectExceptionTest() throws SQLException {

        assertDoesNotThrow(() -> {
            Mockito.when(st.executeQuery(Mockito.anyString())).thenReturn(rs);
            Mockito.when(rs.next()).thenReturn(false);
            Mockito.when(rs.getString(Mockito.anyString())).thenReturn("abc");
            si.isCredentialCorrect(st,"abc@gmail.com",'v',"abc");
        });



    }
}