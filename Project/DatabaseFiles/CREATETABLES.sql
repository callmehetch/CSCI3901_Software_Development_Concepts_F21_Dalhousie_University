drop table if exists persons;
create table persons(
personID int not null  auto_increment,
personName varchar(155) not null,
birthDate varchar(155) default null,
birthLocation varchar(155) default null,
deathDate varchar(155) default null,
deathLocation varchar(155) default null,
gender varchar(155) default null,
PersonOccupationID int default null,
primary key (personID)
);

drop table if exists occupations;
create table occupations(
occupationID int not null auto_increment,
occupationName varchar(155) not null,
primary key (occupationID)
);

drop table if exists personoccupation;
create table personoccupation(
personOccupationID int not null auto_increment,
occupationID int not null,
personID int not null,
primary key(personOccupationID),
foreign key(occupationID) references occupations(occupationID),
foreign key(personID) references persons(personID)
);

drop table if exists referencetable;
create table referencetable(
referenceTableID int not null auto_increment,
referencesOfPerson varchar(155) not null,
personID int not null,
primary key(referenceTableID),
foreign key(personID) references persons(personID)
);

drop table if exists notes;
create table notes(
notesID int not null auto_increment,
notesOfPerson varchar(155) not null,
primary key (notesID)
);

drop table if exists personnotes;
create table personnotes(
personnotesID int not null auto_increment,
notesID int not null,
personID int not null,
primary key(personnotesID),
foreign key(notesID) references notes(notesID),
foreign key(personID) references persons(personID)
);
-- -----------------------------------------------------------------------------------

drop table if exists media;
create table media(
mediaID int not null auto_increment,
fileLocation varchar(155) not null,
mediaDate date default null,
primary key(mediaID)
);

drop table if exists commonlocation;
create table commonlocation(
commonLocationID int not null auto_increment,
city varchar(155) default null,
province varchar(155) default null,
countryName varchar(155) default null,
primary key(commonLocationID)
);

drop table if exists medialocation;
create table medialocation(
mediaLocationID int not null auto_increment,
mediaID int not null,
commonLocationID int not null,
primary key(mediaLocationID),
foreign key (mediaID) references media(mediaID),
foreign key (commonLocationID) references commonlocation(commonLocationID)
);


drop table if exists tag;
create table tag(
tagID int not null auto_increment,
tagOfMedia varchar(155) not null,
primary key (tagID)
);

drop table if exists mediatags;
create table mediatags(
mediaTagsID int not null auto_increment,
tagID int not null,
mediaID int not null,
primary key(mediaTagsID),
foreign key(tagID) references tag(tagID),
foreign key(mediaID) references media(mediaID)
);

drop table if exists peopleinmedia;
create table peopleinmedia(
peopleinmediaID int not null auto_increment,
mediaID int not null,
personID int not null,
primary key(peopleinmediaID),
foreign key(mediaID) references media(mediaID),
foreign key(personID) references persons(personID)
);

drop table if exists relations;
create table relations(
relationID int not null auto_increment,
firstPartnerID int not null,
secondPartnerID int not null,
relation varchar(155) not null,
primary key(relationID),
foreign key(firstPartnerID) references persons(personID),
foreign key(secondPartnerID) references persons(personID)
);