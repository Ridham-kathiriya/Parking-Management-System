package Modules.Ticket.model;

public class Ticket {
    public int userID;
    public int ticket_id;
    public String issue;
    public TICKET_STATUS status;

    public Ticket(int ticket_id, int userID, String issue, TICKET_STATUS status) {
        this.ticket_id = ticket_id;
        this.userID = userID;
        this.issue = issue;
        this.status = status;
    }
}