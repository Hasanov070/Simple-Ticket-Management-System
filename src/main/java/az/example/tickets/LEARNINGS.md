
# Java və OOP: 93 suala cavab — Ticket Management System layihəm üzərindən

> **Dil:** Sadə Azərbaycan dili · **Səviyyə:** Başlanğıc · **Layihə:** Java 21, Maven, konsol tətbiqi
>
> **Vacib qeyd:** Bu dərslik əvvəlki söhbətlərimizdə paylaşdığın `Ticket`, `TicketService`, `TicketMenu`, `TicketStatus` və `TicketPriority` kodlarının məlum hissələrinə əsaslanır. `printTickets(...)` və `readLong(...)` aşağıda sənin paylaşdığın formaya uyğun verilib. Digər bəzi kodlar **öyrədici nümunədir**: layihənin tam faylları bu söhbətdə təqdim edilmədiyi üçün onları sənin faylının sözbəsöz kopyası sayma. Məqsəd məntiqi düzgün başa salmaqdır.

## Başlamazdan əvvəl: layihənin real həyatda qarşılığı

Təsəvvür et, universitetdə **texniki dəstək masası** var. Tələbə deyir: “Wi-Fi işləmir”. İşçi bunun üçün **bilet (ticket)** açır, ID verir, vəziyyətini (`OPEN`) və vacibliyini (`HIGH`) qeyd edir. Daha sonra bu bileti tapmaq, göstərmək, statusunu dəyişmək və silmək olur.

-   **`Ticket`** — bir müraciətin kağız forması: ID, başlıq, təsvir, status, prioritet, vaxt.
-   **`TicketService`** — işçi: kağızları saxlayır, tapır, dəyişir, silir. Burada **iş məntiqi** yerləşir.
-   **`TicketMenu`** — qəbul masası: istifadəçiyə seçim göstərir, yazdığını oxuyur və işi işçiyə ötürür.
-   **`Main`** — səhər ofisi açan şəxs: lazım olan obyektləri qurur və menyunu başladır.
-   **`TicketStatus`** — işin hansı mərhələdə olması: `OPEN`, `IN_PROGRESS`, `CLOSED`.
-   **`TicketPriority`** — vaciblik: `LOW`, `MEDIUM`, `HIGH`.
-   **`LinkedHashMap<Long, Ticket>`** — ID ilə axtarılan, əlavə edilmə ardıcıllığını da saxlayan bilet reyestri.

```
İstifadəçi
   │ 1. Menyuda əməliyyat seçir
   ▼
TicketMenu  ──►  Scanner: istifadəçinin yazdığını oxuyur
   │ 2. TicketService metodunu çağırır
   ▼
TicketService  ──►  LinkedHashMap<Long, Ticket>
   │ 3. Ticket obyektini yaradır/tapır/dəyişir/silir
   ▼
Ticket (id, title, description, status, priority, createdAt)
   │ 4. Nəticə geri qayıdır
   ▼
TicketMenu  ──►  nəticəni konsolda göstərir
```

### İzahları oxuyarkən üç vacib qayda

1.  `Ticket ticket` yazılışında **soldakı `Ticket` tip**, **sağdakı `ticket` dəyişən adıdır**.
2.  `ticket.getStatus()` yazılışında **`ticket` obyektə istinad**, **`getStatus()` həmin obyektin metodudur**.
3.  `ticketService.createTicket(...)` yazılışında **menyu özü bilet saxlamır**; işi xidmət obyektinə tapşırır.

----------

# I. Java Basics — əsas anlayışlar (1–14)

### 1. `class` nədir?

**Cavab:** Class obyekt hazırlamaq üçün **şablondur**. Məsələn, boş bilet forması `Ticket` class-ıdır: hər biletin `id`, `title`, `status` kimi məlumatları olacağını əvvəlcədən müəyyən edir. Class hələ konkret müraciət deyil.

```
public class Ticket {
    private Long id;
    private String title;
}
```

**Yadda saxla:** `Ticket` = biletin modeli; `101` nömrəli konkret bilet = obyekt.

### 2. `object` nədir?

**Cavab:** Class-ın əsasında yaradılmış **konkret nümunədir**. Eyni bilet blankı əsasında “Wi-Fi işləmir” və “Printer işləmir” adlı iki ayrı müraciət açmaq olar. Hərəsinin öz ID-si və vəziyyəti var.

```
// Ticket constructor-unun parametr siyahısına uyğun nümunə:
Ticket ticket = new Ticket(101L, "Wi-Fi işləmir", "2-ci mərtəbə", TicketPriority.HIGH);
```

Burada `new Ticket(...)` yeni obyekt hazırlayır; `ticket` isə ona istinad edir.

### 3. `class` və `object` fərqi nədir?

**Cavab:** **Class reseptdir**, **object həmin reseptlə bişirilən yeməkdir**. `Ticket` class-ı bir dəfə yazılır, amma ondan 100 müxtəlif `Ticket` obyekti yaradıla bilər. Class sahələrin və metodların qaydasını təsvir edir; obyekt həmin sahələrin konkret dəyərlərini daşıyır.

### 4. `field` nədir?

**Cavab:** Obyektin içində saxlanılan **məlumat yeridir**. `private String title;` biletin başlığı üçün yer ayırır. `ticket` obyektinin `title` dəyəri “Wi-Fi işləmir”, başqa obyektinki “Monitor yanmır” ola bilər.

```
private Long id;
private String title;
private TicketStatus ticketStatus;
```

### 5. `method` nədir?

**Cavab:** Obyektin və ya class-ın edə bildiyi **işdir**. `TicketService.createTicket(...)` yeni bilet yaradır; `TicketService.deleteTicket(...)` silir; `Ticket.changeTicketStatus(...)` statusu dəyişir. Field “nəyi bilir?”, method “nə edir?” sualına cavab verir.

### 6. `data type` nədir?

**Cavab:** Dəyişəndə **hansı növ məlumatın ola biləcəyini** göstərir. Biletin adı mətn olduğu üçün `String`, yaradılma vaxtı `LocalDateTime`, prioriteti `TicketPriority`, ID-si `Long` ola bilər.

```
String title = "Wi-Fi problemi";
Long id = 101L;
TicketPriority priority = TicketPriority.HIGH;
```

Tip Java-ya xəbər verir ki, həmin dəyişənlə hansı əməliyyatlar uyğundur.

### 7. `String` nədir?

**Cavab:** Mətn saxlamaq üçün tipdir. Layihəndə `title` və `description` bununla saxlanır. Rəqəm mətn kimi qəbul ediləndə, məsələn `"101"`, hələ riyazi `101` deyil.

```
String title = "Printer işləmir";
String input = "101";  // bu, rəqəm deyil, mətndir
```

