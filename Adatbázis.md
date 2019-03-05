#### Táblák:

bejelentkezes(felhasznalo, jelszo)

felh_targyak(id, felhasznalo, targy_kod, tipus)

targy(kod, nev, minta_felev, kredit, van)

elofeltetel(id, targy_kod, feltetel_kod, felev)

targyak2019tavasz(kod)

vizsgakurzus2019tavasz(kod)


#### Lekérdezések

--létezik felhasználó
```
SELECT COUNT(*)
FROM bejelentkezes
WHEN @felhasznalonev = felhasznalo
```
--regisztráció
```
INSERT INTO bejelentkezes
VALUES (@felhasznalonev, @jelszo)
```
--bejelentkezes
```
SELECT (
CASE
  WHEN @jelszo = jelszo
  THEN 'igen'
  ELSE 'nem'
END
) AS valasz
FROM bejelentkezes
WHERE felhasznalo = @felhasznalonev
```

---------------------------------

--teljesített tárgy beszúrása
```
INSERT INTO felh_targyak (felhasznalo, targy_kod, tipus)
VALUES
(@felhasznalonev, @targykod, 'teljesitett')
```

--felvett tárgy beszúrása
```
INSERT INTO felh_targyak (felhasznalo, targy_kod, tipus)
VALUES
(@felhasznalonev, @targykod, 'jelenlegi')
```

--tárgy törlése
```
DELETE FROM felh_targyak
WHERE felhasznalo = @felhasznalonev AND targy_kod = @targykod
```

---------------------------------------

--felvehető tárgyak listája (egyelőre csak tárgykódok listája; vizsgakurzusok hiányoznak)
```
SELECT kod
FROM targy
WHERE van = 'van'
INTERSECT
SELECT kod
FROM targy AS t
JOIN elofeltetel AS e ON t.kod = e.targy_kod
WHERE e.felev = 'elozo'
	AND e.feltetel_kod NOT IN (
		SELECT targy_kod
		FROM felh_targy
		WHERE felhasznalo = @felhasznalonev
			AND tipus = 'teljesitett'
	)
INTERSECT
SELECT kod
FROM targy AS t
JOIN elofeltetel AS e ON t.kod = e.targy_kod
WHERE e.felev = 'mostani'
	AND e.feltetel_kod NOT IN (
		SELECT targy_kod
		FROM felh_targy
		WHERE felhasznalo = @felhasznalonev
			AND tipus = 'jelenlegi'
	)
;
```
