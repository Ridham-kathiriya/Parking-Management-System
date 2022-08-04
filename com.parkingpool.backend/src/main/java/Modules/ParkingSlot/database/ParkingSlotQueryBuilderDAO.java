package Modules.ParkingSlot.database;

import java.sql.Time;

public interface ParkingSlotQueryBuilderDAO {
    String AddParkingSlotQueryBuilder(int distance_from_elevator,
                                      String address,
                                      int is_handicap,
                                      double longitude,
                                      double latitude,
                                      double hourly_rate,
                                      int is_on_street,
                                      int owner_user_id, Time start_time, Time end_time);

    String FindAllParkingSlotsQueryBuilder();
    String DeleteParkingSlotQueryBuilder(int parkingSlotId, int userId);
    String FindParkingSlotByIdQueryBuilder(int parkingSlotId);
}

