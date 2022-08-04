package Modules.Analytics;

import Modules.Analytics.controller.AnalyticsController;
import Modules.Analytics.model.AnalyticsData;
import Utils.Constants;
import com.opencsv.CSVWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AnalyticsTest {

    @InjectMocks
    AnalyticsController analyticsController;

    @Test
    public void createAnalyticsTest() throws SQLException {
        ArrayList<AnalyticsData> ar = new ArrayList<>();
        Constants.stmt = Mockito.mock(Statement.class);
        Constants.conn = Mockito.mock(Connection.class);
        ResultSet rs = Mockito.mock(ResultSet.class);

        Statement d_st = Mockito.mock(Statement.class);
        ResultSet rs1 = Mockito.mock(ResultSet.class);

        Mockito.when(Constants.stmt.executeQuery(Mockito.anyString())).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
        Mockito.when(rs.getString(Mockito.anyString())).thenReturn("test");
        Mockito.when(rs.getDouble(Mockito.anyString())).thenReturn(63.0);
        Mockito.when(Constants.conn.createStatement()).thenReturn(d_st);
        Mockito.when(d_st.executeQuery(Mockito.anyString())).thenReturn(rs1);

        Mockito.when(rs1.next()).thenReturn(true).thenReturn(false);
        Mockito.when(rs1.getTime(Mockito.anyString())).thenReturn(new Time(12,30,00));
        //Mockito.when(analyticsController.getAnalytics()).thenReturn(ar);


        Assertions.assertDoesNotThrow(()->
        {
            analyticsController.createAnalyticsInCSVFormat(".");
        });
    }
}
