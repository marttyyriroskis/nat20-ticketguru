# Nat20 TicketGuru

Tiimi: Janne Airaksinen, Paul Carlson, Jesse Hellman, Julia Hämäläinen & Tomi Lappalainen

# Johdanto

Tässä projektissa luodaan lipunmyyntijärjestelmä TicketGuru, jonka avulla lipputoimisto voi seurata tapahtumiaan ja tarjolla olevia lippuja sekä myydä lippuja asiakkaille.

Projektin asiakkaana toimii lipputoimisto. Järjestelmän varsinaiset käyttäjät ovat lipputoimiston lipunmyyjät, jotka myyvät lippuja tapahtumiin fyysisesti lipunmyyntipisteessä. Järjestelmää käyttävät myös tapahtumakoordinaattorit ja järjestelmän hallinnoijat. Jatkokehittelynä TicketGuruun on tarkoitus lisätä verkkokauppa, mutta sen toteuttaminen ei kuulu tämän projektin laajuuteen.

TicketGuru-järjestelmän avulla lipputoimisto voi tehostaa liiketoimintaansa myymällä lippuja helpommin, seuraamalla tapahtuma- ja lipputarjontaa sekä pohjaamalla päätöksentekoaan järjestelmän tuottamiin liiketoiminnan raportteihin.

Teknologioina projektissa käytetään Javaa, Spring Boot -viitekehystä ja PostgreSQL-relaatiotietokantaa. Käyttöliittymänä hyödynnetään Thymeleafia. Järjestelmää on tarkoitus käyttää desktop-tietokoneelta; tabletti- tai mobiilikäyttöliittymiä ei tässä projektissa rakenneta.

Projektin lopputuotteena on käyttövalmis TicketGuru-lipunmyyntijärjestelmä sekä siihen liittyvä dokumentaatio.

# Järjestelmän määrittely

Määrittelyssä järjestelmää tarkastellaan käyttäjän näkökulmasta. Järjestelmän toiminnot hahmotellaan käyttötapausten tai käyttäjätarinoiden kautta, ja kuvataan järjestelmän käyttäjäryhmät.

<details>
<summary>Käyttäjäryhmät</summary>

- Lipunmyyjä (**'SALESPERSON'**) voi luoda tai muokata myyntitapahtumia. Hän voi myös selata tapahtumien, lippujen, lipputyyppien tai tapahtumapaikkojen tietoja.
- Lipuntarkastaja (**'TICKET_INSPECTOR'**) voi tarkastaa myytyjä lippuja ja merkitä ne käytetyiksi.
- Tapahtumakoordinaattori (**'COORDINATOR'**) voi luoda tai muokata tapahtumia, lipputyyppejä tai tapahtumapaikkoja.
- Ylläpitäjällä (**'ADMIN'**) on kaikki edellä mainitut oikeudet, sekä lisäksi oikeudet poistaa järjestelmässä olevia tietoja. Hän voi tarvittaessa tarkastella järjestelmälokeja.
</details>

<details>
<summary>Käyttäjätarinat</summary>

### Käyttäjätarina 1

_"Lipunmyyjänä haluan nähdä tulevat tapahtumat ja saatavilla olevat liput pysyäkseni ajan tasalla."_

**Hyväksymiskriteerit:**

- Lipunmyyjä näkee kaikki tulevat tapahtumat ja niihin liittyvät tiedot: päivämäärä, aika ja tapahtuman nimi
- Lipunmyyjä näkee tapahtuman saatavilla olevien lippujen määrän

### Käyttäjätarina 2

_"Lipunmyyjänä haluan valita tapahtuman ja haluamani määrän lippuja voidakseni palvella asiakkaitani."_

**Hyväksymiskriteerit:**

- Lipunmyyjä voi valita tapahtuman saadakseen lisätietoja (paikka, kuvaus, kaupunki ja lippujen tyypit)
- Lipunmyyjä voi valita haluamansa määrän lippuja per lippu tyyppi myytäväksi valittuun tapahtumaan
- Täyteen varattujen tapahtumien lippuja ei voida myydä

