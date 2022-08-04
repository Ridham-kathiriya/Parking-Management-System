package Modules.User.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SignUpTest {

    public Statement st;
    public ResultSet rs;
    public SignUp su;

    @Before
    public void setUp() throws Exception {
        st = Mockito.mock(Statement.class);
        rs = Mockito.mock(ResultSet.class);
        su = new SignUp();
    }

    @Test
    public void checkIsUserExistExceptionTest() {
        assertDoesNotThrow(() -> {
            Mockito.when(st.executeQuery(Mockito.anyString())).thenReturn(rs);
            Mockito.when(rs.next()).thenReturn(true);
            su.checkIsUserExist(st,"abc@gmal.com",'v');
        });
    }

    @Test
    public void checkIsUserExistTest() throws SQLException {

        Mockito.when(st.executeQuery(Mockito.anyString())).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true);
        Assertions.assertTrue(su.checkIsUserExist(st,"abc@gmal.com",'v'));

    }


    @Test
    public void registerTest() throws SQLException {

        Mockito.when(st.executeUpdate(Mockito.anyString())).thenReturn(1);
        Assertions.assertEquals(1,su.register(st,"abc@gmail.com",'c',"abc","abc","xyz"));
    }

    @Test
    public void registerExceptionTest() {
        assertDoesNotThrow(() -> {
            Mockito.when(st.executeUpdate(Mockito.anyString())).thenReturn(1);
            su.register(st,"abc@gmail.com",'c',"abc","abc","xyz");
        });
    }


}
