#### Táblák:

bejelentkezes(**felhasznalo**, jelszo)

felh_targyak(**id**, felhasznalo, targy_kod, tipus)

targy(**kod**, nev, minta_felev, kredit)

elofeltetel(**id**, targy_kod, feltetel_kod, felev)


#### Lekérdezések

--létezik felhasználó
```
SELECT COUNT(*)
FROM bejelentkezes
WHERE felhasznalo = 'FELHASZNALONEV'
```
--regisztráció
```
INSERT INTO bejelentkezes
VALUES ('FELHASZNALONEV', 'JELSZO')
```
--bejelentkezes
```
SELECT (
CASE
  WHEN 'JELSZO' = jelszo
  THEN 'igen'
  ELSE 'nem'
END
) AS valasz
FROM bejelentkezes
WHERE felhasznalo = 'FELHASZNALONEV'
```

---------------------------------

--teljesített tárgy beszúrása
```
INSERT INTO felh_targyak (felhasznalo, targy_kod, tipus)
VALUES
('FELHASZNALONEV', 'TARGYKOD', 'teljesitett')
```

--felvett tárgy beszúrása
```
INSERT INTO felh_targyak (felhasznalo, targy_kod, tipus)
VALUES
('FELHASZNALONEV, 'TARGYKOD', 'jelenlegi')
```

--tárgy törlése
```
DELETE FROM felh_targyak
WHERE felhasznalo = 'FELHASZNALONEV' AND targy_kod = 'TARGYKOD'
```

---------------------------------------

--felvehető tárgyak listája
```
SELECT kod, nev, minta_felev, kredit
FROM targy
WHERE kod NOT IN
(

SELECT kod
FROM targy AS t
JOIN elofeltetel AS e ON t.kod = e.targy_kod
WHERE e.felev = 'elozo'
	AND e.feltetel_kod NOT IN (
		SELECT targy_kod
		FROM felh_targyak
		WHERE felhasznalo = 'FELHASZNALONEV'
			AND tipus = 'teljesitett'
	)
	
UNION

SELECT kod
FROM targy AS t
JOIN elofeltetel AS e ON t.kod = e.targy_kod
WHERE e.felev = 'mostani'
	AND e.feltetel_kod NOT IN (
		SELECT targy_kod
		FROM felh_targyak
		WHERE felhasznalo = 'FELHASZNALONEV'
			AND tipus = 'jelenlegi'
	)
	
)
;
```
