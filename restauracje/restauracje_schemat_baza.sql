CREATE DATABASE restauracje;

\c restauracje

CREATE TABLE restauracja (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	city VARCHAR(50) NOT NULL,
	post_code VARCHAR(50) NOT NULL,
	street VARCHAR(50) NOT NULL,
	local_number VARCHAR(50) NOT NULL,
	web_page VARCHAR(100),
	phone VARCHAR(20),
	email VARCHAR(100)
);

INSERT INTO restauracja(name, city, post_code, street, local_number, web_page, phone, email) VALUES ('BOSKO Restauracja & Delikatesy','Przemysl','37-700','Wladycze','1','restauracjabosko.pl','574 433 858','info@restauracjabosko.pl');
INSERT INTO restauracja(name, city, post_code, street, local_number, web_page, phone, email) VALUES ('Burger Mistrz','Przemysl','37-700','Wybrzeze Pilsudzkiego','1','burgermistrz.pl','794 325 299','');
INSERT INTO restauracja(name, city, post_code, street, local_number, web_page, phone, email) VALUES ('Rutyna','Przemysl','37-700','Serbanska','1','rutynaprzemysl.pl','690 888 598','rutyna.przemysl@gmail.com');
INSERT INTO restauracja(name, city, post_code, street, local_number, web_page, phone, email) VALUES ('Pizzeria 105','Przemysl','37-700','Grunwaldzka','48A','105.pl','500 105 454','');
























/*Template : 
INSERT INTO restauracja(name, city, post_code, street, local_number, web_page, phone, email) VALUES ('','','','','','','','');
*/
