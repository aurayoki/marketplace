# пока неполучилось подключить, запускайте через консоль воркбэнч
INSERT INTO `marketplace`. `users` (id, first_name, last_name, password, email, city_id, date, phone, user_img,active, unique_code) VALUES ('1', 'Георгий', 'Ширин', '1234', 'gg@google.com','1','22.07.1982','12345','null','false', 'null');

INSERT INTO `marketplace`.`roles` (id, name) VALUES ('1','User');

INSERT INTO `marketplace`.`users_roles`(user_id, role_id) VALUES ('1','1');

INSERT INTO `marketplace`.`cities` (id, name) VALUES ('1', 'Сочи');
INSERT INTO `marketplace`.`cities` (id, name) VALUES ('2', 'Москва');
INSERT INTO `marketplace`.`cities` (id, name) VALUES ('3', 'Ростов');
INSERT INTO `marketplace`.`cities` (id, name) VALUES ('4', 'Владивосток');
INSERT INTO `marketplace`.`cities` (id, name) VALUES ('5', 'Волгоград');

INSERT INTO `marketplace`.`good_category` (`id`, `name`) VALUES ('1', 'Транспорт');
INSERT INTO `marketplace`.`good_category` (`id`, `name`) VALUES ('2', 'Недвижимость');
INSERT INTO `marketplace`.`good_category` (`id`, `name`) VALUES ('3', 'Работа');
INSERT INTO `marketplace`.`good_category` (`id`, `name`) VALUES ('4', 'Бытовая электроника');
INSERT INTO `marketplace`.`good_category` (`id`, `name`) VALUES ('5', 'Личные вещи');



INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('1', 'Аудио и видео', '7');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('2', 'Игры, приставки и программы', '7');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('3', 'Настольные компьютеры', '7');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('4', 'Ноутбуки', '7');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('5', 'Оргтехника и расходника', '7');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('6', 'Планшеты и электронные книги', '7');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('7', 'Телефоны', '7');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('8', 'Товары для компьютера', '7');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('9', 'Фототехника', '7');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('10', 'Мотоциклы и мототехника', '1');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('11', 'Водный транспорт', '1');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('12', 'Квартиры', '2');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('13', 'Дома, дачи, коттеджи', '2');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('14', 'Вакансии', '3');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('15', 'Резюме', '3');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('16', 'Оргтехника и расходники', '4');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('17', 'Телефоны', '4');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('18', 'Часы и украшения', '5');
INSERT INTO `marketplace`.`good_subcategory` (`id`, `name`, `good_category_id`) VALUES ('19', 'Красота и здоровье', '5');

INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('1', 'Акустика', '8');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('2', 'Веб-камеры', '8');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('3', 'Джойстики и рули', '8');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('4', 'Клавиатуры и мыши', '8');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('5', 'Комплектующие', '8');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('6', 'Мониторы', '8');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('7', 'Переносные жесткие диски', '8');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('8', 'Сетевое оборудование', '8');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('9', 'ТВ-тюнеры', '8');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('10', 'Флешки и карты памяти', '8');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('11', 'Аксессуары', '8');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('12', 'Картинг', '10');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('13', 'Гидроциклы', '11');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('14', 'Квартиры в новостройках', '12');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('15', 'Комнаты', '13');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('16', 'Высший менеджмент', '14');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('17', 'Административная работа', '15');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('18', 'Принтеры', '16');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('19', 'Мобильные телефоны', '17');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('20', 'Бижутерия', '18');
INSERT INTO `marketplace`.`good_type` (`id`, `name`, `good_subcategory_id`) VALUES ('21', 'Косметика', '19');


INSERT INTO `marketplace`.`advertisement` (`id`, `description`, `name`, `price`, `good_category_id`, `good_subcategory_id`, `good_type_id`, `user_id`) VALUES ('1', 'В идеальном состоянии включался один раз', 'Компьютер AMD', '16000', '7', '8', '5', '1');
INSERT INTO `marketplace`.`advertisement` (`id`, `description`, `name`, `price`, `good_category_id`, `good_subcategory_id`, `good_type_id`, `user_id`) VALUES ('2', 'AM4', 'Материнская плата', '18000', '7', '8', '5', '1');
INSERT INTO `marketplace`.`advertisement` (`id`, `description`, `name`, `price`, `good_category_id`, `good_subcategory_id`, `good_type_id`, `user_id`) VALUES ('3', 'для игр и работы', 'Ноутбук lenovo', '23000', '7', '8', '5', '1');
INSERT INTO `marketplace`.`advertisement` (`id`, `description`, `name`, `price`, `good_category_id`, `good_subcategory_id`, `good_type_id`, `user_id`) VALUES ('4', 'Игровой', 'Монитор AOC CQ32', '21000', '7', '8', '5', '1');
INSERT INTO `marketplace`.`advertisement` (`id`, `description`, `name`, `price`, `good_category_id`, `good_subcategory_id`, `good_type_id`, `user_id`) VALUES ('4', 'Игровой', 'Монитор AOC CQ32', '21000', '7', '8', '5', '1');
INSERT INTO `marketplace`.`advertisement` (`id`, `description`, `name`, `price`, `good_category_id`, `good_subcategory_id`, `good_type_id`, `user_id`) VALUES ('5', 'В идеальном состоянии включался один раз', 'Компьютер AMD', '16000', '1', '10', '12', '1');
INSERT INTO `marketplace`.`advertisement` (`id`, `description`, `name`, `price`, `good_category_id`, `good_subcategory_id`, `good_type_id`, `user_id`) VALUES ('6', 'В идеальном состоянии включался один раз', 'Компьютер AMD', '16000', '2', '12', '14', '1');
INSERT INTO `marketplace`.`advertisement` (`id`, `description`, `name`, `price`, `good_category_id`, `good_subcategory_id`, `good_type_id`, `user_id`) VALUES ('7', 'В идеальном состоянии включался один раз', 'Компьютер AMD', '16000', '3', '15', '17', '1');
INSERT INTO `marketplace`.`advertisement` (`id`, `description`, `name`, `price`, `good_category_id`, `good_subcategory_id`, `good_type_id`, `user_id`) VALUES ('8', 'В идеальном состоянии включался один раз', 'Компьютер AMD', '16000', '4', '17', '19', '1');
INSERT INTO `marketplace`.`advertisement` (`id`, `description`, `name`, `price`, `good_category_id`, `good_subcategory_id`, `good_type_id`, `user_id`) VALUES ('9', 'В идеальном состоянии включался один раз', 'Компьютер AMD', '16000', '5', '19', '21', '1');




