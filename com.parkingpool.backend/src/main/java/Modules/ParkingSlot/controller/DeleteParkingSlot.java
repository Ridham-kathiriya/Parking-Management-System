package Modules.ParkingSlot.controller;

import Modules.ParkingSlot.database.ParkingSlotQueryBuilderDAO;
import Utils.Constants;

import java.sql.SQLException;
import java.sql.Statement;

public class DeleteParkingSlot {
    private final ParkingSlotQueryBuilderDAO parkingSlotQueryBuilderDAO;

    public DeleteParkingSlot(ParkingSlotQueryBuilderDAO parkingSlotQueryBuilderDAO) {
        this.parkingSlotQueryBuilderDAO = parkingSlotQueryBuilderDAO;
    }

    public void deleteParkingSlot(int parkingSlotId, int userId) throws SQLException {
        String deleteParkingSlotQuery = parkingSlotQueryBuilderDAO.DeleteParkingSlotQueryBuilder(parkingSlotId, userId);
        Constants.stmt.execute(deleteParkingSlotQuery);
        Constants.printAndSpeak("The Parking Slot has been deleted successfully!\n");
    }
}