### 8. `int`, `long`, `Long` nədir və fərqləri hansılardır?

**Cavab:** `int` daha kiçik tam ədədlər üçün, `long` daha böyük tam ədədlər üçün **primitive** tipdir. `Long` isə `long`-un **obyekt tipli** formasıdır (wrapper). `Long` `null` ola bilər və `List<Long>` kimi generiklərdə istifadə olunur; `long` `null` ola bilməz. `L` sonluğu long literalını bildirir.

```
int menuChoice = 2;
long primitiveId = 101L;
Long ticketId = 101L;
Long unknownId = null; // ola bilər
```

### 9. `private` nə deməkdir?

**Cavab:** “Bu yerə yalnız **öz class-ının içindən** birbaşa daxil ola bilərsən” deməkdir. Biletin statusunu kənardan istənilən şəkildə dəyişmək əvəzinə, `changeTicketStatus(...)` adlı metoddan istifadə etmək daha təhlükəsizdir.

```
private TicketStatus ticketStatus;
```

`TicketMenu` normalda `ticket.ticketStatus = ...` yazmır; müvafiq metodu çağırır.

### 10. `public` nə deməkdir?

**Cavab:** Başqa class-ların da istifadə edə bildiyi üzv deməkdir. `TicketService`-də `public getAllTickets()` olanda `TicketMenu` həmin əməliyyatı çağıra bilir. **Qeyd:** `public` tam təhlükəsizlik təmin etmir; sadəcə Java-da görünmə qaydasını müəyyənləşdirir.

### 11. `final` nə deməkdir?

**Cavab:** Hansı yerdə işlənməsindən asılıdır. `final` dəyişənin təkrar mənimsədilməsini qadağan edir; **obyektin daxilindəki hər şeyin dəyişməsini qadağan etmir**. `final` class miras alınmır, `final` method override edilmir.

```
private final TicketService ticketService;
```

Constructor-da bir dəfə xidmət obyekti verildikdən sonra bu field başqa `TicketService`-ə yönəldilə bilməz. Amma xidmətin içində yeni biletlər yaradıla bilər.

### 12. `private final TicketService ticketService;` sətrində hər söz nədir?

**Cavab:** Bu sətri “menyu class-ının içində bir xidmət obyektinə istinad saxla” kimi oxu:

-   `private` — bu field-i birbaşa yalnız `TicketMenu`-nun içi görür.
-   `final` — bu istinadı constructor-dan sonra başqa xidmətə dəyişmə.
-   `TicketService` — istinad edilən obyektin **tipi**.
-   `ticketService` — field-in **adı**.
-   `;` — Java əmri bitdi.

**Misal:** Qəbul işçisinə bir məsul əməkdaş təyin olunur; qəbul işçisi dəyişmir, amma həmin əməkdaş gün ərzində çoxlu müraciət emal edir.

### 13. `reference` nədir və obyektlər necə əlaqələnir?

**Cavab:** Reference (istinad) Java-da obyektə **müraciət etmək üçün saxlanan dəyərdir**. Real həyatda bu, şəxsin özü yox, onunla əlaqə saxlamaq üçün telefon nömrəsini bilmək kimidir. İstinad obyektin özü deyil.

```
TicketService service = new TicketService();
TicketMenu menu = new TicketMenu(service);
```

`menu` yaradılarkən mövcud `service` ötürülür. Beləcə `TicketMenu` daxilində həmin `TicketService` üzərində metodlar işlədilir. İki istinad eyni obyektə işarə edə bilər.

### 14. `package` və `import` nə üçündür?

**Cavab:** `package` class-ın hansı **qovluq/məntiqi qrupa** aid olduğunu göstərir; `import` isə başqa qrupdakı class-ın adını rahat yazmağa imkan verir. Sənin layihənin məlum quruluşunda, məsələn, `Ticket` `az.example.tickets.entity`, `TicketPriority` `az.example.tickets.enums`, `TicketService` isə `az.example.tickets.service` daxilindədir.

```
package az.example.tickets;

import az.example.tickets.entity.Ticket;
import az.example.tickets.service.TicketService;
import java.util.List;
```

**Unutma:** `import` obyekt yaratmır; yalnız tipin adını tanıdır.

----------

# II. OOP — obyekt yönümlü proqramlaşdırma (15–28)

### 15. `constructor` nədir?

**Cavab:** Constructor obyekt yarananda **ilkin məlumatları yerləşdirmək** üçün çağırılan xüsusi hissədir. Adı class ilə eynidir və return tipi yazılmır. `new Ticket(...)` dedikdə constructor işləyir.

```
public Ticket(Long id, String title, String description, TicketPriority ticketPriority) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.ticketPriority = ticketPriority;
    this.ticketStatus = TicketStatus.OPEN;
    this.createdAt = LocalDateTime.now();
}
```

Bu, layihəndəki məntiqi göstərən **sadələşdirilmiş nümunədir**; sənin constructor-undakı validation şərtləri burada ixtisar olunub.

### 16. Constructor niyə lazımdır?

**Cavab:** Yeni bilet yaradılanda ID-siz və ya başlıqsız qalmasının qarşısını almağa kömək edir. Constructor-a `id`, `title`, `description`, `priority` verirsən; o, biletin ilkin vəziyyətini (`OPEN`) və yaranma vaxtını da təyin edə bilər. Bununla obyekt yaranan kimi istifadəyə hazır olur.

### 17. Constructor və adi method fərqi nədir?

**Cavab:** Constructor `new Ticket(...)` zamanı çağırılır və obyektin qurulmasına xidmət edir. Method isə **sonra**, mövcud obyekt üzərində iş görür. Məsələn, constructor biletə `OPEN` yazır; `changeTicketStatus(CLOSED)` isə günün sonunda vəziyyəti dəyişə bilər.

### 18. `this` nə deməkdir?

**Cavab:** `this` — **hazırda işlədiyimiz obyektin özü** deməkdir. Bilet constructor-u işləyirsə, `this` yaradılan həmin bileti göstərir. Bu, “mənim ID-m”, “mənim başlığım” deməyə bənzəyir.

### 19. `this.id = id;` necə işləyir?

**Cavab:** Sağdakı `id` constructor-a **gələn parametrdir**; soldakı `this.id` isə **obyektin field-idir**. `=` sağdakı dəyəri soldakı yerə yazır. `new Ticket(101L, ...)` çağırılanda obyektin `id` sahəsi `101` olur.

### 20. `this.title = title;` sətrində niyə iki `title` var?

**Cavab:** Adlar eyni olsa da, yerləri fərqlidir:

```
private String title;  // obyektin yaddaşında saxlanan field

public Ticket(String title) { // müvəqqəti constructor parametri
    this.title = title;       // parametri obyektin field-inə köçür
}
```

