DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS category_transaction;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS names_accounts;
DROP TABLE IF EXISTS users;


CREATE TABLE users
(
    id       SERIAL       NOT NULL,
    name     VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (name)
);

CREATE TABLE names_accounts
(
    id SERIAL NOT NULL,
    category_account VARCHAR(255) UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE accounts
(
    id           SERIAL       NOT NULL,
    user_name    VARCHAR(255) NOT NULL,
    id_category_account INT NOT NULL,
    balance      INT          NOT NULL,
    date         DATE         NOT NULL,
    CONSTRAINT pK PRIMARY KEY (id),
    CONSTRAINT uniq_str UNIQUE (user_name, id_category_account),
    FOREIGN KEY (user_name) REFERENCES users (name),
    FOREIGN KEY (id_category_account) REFERENCES names_accounts(id)
);


CREATE TABLE category_transaction
(
    id            SERIAL NOT NULL,
    name_category VARCHAR(255) UNIQUE,
    PRIMARY KEY (id)
);


CREATE TABLE transactions
(
    id              SERIAL NOT NULL,
    uniq_account_id INT    NOT NULL,
    sum             INT    NOT NULL,
    date            DATE   NOT NULL,
    category        INT    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (uniq_account_id) REFERENCES accounts (id),
    FOREIGN KEY (category) REFERENCES category_transaction (id)
);

INSERT INTO users (name, password, email)
VALUES ('Максим', 'qwerty', '123@mail.ru');
INSERT INTO users(name, password, email)
VALUES ('Яна', 'qwerty', '321@mail.ru');
INSERT INTO users(name, password, email)
VALUES ('Александр', 'asdfg', 'asd@mail.ru');

SELECT *
FROM users;

INSERT INTO names_accounts (category_account)
VALUES ('Карта банковская'),
       ('Карта кредитная'),
       ('Наличные деньги'),
       ('Банковский счет'),
       ('Долги');

INSERT INTO accounts (user_name, id_category_account, balance, date)
VALUES ('Максим', 1 ,'+85000', CURRENT_DATE);
INSERT INTO accounts (user_name, id_category_account, balance, date)
VALUES ('Максим', 2, '+75000', CURRENT_DATE);
INSERT INTO accounts (user_name, id_category_account, balance, date)
VALUES ('Яна', 2, '+95000', CURRENT_DATE);
INSERT INTO accounts (user_name, id_category_account, balance, date)
VALUES ('Александр', 3, '+5000', CURRENT_DATE);

SELECT *
FROM accounts;

INSERT INTO category_transaction(name_category)
VALUES ('Питание'),
       ('Проезд'),
       ('Одежда'),
       ('Развлечения'),
       ('Автомобиль');

SELECT *
FROM category_transaction;

INSERT INTO transactions (uniq_account_id, sum, date, category)
VALUES ('1', '350', '05.01.2020', '1'),
       ('1', '150', '04.01.2020', '3'),
       ('2', '1400', '04.01.2020', '5'),
       ('3', '100', '04.01.2020', '1');

SELECT *
FROM transactions;

SELECT user_name             AS "Name",
       transactions.sum      AS "Summ",
       transactions.date     AS "Date",
       transactions.category AS "Category"
FROM transactions
         JOIN accounts a ON transactions.uniq_account_id = a.id
WHERE transactions.date = 'now'::date - 1;

SELECT name,
       balance
FROM accounts
         RIGHT JOIN users ON accounts.user_name = users.name






