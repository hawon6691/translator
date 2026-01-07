create table `user`(
	user_id bigint primary key auto_increment,
    username varchar(50) unique not null,
    email varchar(100) unique not null,
    password varchar(255) not null,
    created_at datetime default current_timestamp
);

create table `folder`(
    folder_id bigint primary key auto_increment,
    user_id bigint,
    folder_name varchar(100) not null,
    created_at datetime default current_timestamp,
    foreign key (user_id) references user(user_id)
);

create table `language`(
    lang_code char(5) primary key,
    lang_name varchar(50) not null
);

create table `file`(
    file_id bigint primary key auto_increment,
    file_name varchar(255) not null,
    file_path varchar(500) not null,
    file_type varchar(20) not null,
    uploaded_at datetime default current_timestamp
);

create table `translation`(
    translation_id bigint primary key auto_increment,
    folder_id bigint,
    source_lang char(5),
    target_lang char(5),
    original_text text null,
    translated_text text null,
    file_id bigint null,
    created_at datetime default current_timestamp,
    foreign key (folder_id) references folder(folder_id),
    foreign key (source_lang) references language(lang_code),
    foreign key (target_lang) references language(lang_code),
    foreign key (file_id) references file(file_id)
);