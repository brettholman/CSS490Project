drop table if exists Customers;
drop table if exists Category;
drop table if exists Books;
drop table if exists Transactions;
drop table if exists PurchaseDetails;


create table Customers (
	id 			    integer auto_increment, 
    pass 		    varchar(20), 	
    fName 		    varchar(20), 	
    lName 		    varchar(20), 	
    email 		    varchar(50),
	primary key(id)	
);

create table Category (
    id              integer auto_increment,
    categoryName    varchar(20),
    description     varchar(50),
    primary key 	(id)
);

create table Books (
	id 			    integer auto_increment not null,
    title 		    varchar(50) not null, 
    quantity 	    integer not null, 
    price 		    double not null, 
    categoryID	    integer not null, 	#foreign key 
    primary key     (id), 
    foreign key     (categoryID) references Category(id)
);

create table Transactions (
    orderNumber     integer auto_increment not null,
    customerId      integer not null,
    purchaseDate    date not null,
    primary key     (orderNumber),
    foreign key     (customerId) references Customers(id)
);

# Many-to-many relationship with Books and Transactions
create table PurchaseDetails (
    orderNumber     integer not null,
    bookID          integer not null,
    quantity        integer not null,
    primary key     (orderNumber, bookID),
    foreign key     (orderNumber) references Transactions(orderNumber),
    foreign key     (bookID) references Books(id)
);
