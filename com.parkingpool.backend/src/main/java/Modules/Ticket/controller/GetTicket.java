package Modules.Ticket.controller;

import Modules.Ticket.model.TICKET_STATUS;
import Modules.Ticket.model.Ticket;
import Utils.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetTicket {
    Statement stmt = Constants.stmt;
    public Map<String, ArrayList<Ticket>> getAllTickets() throws SQLException {
        String getTicketsQuery = "SELECT * FROM Ticket;";
        ArrayList<Ticket> openTickets = new ArrayList<>();
        ArrayList<Ticket> closedTickets = new ArrayList<>();

        ResultSet resultSet = stmt.executeQuery(getTicketsQuery);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int userId = resultSet.getInt("user_id");
            String issue = resultSet.getString("issue");
            String status = resultSet.getString("status");

            if(status.equals("OPEN")) {
                openTickets.add(new Ticket(id, userId, issue, TICKET_STATUS.valueOf(status)));
            } else {
                closedTickets.add(new Ticket(id, userId, issue, TICKET_STATUS.valueOf(status)));
            }
        }

        Map<String, ArrayList<Ticket>> tickets = new HashMap<String, ArrayList<Ticket>>();
        tickets.put("open", openTickets);
        tickets.put("closed", closedTickets);
        return tickets;
    }
}
