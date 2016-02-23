drop table if exists PurchaseDetails;
drop table if exists Transactions;
drop table if exists Ratings;
drop table if exists Users;
drop table if exists InventoryItems;
drop table if exists Category;
    
create table if not exists Users ( 
    id              integer unique not null,
    userName        varchar(20) unique not null,
    fName 		    varchar(20) not null, 	
    lName 		    varchar(20) not null, 	
    email 		    varchar(50) not null,
    pass 		    varchar(20) not null,
    isAdmin         bool not null,
    lastLogin       date not null,
    accountCreated  date not null, 	
    primary key(id)	
);

insert into Users values(1,'test1', 'test1', 'test1', 'test@test.net', 'pass', true, curdate(), curdate());
insert into Users values(2,'test2', 'test2', 'test2', 'test@test.net', 'pass', true, curdate(), curdate());
insert into Users values(3,'test3', 'test3', 'test3', 'test3@test.net', 'pass', false, curdate(), curdate());

create table if not exists Category (
    id              integer auto_increment not null,
    categoryName    varchar(20) not null,
    primary key 	(id)
);

insert into Category values(1, 'Art');
insert into Category values(2, 'Biography');
insert into Category values(3, 'Childrens');
insert into Category values(4, 'Cookbooks');
insert into Category values(5, 'History');
insert into Category values(6, 'Fiction');
insert into Category values(7, 'Non-Fiction');
insert into Category values(8, 'Mystery');
insert into Category values(9, 'Romance');
insert into Category values(10, 'Sci-Fi');
insert into Category values(11, 'Young Adult');

create table if not exists InventoryItems (
    id 			    integer unique not null,
    title 		    varchar(50) not null, 
    quantity 	    integer not null, 
    price 		    double not null, 
    description     varchar(100),   # can be null
    categoryID	    integer not null, 	# foreign key 
    primary key     (id), 
    foreign key     (categoryID) 
    references      Category(id)
    on delete cascade on update cascade
);

delimiter $$ 
    create trigger InventoryItemsTrigger before insert on InventoryItems  
        for each row 
            begin 
            if new.price <= 0
            then
                signal sqlstate '45000'
                set MESSAGE_TEXT = 'Book price must be greater than 0.';
            end if;
            if new.quantity < 0
            then 
                signal sqlstate '45000'
                set MESSAGE_TEXT = 'There can not be a book with a quantity of less than 0';
            end if;
    end $$
delimiter ;

insert into InventoryItems values (1, 'book1', 20, 20.99, 'temp book one', 1);
insert into InventoryItems values (2, 'book2', 20, 21.99, 'temp book one', 2);
insert into InventoryItems values (3, 'book3', 20, 22.99, 'temp book one', 1);

create table if not exists Transactions (
    transactionNumber       integer not null,
    userID 		            integer not null,
    purchaseDate            date not null,
    primary key             (transactionNumber),
    foreign key             (userID) 
        references Users(id)
        on delete cascade on update cascade
);

insert into Transactions values(1, 1, curdate());
insert into Transactions values(2, 2, curdate());
insert into Transactions values(3, 1, curdate());

# Many-to-many relationship with Books and Transactions, 
# allows for more than one book to be purchased in a single transaction
create table if not exists PurchaseDetails (
    transactionNumber   integer not null,
    itemID  integer not null,
    quantity integer not null,
    primary key (transactionNumber, itemID),
    foreign key (transactionNumber) 
        references Transactions(transactionNumber)
        on delete cascade on update cascade,
    foreign key (itemID) 
        references InventoryItems(id)
        on delete cascade on update cascade
);

insert into PurchaseDetails values(1, 1, 3);
insert into PurchaseDetails values(2, 2, 3);
insert into PurchaseDetails values(2, 1, 4);
insert into PurchaseDetails values(3, 1, 3);


# made to keep track of all ratings. 
create table if not exists Ratings (
    id          integer unique not null,
    userID      integer not null,
    itemID      integer not null,
    rating      integer not null,
    ratingDate  date not null,
    description varchar(100),
    primary key (id),
    foreign key (userID)
        references Users(id)
        on delete cascade on update cascade,
    foreign key (itemID)
        references InventoryItems(id)
        on delete cascade on update cascade
);

delimiter $$
create trigger RatingsTrigger before insert on Ratings
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

insert into ratings values (1, 1, 1, 1, curdate(), 'super cool, dude');
insert into ratings values (2, 1, 1, 3, curdate(), 'super cool, dude');
insert into ratings values (3, 2, 2, 2, curdate(), 'super cool, dude');
insert into ratings values (4, 2, 3, 2, curdate(), 'super cool, dude');
insert into ratings values (5, 2, 3, 3, curdate(), 'super cool, dude');
insert into ratings values (6, 1, 1, 4, curdate(), 'super cool, dude');
insert into ratings values (7, 1, 2, 4, curdate(), 'super cool, dude');
insert into ratings values (8, 2, 3, 5, curdate(), 'super cool, dude');
