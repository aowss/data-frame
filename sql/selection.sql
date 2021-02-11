CREATE TABLE passengers (Name VARCHAR(255), Age INT, Sex VARCHAR(255));
INSERT INTO passengers VALUES ('Braund, Mr. Owen Harris', 22, 'male');
INSERT INTO passengers VALUES ('Allen, Mr. William Henry', 35, 'male');
INSERT INTO passengers VALUES ('Bonnell, Miss. Elizabeth', 58, 'female');

SELECT Age, Sex FROM passengers;

SELECT * FROM passengers WHERE Age > 30;
SELECT * FROM passengers WHERE Age > 30 AND Sex = 'male';

SELECT Name, Sex FROM passengers WHERE Age > 30;
SELECT Name FROM passengers WHERE Age > 30 AND Sex = 'male';