### Käyttäjätarina 3

_"Lipunmyyjänä haluan tulostaa myydyt liput viimeistelläkseni ostotapahtuman."_

**Hyväksymiskriteerit:**

- Lipunmyyjä voi tulostaa myydyt liput
- Tulostetussa lipussa on kaikki olennaiset tiedot: tapahtuma, tapahtumapaikka, lipputyyppi, lipun hinta ja lipun yksilöllinen koodi

### Käyttäjätarina 4

_"Lipunmyyjänä haluan voida etsiä myytyä lippua ongelmatilanteessa."_

**Hyväksymiskriteerit:**

- Lipunmyyjä voi etsiä myytyä lippua yksilöllisen koodin avulla
- Haku näyttää kaikki olennaiset tiedot: myyntitapahtuma, ostoajankohta, tapahtuma, lippu tyyppi ja hinta

### Käyttäjätarina 5

_"Lipunmyyjänä haluan voida peruuttaa myydyn lipun, jotta asiakas saa rahansa takaisin."_

**Hyväksymiskriteerit:**

- Lipunmyyjä voi peruuttaa lipun
- Peruutettua lippua ei voida enää käyttää tapahtumassa

### Käyttäjätarina 6

_"Tapahtumakoordinaattorina haluan muokata tapahtumia, jos niissä on virhe."_

**Hyväksymiskriteerit:**

- Tapahtumakoordinaattori voi muokata tiettyjä tietoja tapahtumasta, kuten kuvausta, maksimilippujen määrää, lippujen tyyppejä tai hintoja

### Käyttäjätarina 7

_"Tapahtumakoordinaattorina haluan luoda uuden tapahtuman, jotta lippuja voidaan myydä tapahtumaan."_

**Hyväksymiskriteerit:**

- Tapahtumakoordinaattori voi luoda uuden tapahtuman kaikilla olennaisilla tiedoilla (tapahtuman nimi, aika, paikka, kuvaus, kaupunki, lippujen tyypit, hinnat ja myytävien lippujen maksimimäärä)
- Tapahtuma näkyy tapahtumakoordinaattoreille ja lipunmyyjille oikealla lipputilanteella

### Käyttäjätarina 8

_"Tapahtumakoordinaattorina haluan nähdä myyntiraportteja, jotta voin seurata tietyn tapahtuman myyntiä ja tehdä muutoksia tarvittaessa."_

**Hyväksymiskriteerit:**

- Tapahtumakoordinaattori näkee myyntiraportit (myydyt liput tyypeittäin, summat ja yksittäiset myynnit)

### Käyttäjätarina 9

_"Ylläpitäjänä haluan lisätä käyttäjiä, jotta ihmiset voivat käyttää järjestelmää."_

**Hyväksymiskriteerit:**

- Ylläpitäjä voi luoda uusia käyttäjiä kaikilla olennaisilla tiedoilla (vähimmäisvaatimus: sähköpostiosoite ja salasana)
- Uudet käyttäjät voivat kirjautua sisään

### Käyttäjätarina 10

_"Ylläpitäjänä haluan poistaa käyttäjiä, jotta käyttäjätiedot ovat ajantasaisia."_

**Hyväksymiskriteerit:**

- Ylläpitäjä voi poistaa käyttäjiä
- Poistettujen käyttäjien tiedot eivät ole enää saatavilla järjestelmässä

### Käyttäjätarina 11

_"Ylläpitäjänä haluan muokata käyttäjiä, jotta käyttäjätiedot ovat ajantasaisia."_

**Hyväksymiskriteerit:**

- Ylläpitäjä voi muokata käyttäjiä
- Muokattujen käyttäjien tiedot ovat ajantasaisia

### Käyttäjätarina 12

_"Ylläpitäjänä haluan lisätä käyttäjärooleja, jotta vain järjestelmään oikeutetut voivat käyttää sitä."_

