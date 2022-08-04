
package Modules.ParkingSlot;

import Modules.ParkingSlot.database.ParkingSlotQueryBuilder;
import org.junit.jupiter.api.*;

import java.sql.Time;

@DisplayName("ParkingSlotQueryBuilder class test suite")
public class ParkingSlotQueryBuilderTest {
    @Test
    @DisplayName("AddParkingSlotQueryBuilder Test")
    public void testAddParkingSlotQueryBuilder(){
        final int distance_from_elevator = 0;
        final String address = "6225 University Ave, Halifax, NS B3H 4R2";
        final int is_handicap = 1;
        final double longitude = 44.6374007;
        final double latitude = -63.5933855;
        final double hourly_rate = 12.95;
        final int is_on_street = 0;
        final int owner_user_id = 6;
        Time start_time = new Time(8, 0, 0);
        Time end_time = new Time(20, 0, 0);

        final ParkingSlotQueryBuilder parkingSlotQueryBuilder = ParkingSlotQueryBuilder.getInstance();
        final String actualQuery = parkingSlotQueryBuilder.AddParkingSlotQueryBuilder(distance_from_elevator, address, is_handicap, longitude, latitude, hourly_rate, is_on_street, owner_user_id, start_time, end_time);
        final String expectedQuery = "INSERT INTO ParkingSlot (distance_from_elevator, address, is_handicap, longitude, latitude, hourly_rate, is_on_street, owner_user_id, start_time, end_time) VALUES('0','6225 University Ave, Halifax, NS B3H 4R2','1','44.6374007','-63.5933855','12.95','0','6','08:00:00','20:00:00')";
        Assertions.assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void testFindallSlotsqueryBuilder(){
        final ParkingSlotQueryBuilder parkingSlotQueryBuilder = ParkingSlotQueryBuilder.getInstance();
        final String actualQuery = parkingSlotQueryBuilder.FindAllParkingSlotsQueryBuilder();
        final String expectedquery = "SELECT * from ParkingSlot";
        Assertions.assertEquals(expectedquery, actualQuery);
    }

    @Test
    public void testDeleteParkingSlot(){
        final ParkingSlotQueryBuilder parkingSlotQueryBuilder = ParkingSlotQueryBuilder.getInstance();
        final String actualQuery = parkingSlotQueryBuilder.DeleteParkingSlotQueryBuilder(2,6);
        final String expectedQuery = "DELETE FROM ParkingSlot WHERE id=2 AND owner_user_id=6";
        Assertions.assertEquals(actualQuery,expectedQuery);
    }

}


