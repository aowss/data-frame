CREATE TABLE passengers (Name VARCHAR(255), Age INT, Sex VARCHAR(255));
INSERT INTO passengers VALUES ('Braund, Mr. Owen Harris', 22, 'male');
INSERT INTO passengers VALUES ('Allen, Mr. William Henry', 35, 'male');
INSERT INTO passengers VALUES ('Bonnell, Miss. Elizabeth', 58, 'female');

SELECT column_name, data_type, character_maximum_length FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = 'passengers';