**Hyväksymiskriteerit:**

- Ylläpitäjä voi lisätä käyttäjille rooleja
- Roolit muuttuvat sen mukaisesti
- Käyttäjien oikeudet määräytyvät heidän rooliensa perusteella

### Käyttäjätarina 13

_"Ylläpitäjänä haluan muokata käyttäjärooleja, jotta vain järjestelmään oikeutetut voivat käyttää sitä."_

**Hyväksymiskriteerit:**

- Ylläpitäjä voi muokata käyttäjille annettuja rooleja
- Roolit muuttuvat sen mukaisesti
- Käyttäjien oikeudet määräytyvät heidän rooliensa perusteella

### Käyttäjätarina 14

_"Ylläpitäjänä haluan poistaa käyttäjärooleja, jotta vain järjestelmään oikeutetut voivat käyttää sitä."_

**Hyväksymiskriteerit:**

- Ylläpitäjä voi poistaa käyttäjiltä rooleja
- Roolit muuttuvat sen mukaisesti
- Käyttäjien oikeudet määräytyvät heidän rooliensa perusteella

### Käyttäjätarina 15

_"Ylläpitäjänä haluan nähdä järjestelmäraportit ja lokit ongelmatilanteissa."_

**Hyväksymiskriteerit:**

- Ylläpitäjä voi nähdä järjestelmäraportit
- Ylläpitäjä voi nähdä lokit

**Lisätiedot**

- Ei implementoitu

</details>

<p>&nbsp;</p>

# Käyttöliittymä

