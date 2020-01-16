-- db2 -td"^" -f create.clp

Create table p1.Customer (
    ID INT NOT NULL GENERATED ALWAYS AS IDENTITY  (START WITH 100, INCREMENT BY 1, NO CACHE), 
    Name VARCHAR(15) NOT NULL,
    Gender CHAR(1) NOT NULL,
    Age INT NOT NULL,
    Pin INT NOT NULL,
    CONSTRAINT CHK_Gender CHECK (Gender = 'M' OR Gender = 'F'),
    CONSTRAINT CHK_Age CHECK (Age >= 0),
    CONSTRAINT CHK_Pin CHECK (Pin >= 0)
)^

Create table p1.Account (
    Number INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1, NO CACHE),
    ID INT NOT NULL,
    Balance INT NOT NULL,
    Type CHAR(1) NOT NULL,
    Status CHAR(1) NOT NULL,
    CONSTRAINT CHK_Bal CHECK (Balance >= 0),
    CONSTRAINT CHK_Type CHECK (Type = 'C' OR Type = 'S'),
    CONSTRAINT CHK_Stat CHECK (Status = 'A' OR Status = 'I')
)^

create view CustomerBalance as 
    select p1.Customer.ID, Name, Age, Gender, sum(p1.Account.Balance) as TotalBalance 
    from p1.Customer left join p1.Account on p1.Customer.ID = p1.Account.ID where p1.Account.Status = 'A' 
    group by p1.Customer.ID, Name, Age, Gender^
