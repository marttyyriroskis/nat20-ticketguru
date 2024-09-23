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

![GUI Diagram](https://hellmanstudios.fi/nat20-ticketguru-gui.png)

## Tietokanta

![Database Diagram](https://raw.githubusercontent.com/marttyyriroskis/nat20-ticketguru/refs/heads/dev/IMG_0327.png)

Järjestelmään säilöttävä ja siinä käsiteltävät tiedot ja niiden väliset suhteet
kuvataan käsitekaaviolla. Käsitemalliin sisältyy myös taulujen välisten viiteyhteyksien ja avainten
määritykset. Tietokanta kuvataan käyttäen jotain kuvausmenetelmää, joko ER-kaaviota ja UML-luokkakaaviota.

Lisäksi kukin järjestelmän tietoelementti ja sen attribuutit kuvataan
tietohakemistossa. Tietohakemisto tarkoittaa yksinkertaisesti vain jokaisen elementin (taulun) ja niiden
attribuuttien (kentät/sarakkeet) listausta ja lyhyttä kuvausta esim. tähän tyyliin:

> ### _Tilit_
>
> _Tilit-taulu sisältää käyttäjätilit. Käyttäjällä voi olla monta tiliä. Tili kuuluu aina vain yhdelle käyttäjälle._
>
> | Kenttä     | Tyyppi      | Kuvaus                                             |
> | ---------- | ----------- | -------------------------------------------------- |
> | id         | int PK      | Tilin id                                           |
> | nimimerkki | varchar(30) | Tilin nimimerkki                                   |
> | avatar     | int FK      | Tilin avatar, viittaus [avatar](#Avatar)-tauluun   |
> | kayttaja   | int FK      | Viittaus käyttäjään [käyttäjä](#Kayttaja)-taulussa |

### ticket_types

ticket_types-taulu sisältää lipputyypit. Yhdessä tapahtumassa voi olla monta lipputyyppiä. Lipputyyppi määrittää aina vain yhtä lippua kerrallaan.

| Kenttä          | Tyyppi      | Kuvaus                                          |
| --------------- | ----------- | ----------------------------------------------- |
| id              | int PK      | Lipputyypin id                                  |
| name            | varchar(50) | Lipputyypin nimimerkki                          |
| retail_price    | double      | Lipputyypin OVH                                 |
| event_id        | int FK      | Viittaus tapahtumaan [events](#events)-taulussa |
| total_available | int         | Lippuja saatavilla                              |

### role_permissions

role_permissions on välitaulu roolien ja niiden lupien välillä. Sillä on siis monen suhde yhteen molempiin tauluihin.

| Kenttä        | Tyyppi | Kuvaus                                               |
| ------------- | ------ | ---------------------------------------------------- |
| role_id       | int PK | Viittaus rooliin [roles](#roles)-taulussa            |
| permission_id | int PK | Viittaus lupaan [permissions](#permissions)-taulussa |

### permissions

permissions-taulu sisältää luvat. Roolilla voi olla monta lupaa, ja sama lupa voi kuulua useampaan eri rooliin. Siksi näillä on välitaulu, role_permissions.

| Kenttä | Tyyppi      | Kuvaus        |
| ------ | ----------- | ------------- |
| id     | int PK      | Luvan id      |
| title  | varchar(50) | Luvan otsikko |

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