Soldakı “bu biletin başlığı”, sağdakı “kənardan verilən başlıq” deməkdir.

### 21. `getter` nədir?

**Cavab:** `private` field-in dəyərini **oxumaq** üçün method-dur. Məsələn `getId()` biletin ID-sini, `getStatus()` vəziyyətini qaytarır. Qəbul masasının biletin məlumatını soruşması kimidir.

```
public Long getId() {
    return id;
}
```

### 22. `setter` nədir?

**Cavab:** Field-in dəyərini **dəyişmək** üçün method-dur. Məsələn `setTitle("Yeni başlıq")`. Amma hər field üçün setter lazım deyil: ID və yaradılma tarixi kimi məlumatlar sabit qala bilər. Layihəndə status üçün daha mənalı ad `changeTicketStatus(...)`-dur.

### 23. Getter və setter fərqi nədir?

**Cavab:** Getter **oxuyur** (`ticket.getTitle()`), setter **dəyişir** (`ticket.setTitle("...")`). Getter əsasən məlumat qaytarır; setter adətən `void` olur və parametr qəbul edir. **Qayda:** nəyisə yalnız oxumaq istəyirsənsə setter çağırma.

### 24. `encapsulation` nədir?

**Cavab:** Məlumatı obyektin içində qoruyub onu **nəzarətli metodlarla** açmaq prinsipidir. Real həyatda bank hesabının balansını birbaşa əl ilə dəyişmək yerinə bankın “pul köçür” əməliyyatından istifadə edirsən. Layihədə `ticketStatus` `private` qalır, dəyişiklik `changeTicketStatus(...)` ilə edilir.

### 25. `validation` nədir və constructor-da niyə lazımdır?

**Cavab:** Gələn məlumatın **qaydalara uyğun olub-olmadığını yoxlamaqdır**. ID `null` olmamalı, başlıq boş olmamalı, prioritet seçilməlidir. Belə yoxlama olmazsa sistemdə mənasız bilet yarana bilər.

```
if (id == null) {
    throw new IllegalArgumentException("ID boş ola bilməz");
}
if (title == null || title.trim().isEmpty()) {
    throw new IllegalArgumentException("Başlıq boş ola bilməz");
}
```

### 26. `equals()` nə edir?

**Cavab:** İki obyektin proqramın təyin etdiyi qaydaya görə **bərabər sayılıb-sayılmadığını** yoxlayır. `==` istinadlar üçün adətən **eyni obyektə baxmağı** yoxlayır. `Ticket` üçün “ID-ləri eynidirsə eyni bilet sayaq” qaydasını `equals()` ilə qurmaq olar. Bu, **mümkün dizayn qərarıdır**, Java-nın avtomatik bilet qaydası deyil.

### 27. `hashCode()` nə edir?

**Cavab:** Obyektin hash əsaslı kolleksiyalarda (məsələn `HashSet`, `HashMap`-də **açar kimi** işlənəndə) yerləşməsinə kömək edən tam ədəd verir. `equals()` iki bileti bərabər sayırsa, onların `hashCode()` nəticələri də eyni olmalıdır. **Layihəndə** `LinkedHashMap<Long, Ticket>` açarı `Long` olduğu üçün əsas axtarış `Long` açarı ilə gedir, `Ticket.hashCode()` ilə yox.

### 28. `toString()` nədir və niyə yazılır?

**Cavab:** Obyektin **mətin şəklində göstərilməsini** müəyyən edir. `System.out.println(ticket)` yazanda Java `ticket.toString()` nəticəsindən istifadə edir. `printTickets()` içində `System.out.println(tickets.get(i))` buna görə biletin ID, başlıq, status kimi məlumatlarını göstərə bilər. `toString()` yazmasan, çox vaxt `Ticket@...` kimi faydasız görünüş alarsan.

----------

# III. Collections — məlumatları bir yerdə saxlamaq (29–42)

### 29. `Array` nədir?

**Cavab:** Eyni tipli bir neçə dəyəri **sabit ölçülü** sıra kimi saxlayır. Məsələn üç bilet ID-si: `Long[] ids = {101L, 102L, 103L};`. İndekslər `0`-dan başlayır: `ids[0]` → `101`.

**Layihədə harada?** Sabit sayda məlumat lazım olsa yararlıdır, lakin ticket sayı dəyişdiyinə görə əsas saxlama üçün adətən daha çevik struktur seçilir.

### 30. `ArrayList` nədir?

**Cavab:** Ölçüsü avtomatik dəyişən **siyahıdır**. Gələn biletləri bir-bir əlavə edə, silə, sıra nömrəsinə görə götürə bilərsən.

```
List<Ticket> list = new ArrayList<>();
list.add(ticket);       // bilet əlavə et
Ticket first = list.get(0); // ilk bileti götür
```

Bu nümunədə `ticket` əvvəlcədən yaradılmış `Ticket` obyektinə istinaddır.

### 31. `Array` və `ArrayList` fərqi nədir?

**Cavab:** `Array`-in ölçüsü yaradılarkən müəyyənləşir (`new Ticket[3]`), `ArrayList` isə əlavə/silmə ilə böyüyüb-kiçilə bilir. Array-də `array.length`, list-də `list.size()` yazılır. Array həm primitive (`int[]`), həm obyekt saxlayır; `ArrayList` isə yalnız obyekt tipli elementlərlə işləyir (`List<Integer>` kimi).

### 32. `List` nədir?

**Cavab:** **Ardıcıllıqla saxlanan** elementlər üçün interface-dir — “siyahının hansı əməliyyatları olmalıdır?” qaydasını bildirir. `ArrayList` həmin qaydanı həyata keçirən class-dır. `List<Ticket>` “bu siyahıda `Ticket` obyektləri var” deməkdir.

```
List<Ticket> tickets = new ArrayList<>();
```

Solda ümumi tip (`List`), sağda konkret quruluş (`ArrayList`) seçilib.

### 33. `Set` nədir?

**Cavab:** **Təkrar element qəbul etməyən** kolleksiyadır. Məsələn eyni status adını təkrar-təkrar yazmadan unikal statusları toplamaq istəsən. Bərabərlik meyarı kolleksiyanın növündən asılıdır: `HashSet` `equals()` və `hashCode()` istifadə edir.

### 34. `Map` nədir?

**Cavab:** Məlumatı **açar → dəyər** şəklində saxlayır. Kitabxanadakı kitab nömrəsi → kitab kimi düşün: bilet ID-si → həmin bilet.

```
Map<Long, Ticket> tickets = new LinkedHashMap<>();
tickets.put(101L, ticket);
Ticket found = tickets.get(101L);
```

**Diqqət:** Map-də **açar unikal olur**. Eyni açarla yenidən `put` yazsan, köhnə dəyər əvəz oluna bilər; təkrar ID-ni ayrıca yoxlamaq lazımdır.

