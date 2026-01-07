select * from `user`;
select * from `folder`;
select * from `language`;
select * from `file`;
select * from `translation`;

show tables;

insert into `user`(user_id, email, username, password, created_at) values(1, "a@a.com", "hawon", "1111", current_timestamp());
insert into `folder`(folder_id, user_id, folder_name, created_at) values(1, 1, "project1", current_timestamp());
insert into `language`(lang_code, lang_name) values("en", "English");
insert into `language`(lang_code, lang_name) values("fr", "French");
insert into `language`(lang_code, lang_name) values("ko", "Korean");
insert into `file`(file_id, file_name, file_path, file_type, uploaded_at) values(1, "greeting.txt", "/uploads/greeting.txt", "text/plain", current_timestamp());
insert into `translation`(translation_id, folder_id, source_lang, target_lang, original_text, translated_text, file_id, created_at) values(1, 1, "en", "fr", "hello", "bonjour", 1, current_timestamp());

--

select user_id, email, username, password, created_at from `user` where email = "a@a.com";
SELECT translation_id, folder_id, source_lang, target_lang, original_text, translated_text, file_id, created_at FROM `translation` WHERE translation_id = 1;
SELECT lang_code, lang_name FROM `language` WHERE lang_code = "ko";
SELECT lang_code, lang_name FROM `language`;
SELECT folder_id, user_id, folder_name, created_at FROM `folder` WHERE folder_id = 1;
SELECT folder_id, user_id, folder_name, created_at FROM `folder` WHERE user_id = 1 ORDER BY created_at DESC;
SELECT file_id, file_name, file_path, file_type, uploaded_at FROM `file` WHERE file_id = 1;
SELECT file_id, file_name, file_path, file_type, uploaded_at FROM `file` ORDER BY uploaded_at DESC;
SELECT user_id, email, username, password, created_at FROM `user`;
SELECT folder_id, user_id, folder_name, created_at FROM `folder`;