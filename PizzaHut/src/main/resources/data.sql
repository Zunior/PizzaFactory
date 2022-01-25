DROP TABLE IF EXISTS PIZZA;

CREATE TABLE PIZZA (
	PI_SLUG VARCHAR(30) NOT NULL,
	PI_DATE TIMESTAMP NOT NULL,
	PI_NAME VARCHAR(255) NOT NULL,
	PI_PRICE INTEGER NOT NULL,
	PI_SIZE INTEGER NOT NULL,
	CONSTRAINT CONSTRAINT_4 PRIMARY KEY (PI_SLUG)
);
CREATE UNIQUE INDEX PRIMARY_KEY_4 ON PUBLIC.PIZZA (PI_SLUG);

INSERT INTO PIZZA (PI_SLUG, PI_DATE, PI_NAME, PI_PRICE, PI_SIZE) VALUES 
	('capricciosa', '2021-02-02T09:02:01Z', 'Capricciosa', '240', '23');
INSERT INTO PIZZA (PI_SLUG, PI_DATE, PI_NAME, PI_PRICE, PI_SIZE) VALUES 
	('calzone', '2021-02-03T12:45:23Z', 'Calzone', '280', '23');
INSERT INTO PIZZA (PI_SLUG, PI_DATE, PI_NAME, PI_PRICE, PI_SIZE) VALUES 
	('margherita', '2021-02-03T12:45:23Z', 'Margherita', '200', '18');