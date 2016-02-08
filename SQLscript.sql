drop table if exists PurchaseDetails;
drop table if exists Transactions;
drop table if exists CustomerRatings;
drop table if exists Customers;
drop table if exists Books;
drop table if exists Category;


create table Customers (
	id 			    integer auto_increment not null, 
    pass 		    varchar(20) not null, 	
    fName 		    varchar(20) not null, 	
    lName 		    varchar(20) not null, 	
    email 		    varchar(50) not null,
	primary key(id)	
);

create table Category (
    id              integer auto_increment not null,
    categoryName    varchar(20) not null,
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
    foreign key     (categoryID) 
		references Category(id)
        on delete cascade on update cascade
);

delimiter $$ 
create trigger BooksTrigger before insert on Books  
    for each row 
    begin 
            if new.price <= 0
            then
                signal sqlstate '45000'
                set MESSAGE_TEXT = 'Book price must be greater than 0.';
            end if;
    end $$
delimiter ;

create table Transactions (
    orderNumber     integer auto_increment not null,
    customerId      integer not null,
    purchaseDate    date not null,
    primary key     (orderNumber),
    foreign key     (customerId) 
		references Customers(id)
        on delete cascade on update cascade
);

# Many-to-many relationship with Books and Transactions
create table PurchaseDetails (
    orderNumber     integer not null,
    bookID          integer not null,
    quantity        integer not null,
    primary key     (orderNumber, bookID),
    foreign key     (orderNumber) 
		references Transactions(orderNumber)
        on delete cascade on update cascade,
    foreign key     (bookID) 
		references Books(id)
        on delete cascade on update cascade
);

# need to discuss how we want to keep ratings. 
create table CustomerRatings (
    customerID      integer not null,
    bookID          integer not null,
    rating          integer not null,
    description     varchar(100),
    primary key     (customerID, bookID),
    foreign key     (customerID)
        references Customers(id)
        on delete cascade on update cascade,
    foreign key     (bookID)
        references Books(id)
        on delete cascade on update cascade
);

delimiter $$
create trigger CustomerRatingsTrigger before insert on CustomerRatings
		for each row 
        begin
				if new.rating < 0
                then 
						signal sqlstate '45000'
				set MESSAGE_TEXT = 'A rating can not be negative.';
				end if; 
                if new.rating > 5
                then 
						signal sqlstate '45000'
				set MESSAGE_TEXT = 'A raiting can not be more than 5.';
                end if;
		end $$
delimiter ;