![GUI Diagram](https://raw.githubusercontent.com/marttyyriroskis/nat20-ticketguru/refs/heads/dev/images/ticketguru-gui-diagram.png)

Yllä oleva kuva esittää TicketGuru-ohjelman käyttöliittymää ja sen eri näkymien välisiä siirtymiä

- Päävalikko: Käyttäjän aloitusvalikko, josta pääsee kaikkiin ohjelman osioihin.
  - Lipunmyynti: Toiminto, joka kattaa lipunmyyntiprosessin. Täältä siirrytään seuraaviin:
    - Myyntitapahtumat: Näyttää yksityiskohtaisesti kaikki myyntitapahtumat.
    - Lippujen tulostus: Tulostaa myydyt liput asiakkaalle.
  - Raportit: Näyttää myyntitapahtumien raportit.
  - Tapahtumahallinta: Täältä hallitaan tapahtumia, mukaan lukien:
    - Lipputyypit: Mahdollistaa erilaisten lipputyyppien määrittelyn ja hallinnan.
  - Lippujen tarkastus: Tarkistaa ostettujen lippujen kelpoisuuden tapahtuman sisäänkäynnillä.

Lisäksi lisätään mahdollisesti hallintaosio tapahtumapaikoille.

# Tietokanta

Alla mallikuva tietokannasta, josta käy ilmi tietokannan sisältämät tiedot, taulujen väliset suhteet ja avainten määritykset.

![Database Diagram](https://raw.githubusercontent.com/marttyyriroskis/nat20-ticketguru/refs/heads/dev/images/ticketguru-db-diagram.png)

Lisäksi jokainen tietokannan taulu ja niiden attribuutit kuvataan tässä tietohakemistossa.

<details>
<summary>permissions</summary>

permissions-taulu sisältää luvat. Roolilla voi olla monta lupaa, ja sama lupa voi kuulua useampaan eri rooliin. Siksi näillä on välitaulu, role_permissions.

| Kenttä | Tyyppi      | Kuvaus        |
| ------ | ----------- | ------------- |
| id     | int PK      | Luvan id      |
| title  | varchar(50) | Luvan otsikko |

</details>

<details>
<summary>role_permissions</summary>

role_permissions on välitaulu roolien ja niiden lupien välillä. Sillä on siis monen suhde yhteen molempiin tauluihin.

| Kenttä        | Tyyppi | Kuvaus                                               |
| ------------- | ------ | ---------------------------------------------------- |
| role_id       | int PK | Viittaus rooliin [roles](#roles)-taulussa            |
| permission_id | int PK | Viittaus lupaan [permissions](#permissions)-taulussa |

</details>

<details>
<summary>roles</summary>

roles-taulu määrittää kaikki mahdolliset käyttäjäroolit, joita käyttäjillä voi olla.

| Kenttä | Tyyppi      | Kuvaus      |
| ------ | ----------- | ----------- |
| id     | int PK      | Roolin id   |
| title  | varchar(50) | Roolin nimi |

</details>

<details>
<summary>users</summary>

users-taulu sisältää käyttäjät. Yhdellä käyttäjällä on vain yksi rooli, mutta sama rooli voi kuulua useammalle käyttäjälle.

| Kenttä     | Tyyppi       | Kuvaus                                    |
| ---------- | ------------ | ----------------------------------------- |
| id         | int PK       | Käyttäjän id                              |
| email      | varchar(150) | Käyttäjän email                           |
| first_name | varchar(150) | Käyttäjän etunimi                         |
| last_name  | varchar(150) | Käyttäjän sukunimi                        |
| password   | varchar(250) | Salasanan hash(+salt)                     |
| role_id    | int FK       | Viittaus rooliin [roles](#roles)-taulussa |

</details>

<details>
<summary>sales</summary>

sales-taulu kuvaa yhtä myyntitapahtumaa. Jokaisella myyntitapahtumalla on yksi myynnin hoitanut käyttäjä.

| Kenttä  | Tyyppi   | Kuvaus                                    |
| ------- | -------- | ----------------------------------------- |
| id      | int PK   | Myyntitapahtuman id                       |
| paid_at | datetime | Myyntihetki                               |
| user_id | int FK   | Viittaus myyjään [users](#users)-taulussa |

</details>

<details>
<summary>tickets</summary>

tickets-taulu sisältää yksittäisiä lippuja eri tapahtumiin. Lippu toimii myös välitaulunta [sales](#sales) ja [ticket_types](#ticket_types) taulujen välillä.

| Kenttä         | Tyyppi      | Kuvaus                                                         |
| -------------- | ----------- | -------------------------------------------------------------- |
| id             | int PK      | Lipun id                                                       |
| ticket_type_id | int FK      | Viittaus lipun tyyppiin [ticket_types](#ticket_types)-taulussa |
| sale_id        | int FK      | Viittaus myyntiin [sales](#sales)-taulussa                     |
| barcode        | varchar(50) | Viivakoodi, jolla voidaan skannata lippu                       |
| used_at        | datetime    | Päivämäärä ja aika, jolloin lippu on merkitty käytetyksi       |
| price          | double      | Lipusta maksettu hinta                                         |

</details>

<details>
<summary>ticket_types</summary>

ticket_types-taulu sisältää lipputyypit. Yhdessä tapahtumassa voi olla monta lipputyyppiä. Lipputyyppi määrittää aina vain yhtä lippua kerrallaan.

| Kenttä          | Tyyppi      | Kuvaus                                          |
| --------------- | ----------- | ----------------------------------------------- |
| id              | int PK      | Lipputyypin id                                  |
| name            | varchar(50) | Lipputyypin nimimerkki                          |
| retail_price    | double      | Lipputyypin OVH                                 |
| event_id        | int FK      | Viittaus tapahtumaan [events](#events)-taulussa |
| total_available | int         | Lippuja saatavilla                              |

</details>

<details>
<summary>events</summary>

events-taulu sisältää tapahtumat. Jokaiselle tapahtumalle luodaan oma rivi. Tapahtuma pidetään aina yhdessä tapahtumapaikassa (venue), mutta yhdessä tapahtumapaikassa voidaan pitää monta tapahtumaa eri aikoihin.

| Kenttä             | Tyyppi       | Kuvaus                                                |
| ------------------ | ------------ | ----------------------------------------------------- |
| id                 | int PK       | Tapahtuman id                                         |
| name               | varchar(100) | Tapahtuman nimi                                       |
| total_tickets      | int          | Myytävien loppujen määrä                              |
| begins_at          | datetime     | Tapahtuman aloituspäivä ja -aika                      |
| ends_at            | datetime     | Tapahtuman päättymispäivä ja -aika                    |
| ticket_sale_begins | date time    | Tapahtuman lipunmyynnin aloituspäivä ja -aika         |
| description        | varchar(500) | Tapahtuman kuvaus                                     |
| venue              | int FK       | Viittaus tapahtumapaikkaan [venues](#venues)-taulussa |

</details>

<details>
<summary>venues</summary>

venues-taulu sisältää tapahtumapaikat. Yksi tapahtumapaikka on aina yhdessä postinumerossa, mutta yhdellä postinumerolla voi olla useampia tapahtumia.

| Kenttä  | Tyyppi        | Kuvaus                                                           |
| ------- | ------------- | ---------------------------------------------------------------- |
| id      | int PK        | tapahtumapaikan id                                               |
| name    | varchar(100)  | tapahtumapaikan nimi                                             |
| address | varchar(100)  | tapahtumapaikan osoite                                           |
| zipcode | varchar(5) FK | Viittaus tapahtumapaikan postiosoitteeseen [zipcodes](#zipcodes) |

</details>

<details>
<summary>zipcodes</summary>

zipcodes-taulu sisältää tapahtumapaikkojen osoitteiden postinumerot ja kaupungit.

| Kenttä  | Tyyppi        | Kuvaus                         |
| ------- | ------------- | ------------------------------ |
| zipcode | varchar(5) PK | Postinumero                    |
| city    | varchar(100)  | Postinumeron mukainen kaupunki |

</details>

<p>&nbsp;</p>

# Tekninen kuvaus

Teknisessä kuvauksessa esitetään järjestelmän toteutuksen suunnittelussa tehdyt tekniset
ratkaisut, esim.

- Missä mikäkin järjestelmän komponentti ajetaan (tietokone, palvelinohjelma)
  ja komponenttien väliset yhteydet (vaikkapa tähän tyyliin:
  https://security.ufl.edu/it-workers/risk-assessment/creating-an-information-systemdata-flow-diagram/)
- Palvelintoteutuksen yleiskuvaus: teknologiat, deployment-ratkaisut yms.
- Keskeisten rajapintojen kuvaukset, esimerkit REST-rajapinta. Tarvittaessa voidaan rajapinnan käyttöä täsmentää
  UML-sekvenssikaavioilla.
- Toteutuksen yleisiä ratkaisuja, esim. turvallisuus.

Tämän lisäksi

- ohjelmakoodin tulee olla kommentoitua
- luokkien, metodien ja muuttujien tulee olla kuvaavasti nimettyjä ja noudattaa
  johdonmukaisia nimeämiskäytäntöjä
- ohjelmiston pitää olla organisoitu komponentteihin niin, että turhalta toistolta
  vältytään

# Testaus

Tässä kohdin selvitetään, miten ohjelmiston oikea toiminta varmistetaan
testaamalla projektin aikana: millaisia testauksia tehdään ja missä vaiheessa.
Testauksen tarkemmat sisällöt ja testisuoritusten tulosten raportit kirjataan
erillisiin dokumentteihin.

Tänne kirjataan myös lopuksi järjestelmän tunnetut ongelmat, joita ei ole korjattu.

# Asennustiedot

## Spring Boot -sovelluksen käyttöönotto tuotantopalvelimella

Tässä ohjeessa käydään läpi Spring Boot -sovelluksen käyttöönotto NGINX-palvelimella, PostgreSQL-tietokannan käyttäminen sekä A-tietueen asettaminen verkkotunnukselle.

---

<details>
<summary>Esivaatimukset</summary>

1. Näiden ohjeiden noudattamiseksi tarvitset kaksi asiaa:

   - Verkkotunnuksen (esim. saatavilla [hover.com](https://hover.com):sta)
   - VPS:n (Virtual Private Server). [Hetzner](https://hetzner.com) tai [DigitalOcean](https://digitalocean.com) ovat suosittuja palveluntarjoajia.

2. Käyttääksesi palvelimesi komentoriviä SSH:n kautta, seuraa [ohjeita tämän linkin takaa.](https://community.hetzner.com/tutorials/add-ssh-key-to-your-hetzner-cloud)

Seuraavassa esimerkissä meillä on `hellmanstudios.fi` domain ja luomme sille tg subdomainin, eli `tg.hellmanstudios.fi`, jossa TicketGuru Spring Boot sovellus isännöidään.

</details>

<details>
<summary>Vaihe 1: Git-repositorion kloonaus</summary>

1. **Siirry kotihakemistoon** (jos et ole jo siellä):

   ```bash
   cd ~
   ```

2. **Kloonaa repositorio** GitHubista:

   ```bash
   git clone https://github.com/marttyyriroskis/nat20-ticketguru.git tg.hellmanstudios.fi
   ```

3. **Siirry projektihakemistoon**:
   ```bash
   cd tg.hellmanstudios.fi
   ```
   </details>

<details>
<summary>Vaihe 2: PostgreSQL-tietokannan määrittäminen</summary>

1. **Asenna PostgreSQL** (jos sitä ei ole jo asennettu):

   ```bash
   sudo apt update
   sudo apt install postgresql postgresql-contrib
   ```

2. **Kirjaudu PostgreSQL:ään** käyttäjänä `postgres`:

   ```bash
   sudo -i -u postgres
   ```

3. **Luo tietokanta ja käyttäjä** sovellusta varten:

   ```sql
   # Käynnistä PostgreSQL:n komentorivi
   psql

   # Luo tietokanta
   CREATE DATABASE ticketguru;

   # Luo käyttäjä salasanalla
   CREATE USER psqladmin WITH PASSWORD 'psqladmin';

   # Myönnä oikeudet käyttäjälle uuteen tietokantaan
   GRANT ALL PRIVILEGES ON DATABASE ticketguru TO psqladmin;

   # Poistu PostgreSQL:stä
   \q
   ```

4. **Poistu `postgres`-käyttäjätilistä**:
   ```bash
   exit
   ```
   </details>

<details>
<summary>Vaihe 3: Sovelluksen paikallisen profiilin määrittäminen</summary>

1. **Kopioi `application-local.properties.example`** tiedostoksi `application-local.properties`:

   ```bash
   cp src/main/resources/application-local.properties.example src/main/resources/application-local.properties
   ```

2. **Muokkaa `application-local.properties`** PostgreSQL-yhteyden määrittämiseksi:

   ```bash
   nano src/main/resources/application-local.properties
   ```

3. **Lisää seuraavat PostgreSQL-konfiguraatiot** tiedostoon `application-local.properties`:

   ```properties
   DB_HOST=localhost
   DB_PORT=5432
   DB_NAME=ticketguru
   DB_USER=psqladmin
   DB_PASSWORD=psqladmin
   ```

4. **Tallenna ja sulje tiedosto**.
</details>

<details>
<summary>Vaihe 4: Rakenna Spring Boot JAR</summary>

1. **Rakenna JAR-tiedosto** käyttämällä Mavenia:

   ```bash
   mvn clean package
   ```

   Tämä luo JAR-tiedoston sijaintiin `/home/user/tg.hellmanstudios.fi/target/ticketguru-0.0.1-SNAPSHOT.jar`. "user" on oma käyttäjätunnuksesi
   </details>

<details>
<summary>Vaihe 5: Systemd-palvelutiedoston asettaminen</summary>

1. **Luo uusi systemd-palvelutiedosto** sovellukselle:

   ```bash
   sudo nano /etc/systemd/system/tg.service
   ```

2. **Lisää seuraava konfiguraatio**, ja päivitä polut tarvittaessa:

   ```ini
   [Unit]
   Description=TG Spring Boot -sovellus
   After=syslog.target

   [Service]
   User=www-data
   Group=www-data
   ExecStart=/usr/bin/java -jar /home/user/tg.hellmanstudios.fi/target/ticketguru-0.0.1-SNAPSHOT.jar # vaihda "user" omaksi käyttäjätunnukseksi
   SuccessExitStatus=143
   Restart=on-failure
   RestartSec=10
   StandardOutput=journal
   StandardError=inherit

   [Install]
   WantedBy=multi-user.target
   ```

3. **Lataa systemd uudelleen** uuden palvelutiedoston ottamiseksi käyttöön:

   ```bash
   sudo systemctl daemon-reload
   ```

4. **Ota palvelu käyttöön ja käynnistä se**:

   ```bash
   sudo systemctl enable tg.service
   sudo systemctl start tg.service
   ```

5. **Tarkista palvelun tila**:
   ```bash
   sudo systemctl status tg.service
   ```
   </details>

<details>
<summary>Vaihe 6: NGINX:n konfigurointi käänteisenä välityspalvelimena</summary>

1. **Luo NGINX-konfiguraatiotiedosto** sovelluksellesi:

   ```bash
   sudo nano /etc/nginx/sites-available/tg
   ```

2. **Lisää seuraava konfiguraatio**:

   ```nginx
   server {
       listen 80;
       server_name tg.hellmanstudios.fi;

       location / {
           proxy_pass http://localhost:8080;
           proxy_set_header Host $host;
           proxy_set_header X-Real-IP $remote_addr;
           proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
           proxy_set_header X-Forwarded-Proto $scheme;
       }
   }
   ```

3. **Ota konfiguraatio käyttöön** luomalla symbolinen linkki `sites-enabled`-hakemistoon:

   ```bash
   sudo ln -s /etc/nginx/sites-available/tg /etc/nginx/sites-enabled/
   ```

4. **Testaa ja lataa NGINX uudelleen** muutosten käyttöönottoa varten:
   ```bash
   sudo nginx -t
   sudo systemctl reload nginx
   ```
   </details>

<details>
<summary>Vaihe 7: A-tietueen asettaminen DNS asetuksista</summary>

Alla on käytetty [Cloudflarea](https://cloudflare.com), joka on vahvasti suositeltu, mutta domain nimien palveluntarjoajilla on aina omat DNS asetussivut, joihin alla olevaa on helppo soveltaa.

1. **Kirjaudu Cloudflareen** ja siirry `hellmanstudios.fi` -verkkotunnuksen DNS-asetuksiin.

2. **Luo uusi A-tietue**:

   - **Tyyppi**: `A`
   - **Nimi**: `tg` (tämä luo `tg.hellmanstudios.fi`)
   - **IPv4-osoite**: Syötä palvelimesi IP-osoite
   - **TTL**: Auto
   - **Välitystila**: Käytössä (oranssi pilvi), jos haluat käyttää Cloudflarea, tai Pois päältä (harmaa pilvi) ohittaaksesi sen.

3. **Tallenna tietue**.
</details>

<details>
<summary>Vaihe 8: Käyttöönoton tarkistaminen</summary>

1. Avaa selain ja siirry osoitteeseen `http://tg.hellmanstudios.fi`.
2. Sinun pitäisi nähdä Spring Boot -sovelluksesi palvelevan NGINX:n kautta, kytkettynä PostgreSQL-tietokantaan ja käytettävissä `tg.hellmanstudios.fi` -aliverkkotunnuksella.

Deployment Script
Alla olevat kommennot suorittamalla voit päivittää ohjelman palvelimella

```bash
cd ~/tg.hellmanstudios.fi
git pull origin main
mvn clean package || ./mvnw clean package
echo "your-sudo-password" | sudo -S systemctl daemon-reload
echo "your-sudo-password"  | sudo -S systemctl stop rentanything
echo "your-sudo-password"  | sudo -S systemctl start rentanything
echo "🚀 Application deployed!"
```

</details>
