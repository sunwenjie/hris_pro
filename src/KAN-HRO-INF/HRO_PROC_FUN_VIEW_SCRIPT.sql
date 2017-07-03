CREATE FUNCTION getCharIndex(codeChar char)
 RETURNS tinyint(4)
BEGIN
	DECLARE temp_index TINYINT(4);

	IF ASCII(codeChar) = ASCII('A') THEN
		SET temp_index = 1;
	ELSEIF ASCII(codeChar) = ASCII('B') THEN
		SET temp_index = 2;
	ELSEIF ASCII(codeChar) = ASCII('C') THEN
		SET temp_index = 3;
	ELSEIF ASCII(codeChar) = ASCII('D') THEN
		SET temp_index = 4;
	ELSEIF ASCII(codeChar) = ASCII('E') THEN
		SET temp_index = 5;
	ELSEIF ASCII(codeChar) = ASCII('F') THEN
		SET temp_index = 6;
	ELSEIF ASCII(codeChar) = ASCII('G') THEN
		SET temp_index = 7;
	ELSEIF ASCII(codeChar) = ASCII('H') THEN
		SET temp_index = 8;
	ELSEIF ASCII(codeChar) = ASCII('I') THEN
		SET temp_index = 9;
	ELSEIF ASCII(codeChar) = ASCII('J') THEN
		SET temp_index = 10;
	ELSEIF ASCII(codeChar) = ASCII('K') THEN
		SET temp_index = 11;
	ELSEIF ASCII(codeChar) = ASCII('L') THEN
		SET temp_index = 12;
	ELSEIF ASCII(codeChar) = ASCII('M') THEN
		SET temp_index = 13;
	ELSEIF ASCII(codeChar) = ASCII('N') THEN
		SET temp_index = 14;
	ELSEIF ASCII(codeChar) = ASCII('O') THEN
		SET temp_index = 15;
	ELSEIF ASCII(codeChar) = ASCII('P') THEN
		SET temp_index = 16;
	ELSEIF ASCII(codeChar) = ASCII('Q') THEN
		SET temp_index = 17;
	ELSEIF ASCII(codeChar) = ASCII('R') THEN
		SET temp_index = 18;
	ELSEIF ASCII(codeChar) = ASCII('S') THEN
		SET temp_index = 19;
	ELSEIF ASCII(codeChar) = ASCII('T') THEN
		SET temp_index = 20;
	ELSEIF ASCII(codeChar) = ASCII('U') THEN
		SET temp_index = 21;
	ELSEIF ASCII(codeChar) = ASCII('V') THEN
		SET temp_index = 22;
	ELSEIF ASCII(codeChar) = ASCII('W') THEN
		SET temp_index = 23;
	ELSEIF ASCII(codeChar) = ASCII('X') THEN
		SET temp_index = 24;
	ELSEIF ASCII(codeChar) = ASCII('Y') THEN
		SET temp_index = 25;
	ELSEIF ASCII(codeChar) = ASCII('Z') THEN
		SET temp_index = 26;

	ELSEIF ASCII(codeChar) = ASCII('a') THEN
		SET temp_index = 27;
	ELSEIF ASCII(codeChar) = ASCII('b') THEN
		SET temp_index = 28;
	ELSEIF ASCII(codeChar) = ASCII('c') THEN
		SET temp_index = 29;
	ELSEIF ASCII(codeChar) = ASCII('d') THEN
		SET temp_index = 30;
	ELSEIF ASCII(codeChar) = ASCII('e') THEN
		SET temp_index = 31;
	ELSEIF ASCII(codeChar) = ASCII('f') THEN
		SET temp_index = 32;
	ELSEIF ASCII(codeChar) = ASCII('g') THEN
		SET temp_index = 33;
	ELSEIF ASCII(codeChar) = ASCII('h') THEN
		SET temp_index = 34;
	ELSEIF ASCII(codeChar) = ASCII('i') THEN
		SET temp_index = 35;
	ELSEIF ASCII(codeChar) = ASCII('j') THEN
		SET temp_index = 36;
	ELSEIF ASCII(codeChar) = ASCII('k') THEN
		SET temp_index = 37;
	ELSEIF ASCII(codeChar) = ASCII('l') THEN
		SET temp_index = 38;
	ELSEIF ASCII(codeChar) = ASCII('m') THEN
		SET temp_index = 39;
	ELSEIF ASCII(codeChar) = ASCII('n') THEN
		SET temp_index = 40;
	ELSEIF ASCII(codeChar) = ASCII('o') THEN
		SET temp_index = 41;
	ELSEIF ASCII(codeChar) = ASCII('p') THEN
		SET temp_index = 42;
	ELSEIF ASCII(codeChar) = ASCII('q') THEN
		SET temp_index = 43;
	ELSEIF ASCII(codeChar) = ASCII('r') THEN
		SET temp_index = 44;
	ELSEIF ASCII(codeChar) = ASCII('s') THEN
		SET temp_index = 45;
	ELSEIF ASCII(codeChar) = ASCII('t') THEN
		SET temp_index = 46;
	ELSEIF ASCII(codeChar) = ASCII('u') THEN
		SET temp_index = 47;
	ELSEIF ASCII(codeChar) = ASCII('v') THEN
		SET temp_index = 48;
	ELSEIF ASCII(codeChar) = ASCII('w') THEN
		SET temp_index = 49;
	ELSEIF ASCII(codeChar) = ASCII('x') THEN
		SET temp_index = 50;
	ELSEIF ASCII(codeChar) = ASCII('y') THEN
		SET temp_index = 51;
	ELSEIF ASCII(codeChar) = ASCII('z') THEN
		SET temp_index = 52;

	ELSEIF ASCII(codeChar) = ASCII('0') THEN
		SET temp_index = 53;
	ELSEIF ASCII(codeChar) = ASCII('1') THEN
		SET temp_index = 54;
	ELSEIF ASCII(codeChar) = ASCII('2') THEN
		SET temp_index = 55;
	ELSEIF ASCII(codeChar) = ASCII('3') THEN
		SET temp_index = 56;
	ELSEIF ASCII(codeChar) = ASCII('4') THEN
		SET temp_index = 57;
	ELSEIF ASCII(codeChar) = ASCII('5') THEN
		SET temp_index = 58;
	ELSEIF ASCII(codeChar) = ASCII('6') THEN
		SET temp_index = 59;
	ELSEIF ASCII(codeChar) = ASCII('7') THEN
		SET temp_index = 60;
	ELSEIF ASCII(codeChar) = ASCII('8') THEN
		SET temp_index = 61;
	ELSEIF ASCII(codeChar) = ASCII('9') THEN
		SET temp_index = 62;

	ELSE
		SET temp_index = 0;
	END IF;

	RETURN temp_index;
END;


CREATE DEFINER = FUNCTION getIncrement(publicCode varchar(100), privateCode varchar(10))
 RETURNS double
BEGIN
	DECLARE temp_increment VARCHAR(12);

	SET temp_increment = '';
		
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 1, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 2, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 3, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 4, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 5, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 6, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 7, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 8, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 9, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 10, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 11, 1)), 1)) INTO temp_increment;
	SELECT CONCAT(temp_increment, SUBSTRING(publicCode, getCharIndex(SUBSTRING(privateCode, 12, 1)), 1)) INTO temp_increment;

	RETURN temp_increment;
END;





