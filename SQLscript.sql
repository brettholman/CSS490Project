use css490;

drop table if exists PurchaseDetails;
drop table if exists Transactions;
drop table if exists Ratings;
drop table if exists Users;
drop table if exists Roles;
drop table if exists InventoryItems;
drop table if exists Category;

create table if not exists Users (
    id              integer not null auto_increment,
    userName        varchar(20) unique not null,
    fName 		    varchar(20) not null,
    lName 		    varchar(20) not null,
    email 		    varchar(50) not null,
    pass 		    varchar(20) not null,
    lastLogin       date not null,
    accountCreated  date not null,
    primary key(id)
);

insert into Users(userName, fName, lName, email, pass, lastLogin, accountCreated) values
    ('test1', 'test1', 'test1', 'test@test.net', 'pass', curdate(), curdate()),
    ('test2', 'test2', 'test2', 'test@test.net', 'pass', curdate(), curdate()),
    ('test3', 'test3', 'test3', 'test3@test.net', 'pass', curdate(), curdate());

create table if not exists roles (
    id              integer unique not null auto_increment,
    userName        varchar(20) unique not null,
    roleName	    varchar(20) not null,
    primary key(id)
);

insert into roles (userName, roleName) values
	('test1', 'admin'),
	('test2', 'admin');

create table if not exists Category (
    id              integer unique not null auto_increment,
    categoryName    varchar(20) not null,
    primary key 	(id)
);

insert into Category (categoryName) values
	('Art'),
	('Biography'),
	('Childrens'),
	('Cookbooks'),
	('History'),
	('Fiction'),
	('Non-Fiction'),
	('Mystery'),
	('Romance'),
	('Sci-Fi'),
	('Fantasy'),
	('Horror'),
	('Young Adult');