### 35. `HashMap` nədir?

**Cavab:** `Map`-in hash əsaslı sürətli axtarış üçün istifadə olunan növüdür. Əlavə etmə və ID ilə tapma adətən səmərəlidir, amma **elementlərin gəldiyi ardıcıllığı zəmanətlə saxlamır**. Sənin layihəndə məlum əsas struktur `LinkedHashMap<Long, Ticket>`-dir: o, standart istifadə zamanı **əlavə olunma sırasını** saxlayır.

### 36. `ArrayList` və `HashMap` fərqi nədir?

**Cavab:** `ArrayList`-də elementə **indekslə** çatırıq: `list.get(0)` → birinci bilet. `HashMap`/`LinkedHashMap`-də **açarla** çatırıq: `tickets.get(101L)` → ID-si 101 olan bilet. ID ilə tapmaq əsas işdirsə, map əlverişlidir; hamısını ardıcıl göstərmək üçün `List<Ticket>` əlverişlidir.

### 37. `List<Ticket>` nə deməkdir?

**Cavab:** “**Ticket obyektlərinin siyahısı**” deməkdir. `<Ticket>` hansı tip elementlərin saxlandığını bildirir (generic). `List<String>` mətn, `List<Long>` ID siyahısıdır. Sənin `printTickets(List<Ticket> tickets)` metodun məhz bilet siyahısını qəbul edir.

### 38. `tickets.get(i)` nə edir?

**Cavab:** `tickets` `List<Ticket>`-dirsə, **i-ci indeksdəki bilet obyektini** götürür. `i = 0` ilk bilet, `i = 1` ikinci biletdir. Məsələn `System.out.println(tickets.get(i))` həmin bileti çap edir. Səhv indeks (məsələn siyahıda 3 bilet varkən `get(3)`) `IndexOutOfBoundsException` yarada bilər.

**Vacib:** `tickets` `Map` olsaydı, `get(i)` sıra nömrəsi deyil, **açar** axtarardı. Tipə diqqət et.

### 39. `tickets.size()` nə edir?

**Cavab:** Siyahıda **neçə bilet** olduğunu qaytarır. 5 bilet varsa `size()` → `5`; indekslər isə `0,1,2,3,4` olur. Buna görə `while (i < tickets.size())` yazılır — `<=` yox, `<`.

### 40. `tickets.isEmpty()` nə edir?

**Cavab:** Siyahının **boş olub-olmadığını** bildirir (`boolean` — `true` və ya `false`). `printTickets()` boş siyahıda “Bilet tapılmadı.” göstərmək üçün bundan istifadə edir.

```
if (tickets.isEmpty()) {
    System.out.println("Bilet tapılmadı.");
}
```

### 41. `TicketStatus.values()` necə işləyir?

**Cavab:** Enum-da müəyyən edilən **bütün variantları array şəklində** verir. Məsələn `OPEN`, `IN_PROGRESS`, `CLOSED`. Menyuda bunları sıra ilə göstərmək üçün istifadə edə bilərsən.

```
TicketStatus[] statuses = TicketStatus.values();
int i = 0;
while (i < statuses.length) {
    System.out.println((i + 1) + ". " + statuses[i]);
    i++;
}
```

`statuses[i]` enum elementidir; `statuses.length` isə array ölçüsüdür.

### 42. `for (Ticket ticket : tickets)` nədir və adi `for`-dan fərqi?

**Cavab:** Bu, **for-each** dövrüdür. “Siyahıdakı hər bileti növbə ilə götür” deməkdir. İndekslə işləməyə ehtiyac olmayan vaxt daha rahatdır.

```
for (Ticket ticket : tickets) {
    System.out.println(ticket);
}
```

Adi `for` və `while` ilə `i` indeksini özün artırırsan; for-each bunu gizlədir. Sən əvvəl daha sadə olması üçün `while` variantından istifadə etmisən — **hər ikisi düzgündür**.

----------

# IV. Conditions, Loops, Input, Exception (43–57)

### 43. `if` və `else` necə işləyir?

**Cavab:** `if` şərt doğrudursa **birinci işi**, `else` isə əks halda **ikinci işi** görür. Qəbul masasında “bilet yoxdur → xəbər ver; var → göstər” qaydası kimi.

```
if (tickets.isEmpty()) {
    System.out.println("Bilet tapılmadı.");
} else {
    System.out.println("Biletləri göstərəcəyik.");
}
```

### 44. `else if` nə üçündür?

**Cavab:** **Bir neçə seçim** yoxlamağa imkan verir. Məsələn istifadəçi `1` seçibsə yeni bilet, `2` seçibsə bütün biletlər, başqa rəqəm seçibsə xəbərdarlıq.

```
if (choice == 1) {
    createTicket();
} else if (choice == 2) {
    showAllTickets();
} else {
    System.out.println("Yanlış seçim");
}
```

### 45. `while` dövrü necə işləyir?

**Cavab:** **Şərt doğru olduğu müddətdə** eyni işi təkrar edir. `printTickets()` əvvəl `i = 0` qoyur, sonra `i < size()` olduqca bir bilet çap edib `i++` edir. `i++` olmasa dövr eyni biletdə ilişib qala bilər.

### 46. `for` dövrü necə işləyir?

**Cavab:** Təkrarın **başlanğıcını, şərtini, addımını** bir sətirdə yazır:

```
for (int i = 0; i < tickets.size(); i++) {
    System.out.println(tickets.get(i));
}
```

Bu kod `while` ilə yazdığın `printTickets()` dövrünün eyni məntiqidir.

### 47. `for` və `while` arasındakı fərq nədir?

**Cavab:** İkisi də təkrar etmək üçündür. `for` çox vaxt sayğacla gəzəndə (`i=0`, `i++`) rahatdır; `while` isə “**bu şərt davam etdikcə**” düşüncəsini açıq göstərir. `readLong()`-da neçə dəfə səhv yazılacağını bilmədiyin üçün `while (true)` məntiqlidir.

### 48. `Scanner` nədir?

**Cavab:** Konsoldan **istifadəçinin yazdığı məlumatı oxuyan** Java class-ıdır. Qəbul masasında müştərini dinləyən işçi kimidir.

```
private final Scanner scanner = new Scanner(System.in);
```

`System.in` klaviatura girişi deməkdir. Bir `Scanner` ilə ardıcıl seçimlər oxumaq olar.

### 49. `scanner.nextLine()` nə edir?

**Cavab:** İstifadəçinin `Enter` düyməsinə qədər yazdığı **bütün sətri mətn (`String`) kimi oxuyur**. İstifadəçi `101` yazsa da nəticə əvvəl `"101"` olur. Sonra lazım olsa `Long.parseLong(...)` ilə ədədə çeviririk.

