package Modules.ParkingSlot;

import Modules.ParkingSlot.Utils.ParkingSlotUtils;
import Modules.ParkingSlot.database.ParkingSlotQueryBuilder;
import Modules.ParkingSlot.model.ParkingSlot;
import Utils.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ParkingSlotUtilsTest {

    @InjectMocks
    ParkingSlotUtils parkingSlotUtils;

    @Mock
    ParkingSlotQueryBuilder parkingSlotQueryBuilder;


    @Test
    public void testFindAllParkingSlots(){
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(parkingSlotQueryBuilder.FindAllParkingSlotsQueryBuilder()).thenReturn("Select * from ParkingSlot");
        Constants.stmt = Mockito.mock(Statement.class);

        Assertions.assertDoesNotThrow(()->{
            Mockito.when(Constants.stmt.executeQuery(Mockito.anyString())).thenReturn(rs);
            parkingSlotUtils.FindAllParkingSlots();
        });

    }

    @Test
    public void testViewParkingslots(){
        ArrayList<ParkingSlot> ps = new ArrayList<>();
        ParkingSlot obj = Mockito.mock(ParkingSlot.class);
        obj.address = "abc";
        ps.add(obj);
        ParkingSlotUtils.viewParkingSlots(ps);
    }


    @Test
    public void testCalculateDistanceInMetres(){
        double expected = 11119.507973459089;
        Assertions.assertEquals(expected, ParkingSlotUtils.calculateDistanceInMeters(-63,63,-63.10,63));
    }

    @Test
    public void testCalculateDistanceNegativeCase(){
        double expected = 1111;
        Assertions.assertNotEquals(expected, ParkingSlotUtils.calculateDistanceInMeters(-63,63,-63.10,63));
    }
}