create table if not exists InventoryItems (
    id 			    integer unique not null auto_increment,
    title 		    varchar(50) not null,
	author			varchar(50) not null,
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

insert into InventoryItems (title, author, quantity, price, description, categoryID) values
	('The Eye of the World', 'Robert Jordan', 0, 20.99, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 11),
	('The Great Hunt', 'Robert Jordan', 3, 21.99, 'Vestibulum pulvinar leo sit amet dui hendrerit mattis.', 11),
	('The Stand', 'Stephen King', 42, 21.99, 'Vivamus rutrum nibh quis volutpat lobortis.', 12),
	('The Day of the Triffids', 'John Wyndham', 17, 21.99, 'Proin quis nibh lobortis, porttitor tortor a, finibus massa.', 10),
	('The Civil War: A Narrative', 'Shelby Foote', 22, 21.99, 'Nullam in dolor ullamcorper, euismod tellus in, tristique ipsum.', 5),
	('The Rise and Fall of the Third Reich', 'William L. Shirer', 8, 21.99, 'Curabitur elementum ex blandit pharetra porta.', 5),
	('The Lion the Witch and the Wardrobe', 'C.S. Lewis', 12, 5.99, 'Ut id tellus venenatis magna mattis interdum.', 11),
	('Dune', 'Frank Herbert', 2, 19.99, 'Integer et metus vitae nisi bibendum faucibus.', 10),
	('The View from the Cherry Tree', 'Willo Davis Roberts', 9, 12.97, 'Etiam lobortis libero eu libero maximus, ultrices dignissim felis auctor.', 8),
	('Charlie and the Chocolate Factory', 'Roald Dahl', 48, 9.99, 'Duis tempor justo quis nisl pulvinar pulvinar.', 3),
	('The Hitchhiker''s Guide to the Galaxy', 'Douglas Adams', 42, 9.99, 'Quisque lobortis odio vitae est volutpat, eget aliquam ante aliquet.', 10),
	('Watership Down', 'Richard Adams', 16, 12.99, 'Vivamus a nulla bibendum, hendrerit augue nec, molestie justo.', 11),
	('The Aeronaut''s Windlass', 'Jim Butcher', 14, 29.99, 'Maecenas sollicitudin sem at lectus pellentesque, nec interdum velit laoreet.', 11),
	('Fuzzy Nation', 'John Scalzi', 27, 19.99, 'Donec id velit vel magna varius consectetur egestas vitae libero.', 10),
	('The Man who Folded Himself', 'David Gerrold', 13, 7.99, 'Nam auctor nunc sit amet blandit consequat.', 10),
	('Alas, Babylon', 'Pat Frank', 2, 7.99, 'Nullam non diam tristique eros condimentum auctor non vitae quam.', 10);
	
create table if not exists Transactions (
    transactionNumber       integer auto_increment not null,
    userID 		            integer not null,
    purchaseDate            date not null,
    totalCost               double not null,
    primary key             (transactionNumber),
    foreign key             (userID)
        references Users(id)
        on delete cascade on update cascade
);

insert into Transactions(userID, purchaseDate, totalCost) values
    (1, curdate(), 123),
    (2, curdate(), 555),
    (1, curdate(), 20),
    (1, curdate(), 123),
    (2, curdate(), 555),
    (1, curdate(), 20),
    (1, '2016-01-25', 20),
    (1, '2016-01-25', 203.99),
    (1, '2016-01-25', 230),
    (1, '2016-01-25', 20),
    (1, '2016-01-25', 230),
    (1, '2016-01-25', 20),
    (1, '2016-01-25', 20),
    (1, '2016-01-25', 203.87),
    (1, '2016-01-25', 20),
    (1, '2016-01-25', 20),
    (1, '2016-01-25', 20),
    (1, '2016-01-25', 20),
    (1, '2016-01-25', 20),
    (1, '2016-01-25', 20),
    (1, '2016-02-02', 20),
    (1, '2016-02-02', 20),
    (1, '2016-02-02', 20),
    (1, '2016-02-02', 20),
    (1, '2016-02-02', 20),
    (1, '2016-02-02', 20),
    (1, '2016-02-02', 20),
    (1, '2016-02-02', 20),
    (1, '2016-02-02', 20),
    (1, '2016-02-02', 20),
    (1, '2016-02-02', 20),
    (1, '2016-02-07', 20),
    (1, '2016-02-08', 240),
    (1, '2016-02-09', 20),
    (1, '2016-02-09', 20),
    (1, '2016-02-09', 20),
    (1, '2016-02-09', 20),
    (1, '2016-02-09', 20),
    (1, '2016-02-09', 20),
    (1, '2016-02-09', 20),
    (1, '2016-02-09', 20),
    (1, '2016-02-09', 20),
    (1, '2016-02-09', 20),
    (1, '2016-02-10', 20),
    (1, '2016-02-11', 20),
    (1, '2016-02-12', 20),
    (1, '2016-02-13', 20),
    (1, '2016-02-14', 20),
    (1, '2016-02-15', 60),
    (1, '2016-02-14', 20),
    (1, '2016-02-15', 60),
    (1, '2016-02-14', 20),
    (1, '2016-02-15', 60),
    (1, '2016-02-14', 20),
    (1, '2016-02-15', 60),
    (1, '2016-02-14', 20),
    (1, '2016-02-15', 60),
    (1, '2016-02-14', 20),
    (1, '2016-02-15', 60),
    (1, '2016-02-14', 20),
    (1, '2016-02-15', 60),
    (1, '2016-02-14', 20),
    (1, '2016-02-15', 60),
    (1, '2016-02-14', 20),
    (1, '2016-02-15', 60),
    (1, '2016-02-14', 20),
    (1, '2016-02-15', 60),
    (1, '2016-02-14', 20),
    (1, '2016-02-15', 60),
    (1, '2016-02-14', 20),
    (1, '2016-02-15', 60),
    (1, '2016-02-14', 20),
    (1, '2016-02-15', 60),
    (1, '2016-02-16', 70),
    (1, '2016-02-17', 70),
    (1, '2016-02-18', 700),
    (1, '2016-02-19', 700),
    (1, '2016-02-20', 700),
    (1, '2016-02-21', 700),
    (1, '2016-02-22', 700),
    (1, '2015-11-14', 700),
    (1, '2015-12-15', 700),
    (1, '2015-11-15', 700),
    (1, '2015-11-14', 700),
    (1, '2015-12-15', 700),
    (1, '2015-11-15', 700),
    (1, '2015-11-14', 700),
    (1, '2015-12-15', 700),
    (1, '2015-12-15', 700),
    (1, '2015-11-15', 700),
    (1, '2015-12-15', 700),
    (1, '2015-10-15', 700),
    (1, '2015-10-15', 700),
    (1, '2015-12-15', 700),
    (1, '2015-10-15', 700);

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

insert into PurchaseDetails values
    (1, 1, 3),
    (1, 2, 7),
    (2, 2, 3),
    (2, 5, 4),
    (3, 3, 4),
    (4, 5, 3),
    (5, 6, 2),
    (6, 1, 5),
    (6, 2, 4),
    (3, 1, 6),
    (4, 1, 3),
    (5, 5, 8),
    (2, 6, 3),
    (3, 4, 3),
    (6, 3, 4),
    (4, 2, 3);

# made to keep track of all ratings.
create table if not exists Ratings (
    id          integer not null auto_increment,
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

insert into ratings (id, userID, itemID, rating, ratingDate, description) values
    (1, 1, 1, 1, curdate(), 'super cool, dude'),
    (2, 1, 1, 3, curdate(), 'super cool, dude'),
    (3, 2, 2, 2, curdate(), 'super cool, dude'),
    (4, 2, 3, 2, curdate(), 'super cool, dude'),
    (5, 2, 3, 3, curdate(), 'super cool, dude'),
    (6, 1, 1, 4, curdate(), 'super cool, dude'),
    (7, 1, 2, 4, curdate(), 'super cool, dude'),
    (8, 2, 3, 5, curdate(), 'super cool, dude');
