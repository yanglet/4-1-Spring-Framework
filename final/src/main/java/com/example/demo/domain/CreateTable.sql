create table member (
       member_id bigint not null auto_increment,
        email varchar(255),
        name varchar(255),
        password varchar(255),
        role varchar(255),
        primary key (member_id)
) engine=InnoDB

create table item (
       item_id bigint not null auto_increment,
        code varchar(255),
        name varchar(255),
        price int(11),
        quantity int(11),
        create_time datetime(6),
        primary key (item_id)
) engine=InnoDB

create table orders (
       order_id bigint not null auto_increment,
        member_id bigint not null,
        total_price int(11),
        create_time datetime(6),
        primary key (order_id),
        foreign key(member_id)
        references member(member_id) on update cascade
) engine=InnoDB

create table orderitem (
       orderitem_id bigint not null auto_increment,
        order_id bigint not null,
        item_id bigint not null,
        total_price int(11),
        quantity int(11),
        primary key (orderitem_id),
        foreign key(order_id)
        references orders(order_id) on update cascade,
        foreign key(item_id)
        references item(item_id) on update cascade
) engine=InnoDB