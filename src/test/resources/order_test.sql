DELETE FROM USERS;
DELETE FROM ORDERS;
DELETE FROM PRODUCTS;
DELETE FROM ORDER_DETAIL;

INSERT INTO USERS (ID_USER,NAME,EMAIL,create_at) VALUES
(1,'Francisco Perez','francisco.perez@parrot.com','2022-03-02 18:48:43.000'),
(2,'Lorena Calvo','lorena.calvo@parrot.com','2022-03-09 18:48:43.000');
