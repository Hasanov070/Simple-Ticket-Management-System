package az.example.tickets.exception;

public class DuplicateTicketException extends RuntimeException {
    public DuplicateTicketException(Long id) {
        super("Bu Biletlə yaranmış identifikasiya nömrəli " + id + " bilet sistemdə mövcuddur. Zəhmət olmasa yenidən yoxlayın!");
    }
}
/*Burada bele bir sual yarana bilerki biz niye custom exceptionlar
* istifade edirik. Meselen elimizde runtimeExeception varki nese xeta olanda triggerlesin.
* Mesele burasindadadir ki, programin bashqa hissesinde RunTimeException tutalanda onun
* konkret olaraq Ticketin tapilmamasi sebebinden yarandigi bilinmir. Ona gore de en yaxshi usul
* custom exceptionlardan istifade etmekdir */
