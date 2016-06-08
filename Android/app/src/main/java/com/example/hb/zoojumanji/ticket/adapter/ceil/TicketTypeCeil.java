package com.example.hb.zoojumanji.ticket.adapter.ceil;

import com.example.hb.zoojumanji.ticket.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hb on 07/06/2016.
 */
public class TicketTypeCeil {

    protected int type;
    protected List<Ticket> tickets = new ArrayList<>();

    public int getType() {
        return type;
    }

    public TicketTypeCeil setType(int type) {
        this.type = type;
        return this;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public TicketTypeCeil addTicket(Ticket ticket) {
        if (!tickets.contains(ticket)) {
            tickets.add(ticket);
        }
        return this;
    }

    public TicketTypeCeil removeTicket(Ticket ticket) {
        if (tickets.contains(ticket)) {
            tickets.remove(ticket);
        }
        return this;
    }

    public TicketTypeCeil(int ticketType) {
        setType(ticketType);
    }

    public int getTicketsCount() {
        int count = 0;

        for (Ticket ticket : tickets) {
            count += ticket.getQuantity();
        }

        return count;
    }
}
