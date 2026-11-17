package az.example.tickets.entity;

import az.example.tickets.enums.TicketPriority;
import az.example.tickets.enums.TicketStatus;

import java.time.LocalDateTime;

public final class Ticket {


    private final Long id; // Biletin IDsi
    /*Sual yarana bilerki neye gore (private final) istifade eledik chunki
    Mentiqe esasen bilete ID verildikden sonra deyishmemek lazimdir. Private final bunu edir.*/
    private String title; // Biletin bashligi

    private String description; // Bilet haqqda melumat

    private TicketStatus ticketStatus; // Biletin Statusu

    private TicketPriority ticketPriority; //Biletin vacibliyi

    private final LocalDateTime createdAt; // LocalDateTime java-da tarix ve saati saxlamaq uhcun
    // Istifade olunur. Burdada eyni qayda ile ticketin zamani yaradildiqdan sonra onu deyishmek olmaz

    //private olmasi sayesinde bashqa classlar bu fieldleri bir basha deyishe bilmez

    // Burada Null exceptionlari qeyd edirik ki bosh qalmasinlar null value olanda error vere bilsin


// Constructor

    public Ticket(Long id, String title, String description, TicketPriority ticketPriority){
        this.id = id; // istifadechi terefinden melumat verilir
        this.title = title; // istifadechi terefinden melumat verilir
        this.description = description; // istifadechi terefinden melumat verilir
        this.ticketPriority = ticketPriority; // istifadechi terefinden melumat verilir
        this.ticketStatus = TicketStatus.OPEN; //Sistem terefinden ilkin melhelede (OPEN) kimi verilir sonradan deyishilecek
        this.createdAt = LocalDateTime.now();



        if (id == null) {
            throw new IllegalArgumentException("Ticket id must not be null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Ticket title must not be empty");
        }
        if (ticketPriority == null) {
            throw new IllegalArgumentException("Ticket priority must not be null");
        }



    }
}
