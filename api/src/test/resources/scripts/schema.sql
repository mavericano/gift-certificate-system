-- auto-generated definition
create table gift_certificate
(
    id               int auto_increment
        primary key,
    name             varchar(64)      not null,
    description      mediumtext       not null,
    price            double default 0 not null,
    duration         int    default 1 not null,
    create_date      datetime         not null,
    last_update_date datetime         not null
);

-- auto-generated definition
create table tag
(
    id   int auto_increment
        primary key,
    name varchar(64) not null
);

-- auto-generated definition
create table gift_certificate__tag
(
    certificate_id int not null,
    tag_id         int not null,
    primary key (certificate_id, tag_id),
    constraint gift_certificate__tag_gift_certificate_id_fk
        foreign key (certificate_id) references gift_certificate (id),
    constraint gift_certificate__tag_tag_id_fk
        foreign key (tag_id) references tag (id)
);

