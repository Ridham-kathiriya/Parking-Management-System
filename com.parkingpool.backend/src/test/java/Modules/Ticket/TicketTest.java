package Modules.Ticket;

import Modules.Ticket.controller.CreateTicket;
import Modules.Ticket.controller.UpdateTicketStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;


import java.sql.Connection;
import static org.mockito.Mockito.mock;

public class TicketTest {

    @InjectMocks
    CreateTicket createTicket = mock(CreateTicket.class);
    UpdateTicketStatus updateTicketStatus = mock(UpdateTicketStatus.class);

    @Test
    void testCreateTicket()
    {
        Assertions.assertDoesNotThrow(()->{
            Connection connection = mock(Connection.class);
            createTicket.createTicket("slow app");
        });
    }

    @Test
    void testUpdateTicketStatus()
    {
        Assertions.assertDoesNotThrow(()->{
            Connection connection = mock(Connection.class);
            updateTicketStatus.updateTicketStatus(45);
        });
    }
}
