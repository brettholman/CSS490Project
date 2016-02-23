drop table if exists PurchaseDetails;
drop table if exists Transactions;
drop table if exists UserRatings;
drop table if exists Users;
drop table if exists InventoryItems;
drop table if exists Category;


create table if not exists Users ( 
    userName        varchar(20) unique not null,
    fName 		    varchar(20) not null, 	
    lName 		    varchar(20) not null, 	
    email 		    varchar(50) not null,
    pass 		    varchar(20) not null,
    isAdmin         bool not null,
    lastLogin       date not null,
    accountCreated  date not null, 	
	primary key(userName)	
);

insert into Users values('test', 'test', 'test', 'test@test.net', 'pass', true, now(), now());

create table if not exists Category (
    id              integer auto_increment not null,
    categoryName    varchar(20) not null,
    primary key 	(id)
);

create table if not exists InventoryItems (
	id 			    integer auto_increment not null,
    title 		    varchar(50) not null, 
    quantity 	    integer not null, 
    price 		    double not null, 
    description     varchar(100),       # can be null
    categoryID	    integer not null, 	# foreign key 
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

create table if not exists Transactions (
    orderNumber     integer auto_increment not null,
    userName 		varchar(20) not null,
    purchaseDate    date not null,
    primary key     (orderNumber),
    foreign key     (userName) 
		references Users(userName)
        on delete cascade on update cascade
);

# Many-to-many relationship with Books and Transactions, 
# allows for more than one book to be purchased in a single transaction
create table if not exists PurchaseDetails (
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
create table if not exists UserRatings (
    id              integer auto_increment not null,
    userName        varchar(20) not null,
    bookID          integer not null,
    rating          integer not null,
    ratingDate      date not null,
    description     varchar(100),
    primary key     (id),
    foreign key     (userName)
        references Users(userName)
        on delete cascade on update cascade,
    foreign key     (bookID)
        references Books(id)
        on delete cascade on update cascade
);

delimiter $$
create trigger UserRatingsTrigger before insert on UserRatings
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