### 50. `.trim()` nə edir?

**Cavab:** Mətnin **əvvəlində və sonunda** olan boşluqları götürür. Məsələn `" 101 ".trim()` → `"101"`. Mətnin ortasındakı boşluqları silmir: `"Wi-Fi problem"` olduğu kimi qalır.

### 51. `Long.parseLong(input)` nə edir?

**Cavab:** Rəqəmlə yazılmış **String-i `long` tipinə çevirir**. `"101"` → `101L`. Amma `"salam"` və ya `"12.5"` üçün `NumberFormatException` yaradır. `readLong()` bunu `try-catch` ilə tutur.

### 52. `.intValue()` nə deməkdir?

**Cavab:** `Long` kimi say obyektinin ədəd dəyərini **`int` formasında** qaytarır. Məsələn `Long n = 2L; int x = n.intValue();`. **Diqqət:** çox böyük `long` ədədini `int`-ə çevirmək dəyəri dəyişə bilər; `null.intValue()` isə səhv yaradar. Menyu seçimini oxuyarkən `int` lazım ola bilər, amma bilet ID-sini əsasən `Long` saxlamaq daha uyğundur.

### 53. `try` və `catch` nədir?

**Cavab:** `try` “**bu əməliyyatı et, amma səhv yarana bilər**”, `catch` isə “**bu növ səhv olsa nə et**” deməkdir. Sənin `readLong()` metodunda istifadəçi hərf yazarsa proqram bağlanmır, yenidən rəqəm istəyir.

```
try {
    return Long.parseLong(input);
} catch (NumberFormatException error) {
    System.out.println("Düzgün rəqəm daxil edin.");
}
```

### 54. `NumberFormatException` nə vaxt yaranır?

**Cavab:** Ədədi tipə çevirmək istədiyin **mətn düzgün rəqəm olmayanda**. Məsələn `Long.parseLong("abc")`, `Long.parseLong("")`, yaxud `long` sərhədini aşan mətn. `catch (NumberFormatException error)` bu halı xüsusi olaraq tutur.

### 55. `throw` və exception niyə istifadə olunur?

**Cavab:** **Exception** proqramın normal iş axınından kənar vəziyyəti bildirən obyektdir; `throw` isə onu **özümüz qəsdən yaratmaq və ötürmək** üçündür. Məsələn, eyni ID ilə ikinci bilet açmağa icazə verməmək:

```
if (tickets.containsKey(id)) {
    throw new IllegalArgumentException("Bu ID artıq mövcuddur");
}
```

**Fərq:** `throw` səhvi qaldırır, `catch` uyğun səhvi tutub idarə edə bilər. Hər səhvi tutmaq məcburi deyil; mənalı yerdə tutmaq lazımdır.

### 56. `return` nə edir?

**Cavab:** **Metodu bitirir**, metodun return tipi `void` deyilsə **nəticəni geri qaytarır**. `readLong()`-da düzgün ədəd daxil olan kimi `return Long.parseLong(input)` işləyir və method bitir. `getTicketById()` isə tapdığı `Ticket` obyektinə istinadı qaytara bilər.

### 57. `while (true)` nədir və nə vaxt dayanır?

**Cavab:** `true` dəyişməyən doğru şərtdir, buna görə dövr nəzəri olaraq **sonsuz davam edir**. Amma içindəki `return` və ya `break` dövrü dayandıra bilər. Sənin `readLong()`-da düzgün rəqəm yazılanda `return` həm dövrü, həm də metodu bitirir; səhv yazılanda `catch` mesaj göstərir və dövr yenidən başlayır.

----------

# V. Enum və Ticket məlumat modeli (58–65)

### 58. `enum` nədir?

**Cavab:** **Yalnız əvvəlcədən müəyyən edilmiş variantlardan birini** seçməyə imkan verən Java tipidir. Təsəvvür et iş yerində status möhürləri yalnız üç cürdür: Açıq, İşlənir, Bağlı. `enum` yanlış status yazmaq riskini azaldır.

```
public enum TicketStatus {
    OPEN, IN_PROGRESS, CLOSED
}
```

### 59. `enum` ilə `String` arasında fərq nədir?

**Cavab:** `String`-də istənilən mətn yazmaq mümkündür: `"opne"` kimi yazı xətası da qəbul edilə bilər. `TicketStatus` isə yalnız `OPEN`, `IN_PROGRESS`, `CLOSED` dəyərlərini qəbul edir. Məhdud variantlar üçün enum daha etibarlıdır.

```
TicketStatus status = TicketStatus.OPEN; // düzgün
// TicketStatus status = "OPEN";         // tip səhvi
```

### 60. `TicketStatus` niyə yaradılır?

**Cavab:** Biletin **iş vəziyyətini** dəqiq idarə etmək üçün. `getTicketByStatus(TicketStatus.OPEN)` çağıranda yalnız açıq biletləri tapmağa imkan verir. Hamı eyni variant adlarını istifadə edir, qarışıqlıq azalır.

### 61. `OPEN`, `IN_PROGRESS`, `CLOSED` necə işləyir?

**Cavab:** Bunlar `TicketStatus` enum-unun **üç fərqli sabit dəyəridir**. `OPEN`: müraciət açılıb. `IN_PROGRESS`: üzərində işlənir. `CLOSED`: iş bağlanıb. Status dəyişmək üçün səndə `ticket.changeTicketStatus(newStatus)` metod məntiqi var. Bu dəyərlər özləri avtomatik iş axını yaratmır; dəyişiklik əməliyyatını kod yazır.

### 62. `TicketPriority` nədir?

**Cavab:** Biletin **nə qədər təcili** olduğunu bildirən enum tipidir. Status “iş haradadır?”, priority isə “nə qədər vacibdir?” sualına cavab verir. “Wi-Fi bütün universitetdə işləmir” müraciəti `HIGH`, sadə məlumat sorğusu `LOW` ola bilər.

```
public enum TicketPriority {
    LOW, MEDIUM, HIGH
}
```

### 63. `LOW`, `MEDIUM`, `HIGH` necə istifadə edilir?

**Cavab:** Bunlar prioritetin **sabit variantlarıdır**. `TicketPriority.HIGH` yazmaq “yüksək prioritet” deməkdir. `getHighPriorityTickets()` daxilində hər biletin prioritetini yoxlayıb yalnız `HIGH` olanları siyahıya əlavə edirsən.

```
if (ticket.getPriority() == TicketPriority.HIGH) {
    result.add(ticket);
}
```

Enum dəyərlərini `==` ilə müqayisə etmək düzgündür.

### 64. `LocalDateTime` nədir?

