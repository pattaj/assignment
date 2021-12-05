CREATE TABLE ACCOUNT (
  ACCOUNT_NUMBER INTEGER(9) NOT NULL,
  NAME VARCHAR(20) NOT NULL,
  PIN INTEGER(4) NOT NULL,
  OPENING_BALANCE DOUBLE(12),
  OVERDRAFT DOUBLE(12),  
  PRIMARY KEY (ACCOUNT_NUMBER)
);

CREATE TABLE BANK (
  TYPE VARCHAR(20) NOT NULL ,
  VALUE INTEGER NOT NULL ,
  AMOUNT INTEGER,
  PRIMARY KEY (TYPE)
);