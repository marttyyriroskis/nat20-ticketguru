# Nat20 TicketGuru

Tiimi: Janne Airaksinen, Paul Carlson, Jesse Hellman, Julia Hämäläinen & Tomi Lappalainen

## Johdanto

Tässä projektissa luodaan lipunmyyntijärjestelmä TicketGuru, jonka avulla lipputoimisto voi seurata tapahtumiaan ja tarjolla olevia lippuja sekä myydä lippuja asiakkaille.

Projektin asiakkaana toimii lipputoimisto. Järjestelmän varsinaiset käyttäjät ovat lipputoimiston lipunmyyjät, jotka myyvät lippuja tapahtumiin fyysisesti lipunmyyntipisteessä. Järjestelmää käyttävät myös tapahtumakoordinaattorit ja järjestelmän hallinnoijat. Jatkokehittelynä TicketGuruun on tarkoitus lisätä verkkokauppa, mutta sen toteuttaminen ei kuulu tämän projektin laajuuteen.

TicketGuru-järjestelmän avulla lipputoimisto voi tehostaa liiketoimintaansa myymällä lippuja helpommin, seuraamalla tapahtuma- ja lipputarjontaa sekä pohjaamalla päätöksentekoaan järjestelmän tuottamiin liiketoiminnan raportteihin.

Teknologioina projektissa käytetään Javaa, Spring Boot -viitekehystä ja PostgreSQL-relaatiotietokantaa. Käyttöliittymänä hyödynnetään Thymeleafia. Järjestelmää on tarkoitus käyttää desktop-tietokoneelta; tabletti- tai mobiilikäyttöliittymiä ei tässä projektissa rakenneta.

Projektin lopputuotteena on käyttövalmis TicketGuru-lipunmyyntijärjestelmä sekä siihen liittyvä dokumentaatio.

## Järjestelmän määrittely

Määrittelyssä järjestelmää tarkastellaan käyttäjän näkökulmasta. Järjestelmän
toiminnot hahmotellaan käyttötapausten tai käyttäjätarinoiden kautta, ja kuvataan järjestelmän
käyttäjäryhmät.

### Käyttäjäryhmät

- Lipunmyyjä on järjestelmän ensisijainen käyttäjä joka myy asiakkaille tapahtuma lippuja järjestelmän kautta.
- Tapahtumakoordinaattori lisää uusia tapahtumia tarjolle ja määrittää niille oleelliset tiedot sekä näkee tapahtumaan myytyjen lippujen tilastoja, jotta voi tehdä muutoksia tarvittaessa.
- Järjestelmää hallitseva ylläpitäjä voi lisätä, muokata ja poistaa käyttäjiä ja niihin liittyviä tietoja, sekä lisätä, muokata ja poistaa käyttäjärooleja. Ylläpitäjä näkee myös järjestelmä raportteja ja lokeja ongelmien ehkäisemiseksi.

### Käyttäjätarinat

#### Käyttäjätarina 1

_"Lipunmyyjänä haluan nähdä tulevat tapahtumat ja saatavilla olevat liput pysyäkseni ajan tasalla."_

**Hyväksymiskriteerit:**

- Lipunmyyjä näkee kaikki tulevat tapahtumat ja niihin liittyvät tiedot: päivämäärä, aika ja tapahtuman nimi
- Lipunmyyjä näkee tapahtuman saatavilla olevien lippujen määrän

#### Käyttäjätarina 2

_"Lipunmyyjänä haluan valita tapahtuman ja haluamani määrän lippuja voidakseni palvella asiakkaitani."_

**Hyväksymiskriteerit:**

- Lipunmyyjä voi valita tapahtuman saadakseen lisätietoja (paikka, kuvaus, kaupunki ja lippujen tyypit)
- Lipunmyyjä voi valita haluamansa määrän lippuja per lippu tyyppi myytäväksi valittuun tapahtumaan
- Täyteen varattujen tapahtumien lippuja ei voida myydä

#### Käyttäjätarina 3

_"Lipunmyyjänä haluan tulostaa myydyt liput viimeistelläkseni ostotapahtuman."_

**Hyväksymiskriteerit:**

