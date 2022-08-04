package Modules.ParkingSlot;

import Modules.ParkingSlot.Utils.ParkingSlotUtils;
import Modules.ParkingSlot.controller.AddParkingSlot;
import Modules.ParkingSlot.controller.DeleteParkingSlot;
import Modules.ParkingSlot.controller.FindParkingSlots;
import Modules.ParkingSlot.database.ParkingSlotQueryBuilder;
import Modules.ParkingSlot.model.ParkingSlot;
import Utils.Constants;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

@DisplayName("ParkingSlot class test suite")

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ParkingSlotTest {


    @InjectMocks
    FindParkingSlots findParkingSlots;

    @Mock
    ParkingSlotUtils parkingSlotUtils;

    @Mock
    ParkingSlotQueryBuilder parkingSlotQueryBuilder;

    @Mock Statement statement;


    ParkingSlot parkingSlot;

    @Test
    public void testParkingSlotConstructor(){
        ParkingSlot myPS = new ParkingSlot(2,50,"6225 University Ave, Halifax, NS B3H 4R2",
                1,44.6374007, -63.5933855,52,1,
                2,new Time(11), new Time(23) );

        Assertions.assertEquals(myPS.hourly_rate,52);
    }

    @Test
    public void testAddParkingSlotPositiveCase(){
        parkingSlot = Mockito.mock(ParkingSlot.class);
        ParkingSlotQueryBuilder parkingSlotQueryBuilder = Mockito.mock(ParkingSlotQueryBuilder.class);
        AddParkingSlot addParkingSlot = new AddParkingSlot(parkingSlotQueryBuilder);
        Constants.stmt = Mockito.mock(Statement.class);
        Assertions.assertDoesNotThrow(()->{
            Mockito.when(Constants.stmt.execute("test")).thenReturn(true);
            addParkingSlot.AddParkingSlots(parkingSlot);
        });
    }

    @Test
    public void testAddParkingSlotNegetiveCase(){
        parkingSlot = Mockito.mock(ParkingSlot.class);
        ParkingSlotQueryBuilder parkingSlotQueryBuilder = Mockito.mock(ParkingSlotQueryBuilder.class);
        AddParkingSlot addParkingSlot = new AddParkingSlot(parkingSlotQueryBuilder);
        Constants.stmt = Mockito.mock(Statement.class);
        Assertions.assertDoesNotThrow(()->{
            Mockito.when(Constants.stmt.execute("negetive test")).thenReturn(false);
            Assertions.assertFalse(addParkingSlot.AddParkingSlots(parkingSlot));
        });
    }

    @Test
    public void testDeleteParkingSlot(){
        ParkingSlotQueryBuilder parkingSlotQueryBuilder = Mockito.mock(ParkingSlotQueryBuilder.class);
        DeleteParkingSlot deleteParkingSlot = new DeleteParkingSlot(parkingSlotQueryBuilder);
        Constants.stmt = Mockito.mock(Statement.class);
        Assertions.assertDoesNotThrow(()->{
            Mockito.when(Constants.stmt.execute("delete")).thenReturn(true);
            deleteParkingSlot.deleteParkingSlot(Mockito.anyInt(), Mockito.anyInt());
        });
    }

    @Test
    public void testFindNearbyParkingSlot(){
        ArrayList<ParkingSlot> arrayList =  new ArrayList<>();
        Assertions.assertDoesNotThrow(()->{
            Mockito.when(parkingSlotUtils.FindAllParkingSlots()).thenReturn(arrayList);
            //Mockito.when(Mockito.mock(ParkingSlotUtils.class).calculateDistanceInMeters(-2,-2,-2,-2)).thenReturn(20000.0);
            findParkingSlots.findNearbyParkingSlots(-63, 64);
        });

    }

    @Test
    public void testFindAvailableParkingSlots(){
        ArrayList<ParkingSlot> arrayList =  new ArrayList<>();
        Constants.stmt = Mockito.mock(Statement.class);
        ResultSet rs = Mockito.mock(ResultSet.class);
        Date date = new Date(20220412);

        Assertions.assertDoesNotThrow(()->{
            Mockito.when(Constants.stmt.executeQuery(Mockito.anyString())).thenReturn(rs);
            Mockito.when(findParkingSlots.findNearbyParkingSlots(-63,-63)).thenReturn(arrayList);
            findParkingSlots.findAvailableParkingSlots(-63.0,-63.0, date, LocalTime.of(2,40),LocalTime.of(3,49), true);
        });
    }

    @Test
    public void testFilterRate(){
        ArrayList<ParkingSlot> arrayList =  new ArrayList<>();
        ParkingSlot parkingSlot = Mockito.mock(ParkingSlot.class);
        arrayList.add(parkingSlot);
        Assertions.assertDoesNotThrow(()->{
            findParkingSlots.sortAccordingToRate(arrayList);
        });
    }

}
