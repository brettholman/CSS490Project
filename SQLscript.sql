drop table if exists Customers;

create table Customers (
	id 			integer auto_increment, 
    pass 		varchar(20), 	
    fName 		varchar(20), 	
    lName 		varchar(20), 	
    email 		varchar(50),
	primary key(id)	
);

create table Books (
	id 			integer auto_increment,
    title 		varchar(50), 
    quantity 	integer, 
    price 		double, 
    category	varchar(20) 	#foreign key 
)
    

