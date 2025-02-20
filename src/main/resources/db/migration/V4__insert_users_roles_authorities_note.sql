INSERT INTO users (email, password, enabled) VALUES
('admin@i.ua', '{noop}123', 'Y'),
('user@i.ua', '{noop}123', 'Y'),
('guest@i.ua', '{noop}123', 'Y'),
('user1@i.ua', '{bcrypt}$2a$12$/UmCcwf07UC8aTTW.rv3Z.qWBmKXv8HO6lNNpfSb5bOXVQhqIYm8e', 'Y'),
('user2@i.ua', '{noop}123', 'Y'),
('user31@i.ua', '{noop}123', 'Y'),
('user32@i.ua', '{noop}123', 'Y');

INSERT INTO role (name, reserved_by_system) VALUES
('ADMIN', 'Y'),
('USER', 'Y'),
('GUEST', 'Y'),
('STOREKEEPER', 'N'),
('GARDENER', 'N'),
('COOK', 'N');

INSERT INTO user_role (id_user, id_role) VALUES
('1', '1'),
('2', '2'),
('3', '3'),
('4', '4'),
('4', '2'),
('5', '5'),
('5', '2'),
('6', '6'),
('6', '2'),
('7', '6'),
('7', '2');

INSERT INTO note (title, content, id_role) VALUES
('Cook', 'Cook ...', '6'),
('Storekeeper', 'Storekeeper ...', '4'),
('Gardener', 'Gardener ...', '5');