- Lipunmyyjä voi tulostaa myydyt liput
- Tulostetussa lipussa on kaikki olennaiset tiedot: myyntitapahtuma, ostoajankohta, summa, tapahtuma, lippu tyyppi, lipun hinta ja lipun yksilöllinen koodi

#### Käyttäjätarina 4

_"Lipunmyyjänä haluan voida etsiä myytyä lippua ongelmatilanteessa."_

**Hyväksymiskriteerit:**

- Lipunmyyjä voi etsiä myytyä lippua yksilöllisen koodin avulla
- Haku näyttää kaikki olennaiset tiedot: myyntitapahtuma, ostoajankohta, tapahtuma, lippu tyyppi ja hinta

#### Käyttäjätarina 5

_"Lipunmyyjänä haluan voida peruuttaa myydyn lipun, jotta asiakas saa rahansa takaisin."_

**Hyväksymiskriteerit:**

- Lipunmyyjä voi peruuttaa lipun
- Peruutettua lippua ei voida enää käyttää tapahtumassa

#### Käyttäjätarina 6

_"Tapahtumakoordinaattorina haluan muokata tapahtumia, jos niissä on virhe."_

**Hyväksymiskriteerit:**

- Tapahtumakoordinaattori voi muokata tiettyjä tietoja tapahtumasta, kuten kuvausta, maksimilippujen määrää, lippujen tyyppejä tai hintoja

#### Käyttäjätarina 7

_"Tapahtumakoordinaattorina haluan luoda uuden tapahtuman, jotta lippuja voidaan myydä tapahtumaan."_

**Hyväksymiskriteerit:**

- Tapahtumakoordinaattori voi luoda uuden tapahtuman kaikilla olennaisilla tiedoilla (tapahtuman nimi, aika, paikka, kuvaus, kaupunki, lippujen tyypit, hinnat ja myytävien lippujen maksimimäärä)
- Tapahtuma näkyy tapahtumakoordinaattoreille ja lipunmyyjille oikealla lipputilanteella

#### Käyttäjätarina 8

_"Tapahtumakoordinaattorina haluan nähdä myyntiraportteja, jotta voin seurata tietyn tapahtuman myyntiä ja tehdä muutoksia tarvittaessa."_

**Hyväksymiskriteerit:**

- Tapahtumakoordinaattori näkee myyntiraportit (myydyt liput tyypeittäin, summat ja yksittäiset myynnit)

#### Käyttäjätarina 9

_"Ylläpitäjänä haluan lisätä käyttäjiä, jotta ihmiset voivat käyttää järjestelmää."_

**Hyväksymiskriteerit:**

- Ylläpitäjä voi luoda uusia käyttäjiä kaikilla olennaisilla tiedoilla (vähimmäisvaatimus: sähköpostiosoite ja salasana)
- Uudet käyttäjät voivat kirjautua sisään

#### Käyttäjätarina 10

_"Ylläpitäjänä haluan poistaa käyttäjiä, jotta käyttäjätiedot ovat ajantasaisia."_

**Hyväksymiskriteerit:**

- Ylläpitäjä voi poistaa käyttäjiä
- Poistettujen käyttäjien tiedot eivät ole enää saatavilla järjestelmässä

#### Käyttäjätarina 11

_"Ylläpitäjänä haluan muokata käyttäjiä, jotta käyttäjätiedot ovat ajantasaisia."_

**Hyväksymiskriteerit:**

- Ylläpitäjä voi muokata käyttäjiä
- Muokattujen käyttäjien tiedot ovat ajantasaisia

#### Käyttäjätarina 12

_"Ylläpitäjänä haluan lisätä käyttäjärooleja, jotta vain järjestelmään oikeutetut voivat käyttää sitä."_

**Hyväksymiskriteerit:**

- Ylläpitäjä voi lisätä käyttäjille rooleja
- Roolit muuttuvat sen mukaisesti
- Käyttäjien oikeudet määräytyvät heidän rooliensa perusteella

#### Käyttäjätarina 13

_"Ylläpitäjänä haluan muokata käyttäjärooleja, jotta vain järjestelmään oikeutetut voivat käyttää sitä."_

**Hyväksymiskriteerit:**

- Ylläpitäjä voi muokata käyttäjille annettuja rooleja
- Roolit muuttuvat sen mukaisesti
- Käyttäjien oikeudet määräytyvät heidän rooliensa perusteella

