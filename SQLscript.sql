drop table if exists PurchaseDetails;
drop table if exists Transactions;
drop table if exists CustomerRatings;
drop table if exists Customers;
drop table if exists Books;
drop table if exists Category;


create table Customers ( 
    userName        varchar(20) unique not null,
    fName 		    varchar(20) not null, 	
    lName 		    varchar(20) not null, 	
    email 		    varchar(50) not null,
    pass 		    varchar(20) not null,
    lastLogin       date not null,
    accountCreated  date not null, 	
	primary key(userName)	
);

create table Category (
    id              integer auto_increment not null,
    categoryName    varchar(20) not null,
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
            if quantity < 0
            then 
                signal sqlstate '45000'
                set MESSAGE_TEXT = 'There can not be a book with a quantity of less than 0';
            end if;
    end $$
delimiter ;

create table Transactions (
    orderNumber     integer auto_increment not null,
    userName 		varchar(20) not null,
    purchaseDate    date not null,
    primary key     (orderNumber),
    foreign key     (userName) 
		references Customers(userName)
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

# made to keep track of all ratings. 
create table CustomerRatings (
    id              integer auto_increment not null,
    userName        varchar(20) not null,
    bookID          integer not null,
    rating          integer not null,
    ratingDate      date not null,
    description     varchar(100),
    primary key     (id),
    foreign key     (userName)
        references Customers(userName)
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