**Cavab:** **Tarix + saat** saxlayan Java tipidir; məsələn 23 noyabr 2026, saat 15:21. Yeni biletin nə vaxt açıldığını bilmək üçün `createdAt` field-ində saxlamaq olar.

```
private final LocalDateTime createdAt;
// Constructor daxilində:
this.createdAt = LocalDateTime.now();
```

`LocalDateTime` saat qurşağı (`timezone`) məlumatını ayrıca saxlamır; lokal tarix-saatdır.

### 65. `Ticket` içindəki `id`, `title`, `description`, `status`, `priority`, `createdAt` necə saxlanılır?

**Cavab:** Hər biri `Ticket` obyektinin **ayrı field-idir**. Bir obyekt = bir biletin bütün məlumatları. Yeni bilet yarananda constructor bunları doldurur; getter-lər oxuyur; icazə verilən dəyişikliklər method-larla edilir.

```
Ticket #101
├── id:           101
├── title:        "Wi-Fi işləmir"
├── description:  "2-ci mərtəbədə internet yoxdur"
├── ticketStatus: OPEN
├── ticketPriority: HIGH
└── createdAt:    biletin yaradılma tarixi və saatı
```

`id` biletin **kimliyidir**; status və prioritet isə onun **xüsusiyyətləridir**.

----------

# VI. Ticket Management System — layihənin işləmə məntiqi (66–80)

### 66. `Ticket.java` class-ı nə iş görür?

**Cavab:** **Bir biletin özünü təmsil edir**. Məlumatları (`id`, `title`, `description`, `status`, `priority`, `createdAt`) saxlayır və lazım olan davranışları (`getId()`, `getStatus()`, `changeTicketStatus()`, `toString()`) təqdim edir. `Ticket` özü menyu göstərmir və bütün biletlərin siyahısını idarə etmir; onun işi **bir biletin məlumatı** olmaqdır.

### 67. `TicketMenu.java` nə üçündür?

**Cavab:** **İstifadəçi ilə proqram arasında ünsiyyət** qurur. Konsolda seçimləri göstərir, `Scanner` ilə yazılanı oxuyur, `TicketService`-ə əməliyyat tapşırır, nəticəni çap edir. Qəbul masası kimi düşün: müştəriyə sual verir, sonra arxa ofisə müraciət edir.

### 68. `TicketService.java` nə iş görür?

**Cavab:** Layihənin **iş qaydalarını** idarə edir. Yeni bilet yaratmaq, ID ilə tapmaq, bütün biletləri göstərmək, statusa/prioritetə görə süzmək, status dəyişmək və silmək burada həyata keçirilir. Sənin layihəndə biletləri `LinkedHashMap<Long, Ticket>` daxilində saxlayır.

```
private final Map<Long, Ticket> tickets = new LinkedHashMap<>();
```

Bu **model sətirdir**; konkret class-da sahə tipi `Map` və ya `LinkedHashMap` kimi yazılmış ola bilər.

### 69. `TicketMenu` və `TicketService` arasında əlaqə necə qurulur?

**Cavab:** `TicketMenu` içində `TicketService` tipli field var; `Main`-dən və ya başqa yaradıcı koddan **mövcud service obyekti constructor vasitəsilə verilir**. Beləcə menyu əməliyyatların kodunu təkrarlamır, sadəcə çağırır.

```
TicketService service = new TicketService();
TicketMenu menu = new TicketMenu(service);
menu.start();
```

### 70. `TicketMenu(TicketService ticketService)` constructor-u necə işləyir?

**Cavab:** `new TicketMenu(service)` çağıranda mötərizədəki `service` **constructor parametrinə** çevrilir. Constructor da həmin istinadı class-ın `ticketService` field-inə yazır.

```
private final TicketService ticketService;

public TicketMenu(TicketService ticketService) {
    this.ticketService = ticketService;
}
```

Əsas fikir: **iki ayrı xidmət yaradılmır**. Kənardan verilən mövcud obyektin istinadı saxlanılır.

### 71. `this.ticketService = ticketService;` nə deməkdir?

**Cavab:** Soldakı `this.ticketService` = **TicketMenu obyektinin öz field-i**; sağdakı `ticketService` = **constructor-a verilmiş parametr**. “Kənardan mənə verilən xidmət obyektini özüm üçün yadda saxla” əmri kimi oxu.

### 72. `TicketService` daxilində bilet necə yaradılır?

**Cavab:** İşin məntiqi belədir: **(1)** istifadəçidən ID, başlıq, təsvir, prioritet alınır; **(2)** ID-nin təkrarlanmaması yoxlanır; **(3)** `new Ticket(...)` qurulur; **(4)** `tickets.put(id, ticket)` ilə xəritəyə əlavə olunur. Sənin müzakirə etdiyin imza:

```
public void createTicket(Long id, String title, String description,
                         TicketPriority ticketPriority) {
    if (tickets.containsKey(id)) {
        throw new IllegalArgumentException("Bu ID artıq mövcuddur");
    }
    Ticket ticket = new Ticket(id, title, description, ticketPriority);
    tickets.put(id, ticket);
}
```

Bu **öyrədici implementasiyadır**. `put`-un solunda açar `id`, sağında bütöv `Ticket` obyektidir. Constructor yeni biletə `OPEN` statusu verə bilər.

### 73. Bütün biletlər necə göstərilir?

**Cavab:** `TicketService.getAllTickets()` `Map` içindəki bütün **dəyərləri** (`Ticket` obyektlərini) siyahıya çevirir; `TicketMenu` isə `printTickets(...)` ilə onları çap edir.

```
public List<Ticket> getAllTickets() {
    return new ArrayList<>(tickets.values());
}
```

-   `tickets.values()` → yalnız bilet obyektləri;
-   `new ArrayList<>(...)` → onlardan yeni siyahı;
-   `return` → siyahını çağıran menyuya qaytarır.

### 74. Ticket ID vasitəsilə necə tapılır?

**Cavab:** İstifadəçi `101` yazır; menyu `readLong()` ilə bunu `Long`-a çevirir; `TicketService.getTicketById(101L)` xəritədə `101` **açarını** axtarır. Tapılmasa yoxlama edilməlidir.

```
public Ticket getTicketById(Long id) {
    Ticket ticket = tickets.get(id);
    if (ticket == null) {
        throw new IllegalArgumentException("Bu ID ilə bilet yoxdur");
    }
    return ticket;
}
```

Bu **öyrədici nümunədir**: layihənin konkret səhv növü və mesajı fərqli ola bilər.

### 75. Ticket statusuna görə necə filter edilir?

**Cavab:** `TicketService.getTicketByStatus(status)` bütün biletləri yoxlayır. Biletin statusu axtarılan enum dəyərinə bərabərdirsə, onu **nəticə siyahısına** əlavə edir. Sonra siyahı menyuya qaytarılır.

