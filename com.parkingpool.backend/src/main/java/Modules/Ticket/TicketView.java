package Modules.Ticket;

import Modules.Ticket.controller.CreateTicket;
import Modules.Ticket.controller.GetTicket;
import Modules.Ticket.controller.UpdateTicketStatus;
import Modules.Ticket.model.Ticket;
import Modules.User.Utils.UserUtils;
import Utils.Constants;
import Utils.Scan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class TicketView {
    // ------ PUBLIC METHODS ------
    public void displayAdminTicketMenu() throws SQLException {
        Constants.printAndSpeak("\n     Welcome to the Ticket Menu");
        Constants.printAndSpeak("\nPlease select an option:\n1. View Tickets\n2. Update Ticket Status\n3. Back");
        boolean toContinue = true;
        while(toContinue) {

            switch (Integer.parseInt(Scan.nextLine())) {
                case 1:
                    viewTickets();
                    toContinue = true;
                    break;
                case 2:
                    updateTicketStatus();
                    toContinue = true;
                    break;
                case 3:
                    toContinue = false;
                    break;
            }
        }
    }

    public void createTicket(){
        Constants.printAndSpeak("\nPlease enter the issue you wish to report: ");
        String issue = Scan.nextLine();

        CreateTicket createTicket = new CreateTicket();
        createTicket.createTicket(issue);

        Constants.printAndSpeak("\nTicket has been raised. Please wait for an admin to respond.");
    }


    // ------ PRIVATE METHODS ------
    private void viewTickets() throws SQLException {
        GetTicket getTicket = new GetTicket();
        Map<String, ArrayList<Ticket>> tickets = getTicket.getAllTickets();
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("|                                Open Tickets                           |");
        System.out.println("-------------------------------------------------------------------------");
        UserUtils userUtils = new UserUtils();
        for(Ticket ticket : tickets.get("open")) {
            System.out.println("Ticket ID: " + ticket.ticket_id);
            System.out.println("Issue: " + ticket.issue);
            System.out.println("Opened By: " + userUtils.getUserById(ticket.userID).email);
        }

        System.out.println("\n");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("|                               Closed Tickets                          |");
        System.out.println("-------------------------------------------------------------------------");
        for(Ticket ticket : tickets.get("closed")) {
            System.out.println("Ticket ID: " + ticket.ticket_id);
            System.out.println("Issue: " + ticket.issue);
            System.out.println("Opened By: " + userUtils.getUserById(ticket.userID).email);
        }
    }

    private void updateTicketStatus() {
        Constants.printAndSpeak("\nPlease enter the ticket ID you wish to update: ");
        UpdateTicketStatus updateTicketStatus = new UpdateTicketStatus();
        updateTicketStatus.updateTicketStatus(Integer.parseInt(Scan.nextLine()));
    }
}
