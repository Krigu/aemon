INSERT INTO PUBLISHER(ID_PUBLISHER, NAME) VALUES (1, 'SuperPublisher');
INSERT INTO MEDIA (NAME, MEDIA_TYPE) VALUES ('NHL Advanced', 'BOOK');
INSERT INTO BOOK (ID, ID_PUBLISHER) VALUES (1,1);
INSERT INTO AUTHOR (NAME) VALUES ('Kurt Sauer');
INSERT INTO BOOK_AUTHOR (BOOK_ID, AUTHOR_ID) VALUES (1,1);
INSERT INTO STUDENT (ID, FIRST_NAME, LAST_NAME) VALUES(1, 'Doktor', 'X');