```
public List<Ticket> getTicketByStatus(TicketStatus status) {
    List<Ticket> result = new ArrayList<>();
    for (Ticket ticket : tickets.values()) {
        if (ticket.getStatus() == status) {
            result.add(ticket);
        }
    }
    return result;
}
```

`result`-da yalnız məsələn `OPEN` olan müraciətlər qalır. Məqsəd “bütün reyestri bir-bir yoxlayıb uyğun olanları seçmək”dir.

### 76. Yalnız `HIGH` prioritetli ticket-lər necə göstərilir?

**Cavab:** `TicketService.getHighPriorityTickets()` hər biletdə `getPriority()` nəticəsini `TicketPriority.HIGH` ilə müqayisə edir. Uyğundursa nəticə siyahısına əlavə edir, sonra `printTickets(...)` həmin siyahını çap edir.

```
if (ticket.getPriority() == TicketPriority.HIGH) {
    result.add(ticket);
}
```

Statusla prioriteti qarışdırma: **OPEN** vəziyyətdir, **HIGH** vaciblikdir.

### 77. Ticket statusu necə dəyişdirilir?

**Cavab:** Məsələn, ID `101`, yeni status `IN_PROGRESS`: menyu dəyərləri alır → service `getTicketById(101L)` ilə bilet tapır → `ticket.changeTicketStatus(TicketStatus.IN_PROGRESS)` çağırır. **Yalnız həmin biletin status field-i** dəyişir.

```
public void changeTicketStatus(Long id, TicketStatus newStatus) {
    Ticket ticket = getTicketById(id);
    ticket.changeTicketStatus(newStatus);
}
```

`Ticket` daxilində uyğun metodu yoxlama ilə qurmaq olar:

```
public void changeTicketStatus(TicketStatus newStatus) {
    if (newStatus == null) {
        throw new IllegalArgumentException("Status boş ola bilməz");
    }
    this.ticketStatus = newStatus;
}
```

### 78. Ticket necə silinir?

**Cavab:** Menyu silinəcək ID-ni oxuyur → service əvvəl biletin mövcud olmasını yoxlaya bilər → `tickets.remove(id)` biletin xəritədəki qeydlərini silir.

```
public void deleteTicket(Long id) {
    getTicketById(id);  // yoxlama: belə bilet varmı?
    tickets.remove(id); // ID açarına aid qeydi xəritədən sil
}
```

**Dəqiqlik:** Bu, obyektin JVM yaddaşından dərhal məhv edilməsi demək deyil; map-dəki giriş silinir. Başqa istinad yoxdursa obyekt gələcəkdə garbage collection üçün uyğun ola bilər.

### 79. Database olmadan ticket-lər yaddaşda necə saxlanılır?

**Cavab:** `TicketService` daxilində **RAM-də yaşayan** `LinkedHashMap` var. `createTicket()` yeni qeydi `put(...)` ilə əlavə edir. Proqram işlədiyi müddətdə bu obyektlər əlçatandır. **Proqram bağlananda adi RAM məlumatları qalıcı saxlanmır**; ayrıca fayl və ya database yazılmayıbsa növbəti açılışda itir.

### 80. Proqram başlayanda 6 sample ticket necə əlavə edilir?

**Cavab:** Əvvəlki müzakirələrimizdə `SampleTickets` adlı başlanğıc məlumat hazırlayan hissə var idi. Məntiq: tətbiq başlayanda `TicketService` qurulur, **altı nümunə** `createTicket(...)` ilə əlavə edilir, sonra menyu açılır. Müəyyən variantlar səndə fərqli ola bilər.

```
TicketService service = new TicketService();
// SampleTickets vasitəsilə service-ə 6 bilet yüklənir
TicketMenu menu = new TicketMenu(service);
menu.start();
```

**Vacib:** Bu 6 bilet database-dən gəlmir; hər yeni işə düşmə zamanı yenidən yaradıla bilər. Başlanğıc məlumatı daxil etmə hissəsinin dəqiq metod adını tam mənbə olmadan iddia etmirik.

----------

# VII. `TicketMenu`: sənin konkret method-ların (81–93)

### 81. `printTickets(List<Ticket> tickets)` nə üçün yazılıb?

**Cavab:** Menyuda **müxtəlif yerlərdən gələn bilet siyahılarını eyni qayda ilə çap etmək** üçün. Bütün biletlər, statusa görə süzülənlər və HIGH biletlər eyni ekranda-göstərmə metodundan istifadə edə bilər. Ona görə ayrıca helper method yazmaq kodun təkrarını azaldır.

**Sənin paylaşdığın formaya uyğun kod:**

```
private void printTickets(List<Ticket> tickets) {
    if (tickets.isEmpty()) {
        System.out.println("Bilet tapılmadı.");
    } else {
        int i = 0;
        while (i < tickets.size()) {
            System.out.println(tickets.get(i));
            i++;
        }
    }
}
```

**Sətr-sətr:** `private` → yalnız `TicketMenu` içində çağırılır; `void` → geriyə dəyər qaytarmır; `List<Ticket> tickets` → çap ediləcək biletləri qəbul edir. `isEmpty()` → boşdurmu? `i = 0` → ilk elementdən başla; `i < size()` → indeks mövcud olduqca davam et; `get(i)` → həmin bileti götür; `println(...)` → çap et; `i++` → sonrakına keç.

### 82. `tickets.isEmpty()` yoxlaması niyə lazımdır?

**Cavab:** Bilet yoxdursa istifadəçiyə aydın **“Bilet tapılmadı.”** mesajı vermək üçün. Bu yoxlama olmasa dövr sadəcə heç nə çap etmədən bitə bilər və istifadəçi “proqram işlədimi?” deyə düşünər. `isEmpty()` istifadəçi təcrübəsini yaxşılaşdırır.

### 83. `while (i < tickets.size())` necə işləyir?

**Cavab:** Tutaq ki 3 bilet var. `size()` → `3`. Addımlar:

Addım

`i`

`i < 3`

Nəticə

1

0

doğrudur

1-ci bileti çap et, sonra `i=1`

2

1

doğrudur

2-ci bileti çap et, sonra `i=2`

3

2

doğrudur

3-cü bileti çap et, sonra `i=3`

4

3

yanlışdır

Dövr dayanır

Burada `i` **bilet ID-si deyil**, siyahıdakı **sıra indeksidir**.

### 84. `System.out.println(tickets.get(i));` nə edir?

**Cavab:** Əvvəl `tickets.get(i)` həmin indeksdəki `Ticket` obyektini alır. Sonra `println` onu mətn şəklində konsola yazır. `Ticket.toString()` override olunubsa **o qaytardığı mətni** görürsən. Yəni kod biletin obyektini “çap oluna bilən yazıya” çevirir.

