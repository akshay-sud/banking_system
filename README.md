# banking_system
JDBC/SQL Banking System Project

SJSU CS 157A Project - JDBC boilerplate code provided by Professor (left ambiguous to prevent against plagiarism)

# Overview
Banking system project is designed to link database to Java program utilized for CLI (Command Line Interface) and allow user to access database without directly writing queries. 

p1.java             - CLI/menu code

BankingSystem.java  - JDBC SQL Methods utilized by p1.java to interact with the database

create.clp          - SQL table creation file containing commands used to create the two necessary tables for the database,        p1.Customer and p1.Account as well as a view CustomerBalance.

# Banking Methods

newCustomer - creates a new customer entry in the Customer table

openAccount - creates a new account entry in the Account table

closeAccount - sets account balance to 0 and sets account to I for Inactive

deposit - deposits set amount to account

withdraw - withdraws set amount from account, checks if user withdrawing is account owner

transfer - transfers set amount from account to account, checks if user transferring funds is owner of account losing money

accountSummary - gives summary of all accounts registered to a particular user

reportA - queries CustomerBalance view to find total balance of all accounts for each user

reportB - queries CustomerBalance view to find average total balance of all users between given age range
