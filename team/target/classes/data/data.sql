create table if not exists team(
   team_id mediumint primary key auto_increment,
   name varchar(255) not null
);

insert into team(name) values('Manchester FC');
insert into team(name) values('London FC');
insert into team(name) values('Liverpool FC');
insert into team(name) values('Blackburn FC');
insert into team(name) values('Leeds FC');
insert into team(name) values('Ipswich FC');
insert into team(name) values('Bolton FC');

create table if not exists player(
    player_id mediumint  primary key auto_increment,
    name varchar(255) not null,
    team_id mediumint not null,
    foreign key(team_id) references team(team_id)
);