### 85. `readLong(String message)` niyə yaradılıb?

**Cavab:** İstifadəçidən **düzgün tam ədəd** almaq üçün. ID istənəndə, menyu rəqəmi gözləyir, amma istifadəçi səhvən `abc` yaza bilər. `readLong()` səhvi tutub yenidən soruşur; beləliklə bu yoxlamanı hər menyu əməliyyatında təkrar yazmırsan.

### 86. `String input = scanner.nextLine().trim();` necə işləyir?

**Cavab:** Bunu **üç addımla** oxu:

1.  `scanner.nextLine()` — istifadəçi klaviaturada nə yazdı? Məsələn `" 101 "`.
2.  `.trim()` — başlanğıc və sondakı boşluqları sil → `"101"`.
3.  `String input = ...` — alınan mətni `input` adlı dəyişəndə saxla.

Hələ `input` **String**-dir, ədəd deyil.

### 87. `Long.parseLong(input)` niyə `try` daxilindədir?

**Cavab:** Çünki istifadəçi **rəqəm olmayan mətn** yaza bilər. `parseLong("101")` uğurludur, `parseLong("salam")` isə exception qaldırır. `try` məhz bu riskli əməliyyatı əhatə edir ki, `catch` onu idarə edə bilsin.

### 88. `catch (NumberFormatException error)` niyə yazılıb?

**Cavab:** **Yalnız ədədə çevirmə formatı səhvini** tutmaq üçün. `error` exception obyektinə verilən dəyişən adıdır; onu mütləq ekrana yazmaq lazım deyil. Məsələn istifadəçi `abc` yazdısa “Düzgün rəqəm daxil edin.” mesajı görünür və `while(true)` yenidən sual verir.

### 89. `readStatus()` nə edir?

**Cavab:** İstifadəçidən **status seçimi** alır və nəticə olaraq `TicketStatus` qaytarır. Məsələn 1 → `OPEN`, 2 → `IN_PROGRESS`, 3 → `CLOSED`. Sənin son müzakirələrində bu tip helper-lər sadə `while`, `if/else` məntiqi ilə izah olunurdu.

**Məntiqi göstərən nümunə (sənin dəqiq mətnindən kopya deyil):**

```
private TicketStatus readStatus() {
    while (true) {
        System.out.println("1. OPEN, 2. IN_PROGRESS, 3. CLOSED");
        Long choice = readLong("Status seç: ");
        if (choice == 1L) return TicketStatus.OPEN;
        else if (choice == 2L) return TicketStatus.IN_PROGRESS;
        else if (choice == 3L) return TicketStatus.CLOSED;
        else System.out.println("1, 2 və ya 3 seç.");
    }
}
```

**Diqqət:** `choice` burada `Long`-dur. `choice == 1L` avtomatik unboxing ilə işləyir, çünki `readLong()` uğurlu nəticədə `null` qaytarmır. Birbaşa `Long` obyektlərini `==` ilə müqayisə etmək isə ümumən düzgün üsul deyil; obyekt-bərabərliyi üçün `equals()` daha uyğundur.

### 90. `readPriority()` nə edir?

**Cavab:** İstifadəçidən **prioritet** seçimini alır və `TicketPriority` qaytarır. Nəticə 1 → `LOW`, 2 → `MEDIUM`, 3 → `HIGH` ola bilər.

```
private TicketPriority readPriority() {
    while (true) {
        System.out.println("1. LOW, 2. MEDIUM, 3. HIGH");
        Long choice = readLong("Prioritet seç: ");
        if (choice == 1L) return TicketPriority.LOW;
        else if (choice == 2L) return TicketPriority.MEDIUM;
        else if (choice == 3L) return TicketPriority.HIGH;
        else System.out.println("1, 2 və ya 3 seç.");
    }
}
```

Bu da **öyrədici versiyadır**. Məqsəd istifadəçi nömrəsi ilə Java enum-u arasında körpü yaratmaqdır.

### 91. Helper method-ları niyə ayrıca yaradırıq?

**Cavab:** Eyni işi bir neçə dəfə yazmamaq üçün. `readLong()` həm “Yaradılacaq biletin ID-si”, həm “Silinəcək biletin ID-si” üçün işlədilə bilər. `printTickets()` isə həm bütün, həm filter edilmiş biletləri çap edir. Bu, **kodun təkrarlanmasını azaldır** və səhvi bir yerdə düzəltməyə imkan verir.

### 92. `readLong()`, `readStatus()`, `readPriority()` `changeStatus()` ilə necə əlaqələnir?

**Cavab:** Status dəyişmə ekranı iki məlumat istəyir: **hansı bilet?** və **hansı yeni status?**. Buna görə `changeStatus()` (sənin menyundakı metod adı variantına uyğun olaraq `changeTicketStatus()` ola bilər) belə işləyir:

```
private void changeTicketStatus() {
    Long id = readLong("Bilet ID-si: ");
    TicketStatus newStatus = readStatus();
    ticketService.changeTicketStatus(id, newStatus);
    System.out.println("Status dəyişdirildi.");
}
```

**Axın:** `readLong` → `101L` qaytarır; `readStatus` → `IN_PROGRESS` qaytarır; `ticketService.changeTicketStatus(...)` → biletin statusunu dəyişir; `println` → istifadəçiyə nəticəni bildirir. **Yuxarıdakı nümunədə xəta emalı göstərilməyib**: ID tapılmasa service exception verə bilər, menyuda bunu uyğun `try-catch` ilə göstərmək lazımdır.

### 93. İstifadəçi səhv məlumat yazanda proqram necə idarə edir?

**Cavab:** **Məlumatın növünə görə** fərqli yoxlamalar var:

-   **ID yerinə `abc` yazır** → `Long.parseLong(...)` `NumberFormatException` yaradır → `readLong()`-dakı `catch` mesaj verir və yenidən oxuyur.
-   **Status üçün 9 seçir** → `readStatus()`-un `else` hissəsi “1, 2 və ya 3 seç” göstərir.
-   **Boş başlıq verir** → `Ticket` constructor-unda validation bunu `IllegalArgumentException` ilə rədd edə bilər.
-   **Artıq mövcud olan ID verir** → `TicketService.createTicket()` təkrar açarı qəbul etməməlidir.
-   **Olmayan ID-ni silmək istəyir** → `getTicketById()` yoxlayır; tapılmırsa uyğun səhv göstərilməlidir.

**Əsas fərq:** `readLong()` **formatı** yoxlayır; constructor **biletin düzgün məlumatlı olmasını** yoxlayır; service isə **biznes qaydasını** (məsələn ID unikaldırmı) yoxlayır. Bunlar üç ayrı məsuliyyətdir.