#### Käyttäjätarina 14

_"Ylläpitäjänä haluan poistaa käyttäjärooleja, jotta vain järjestelmään oikeutetut voivat käyttää sitä."_

**Hyväksymiskriteerit:**

- Ylläpitäjä voi poistaa käyttäjiltä rooleja
- Roolit muuttuvat sen mukaisesti
- Käyttäjien oikeudet määräytyvät heidän rooliensa perusteella

#### Käyttäjätarina 15

_"Ylläpitäjänä haluan nähdä järjestelmäraportit ja lokit ongelmatilanteissa."_

**Hyväksymiskriteerit:**

- Ylläpitäjä voi nähdä järjestelmäraportit
- Ylläpitäjä voi nähdä lokit

## Käyttöliittymä

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

## Tietokanta

Alla mallikuva tietokannasta, josta käy ilmi tietokannan sisältämät tiedot, taulujen väliset suhteet ja avainten määritykset.

![Database Diagram](https://raw.githubusercontent.com/marttyyriroskis/nat20-ticketguru/refs/heads/dev/images/ticketguru-db-diagram.png)

Lisäksi jokainen tietokannan taulu ja niiden attribuutit kuvataan tässä tietohakemistossa.

### permissions

permissions-taulu sisältää luvat. Roolilla voi olla monta lupaa, ja sama lupa voi kuulua useampaan eri rooliin. Siksi näillä on välitaulu, role_permissions.

| Kenttä | Tyyppi      | Kuvaus        |
| ------ | ----------- | ------------- |
| id     | int PK      | Luvan id      |
| title  | varchar(50) | Luvan otsikko |

### role_permissions

role_permissions on välitaulu roolien ja niiden lupien välillä. Sillä on siis monen suhde yhteen molempiin tauluihin.

| Kenttä        | Tyyppi | Kuvaus                                               |
| ------------- | ------ | ---------------------------------------------------- |
| role_id       | int PK | Viittaus rooliin [roles](#roles)-taulussa            |
| permission_id | int PK | Viittaus lupaan [permissions](#permissions)-taulussa |

### roles

roles-taulu määrittää kaikki mahdolliset käyttäjäroolit, joita käyttäjillä voi olla.

| Kenttä | Tyyppi      | Kuvaus      |
| ------ | ----------- | ----------- |
| id     | int PK      | Roolin id   |
| title  | varchar(50) | Roolin nimi |

### users

users-taulu sisältää käyttäjät. Yhdellä käyttäjällä on vain yksi rooli, mutta sama rooli voi kuulua useammalle käyttäjälle.

| Kenttä     | Tyyppi       | Kuvaus                                    |
| ---------- | ------------ | ----------------------------------------- |
| id         | int PK       | Käyttäjän id                              |
| email      | varchar(150) | Käyttäjän email                           |
| first_name | varchar(150) | Käyttäjän etunimi                         |
| last_name  | varchar(150) | Käyttäjän sukunimi                        |
| password   | varchar(250) | Salasanan hash(+salt)                     |
| role_id    | int FK       | Viittaus rooliin [roles](#roles)-taulussa |

### sales

sales-taulu kuvaa yhtä myyntitapahtumaa. Jokaisella myyntitapahtumalla on yksi myynnin hoitanut käyttäjä.

| Kenttä  | Tyyppi   | Kuvaus                                       |
| ------- | -------- | -------------------------------------------- |
| id      | int PK   | Myyntitapahtuman id                          |
| paid_at | datetime | Myyntihetki                                  |
| user_id | int FK   | Viittaus käyttäjään [users](#users)-taulussa |

### sales_tickets

sales_tickets -taulu yhdistää lipun ja sen myyntitapahtuman (sales) toisiinsa. Tauluun listataan kaikki yhdessä myyntitapahtumassa myydyt liput ja niiden myyntihinnat. Yhdessä myyntitapahtumassa voi olla monta lippua, mutta jokainen yksittäinen lippu voidaan myydä vain kerran.

| Kenttä    | Tyyppi | Kuvaus                                                |
| --------- | ------ | ----------------------------------------------------- |
| id        | int PK | Myyntirivin id                                        |
| ticket_id | int FK | Viittaus myytyyn lippuun [tickets](#tickets)-taulussa |
| sale_id   | int FK | Viittaus myyjään [users](#users)-taulussa             |
| price     | double | Lipusta maksettu hinta                                |

### tickets

tickets-taulu sisältää yksittäisiä lippuja eri tapahtumiin. Lipuilla voi olla monta eri lipputyyppiä, ja yksi lippu voidaan myydä vain kerran.

| Kenttä         | Tyyppi      | Kuvaus                                                         |
| -------------- | ----------- | -------------------------------------------------------------- |
| id             | int PK      | Lipun id                                                       |
| ticket_type_id | int FK      | Viittaus lipun tyyppiin [ticket_types](#ticket_types)-taulussa |
| barcode        | varchar(50) | Viivakoodi, jolla voidaan skannata lippu                       |
| used_at        | datetime    | Päivämäärä ja aika, jolloin lippu on merkitty käytetyksi       |

### ticket_types

ticket_types-taulu sisältää lipputyypit. Yhdessä tapahtumassa voi olla monta lipputyyppiä. Lipputyyppi määrittää aina vain yhtä lippua kerrallaan.

| Kenttä          | Tyyppi      | Kuvaus                                          |
| --------------- | ----------- | ----------------------------------------------- |
| id              | int PK      | Lipputyypin id                                  |
| name            | varchar(50) | Lipputyypin nimimerkki                          |
| retail_price    | double      | Lipputyypin OVH                                 |
| event_id        | int FK      | Viittaus tapahtumaan [events](#events)-taulussa |
| total_available | int         | Lippuja saatavilla                              |

### events

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

### venues

venues-taulu sisältää tapahtumapaikat. Yksi tapahtumapaikka on aina yhdessä postinumerossa, mutta yhdellä postinumerolla voi olla useampia tapahtumia.

| Kenttä  | Tyyppi        | Kuvaus                                                           |
| ------- | ------------- | ---------------------------------------------------------------- |
| id      | int PK        | tapahtumapaikan id                                               |
| name    | varchar(100)  | tapahtumapaikan nimi                                             |
| address | varchar(100)  | tapahtumapaikan osoite                                           |
| zipcode | varchar(5) FK | Viittaus tapahtumapaikan postiosoitteeseen [zipcodes](#zipcodes) |

### zipcodes

zipcodes-taulu sisältää tapahtumapaikkojen osoitteiden postinumerot ja kaupungit.

| Kenttä  | Tyyppi        | Kuvaus                         |
| ------- | ------------- | ------------------------------ |
| zipcode | varchar(5) PK | Postinumero                    |
| city    | varchar(100)  | Postinumeron mukainen kaupunki |

## Tekninen kuvaus

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

## Testaus

Tässä kohdin selvitetään, miten ohjelmiston oikea toiminta varmistetaan
testaamalla projektin aikana: millaisia testauksia tehdään ja missä vaiheessa.
Testauksen tarkemmat sisällöt ja testisuoritusten tulosten raportit kirjataan
erillisiin dokumentteihin.

Tänne kirjataan myös lopuksi järjestelmän tunnetut ongelmat, joita ei ole korjattu.

## Asennustiedot

Järjestelmän asennus on syytä dokumentoida kahdesta näkökulmasta:

- järjestelmän kehitysympäristö: miten järjestelmän kehitysympäristön saisi
  rakennettua johonkin toiseen koneeseen

- järjestelmän asentaminen tuotantoympäristöön: miten järjestelmän saisi
  asennettua johonkin uuteen ympäristöön.

Asennusohjeesta tulisi ainakin käydä ilmi, miten käytettävä tietokanta ja
käyttäjät tulee ohjelmistoa asentaessa määritellä (käytettävä tietokanta,
käyttäjätunnus, salasana, tietokannan luonti yms.).

## Käynnistys- ja käyttöohje

Tyypillisesti tässä riittää kertoa ohjelman käynnistykseen tarvittava URL sekä
mahdolliset kirjautumiseen tarvittavat tunnukset. Jos järjestelmän
käynnistämiseen tai käyttöön liittyy joitain muita toimenpiteitä tai toimintajärjestykseen liittyviä asioita, nekin kerrotaan tässä yhteydessä.

Usko tai älä, tulet tarvitsemaan tätä itsekin, kun tauon jälkeen palaat
järjestelmän pariin !
