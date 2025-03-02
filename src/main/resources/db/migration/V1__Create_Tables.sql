-------------------------
-- 著者テーブル
-------------------------
CREATE TABLE author (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL
);

-------------------------
-- 書籍テーブル
-------------------------
CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    price INT NOT NULL CHECK (price >= 0),
    publication_status INT NOT NULL CHECK (publication_status IN (0, 1))
);

-------------------------
-- 書籍_著者テーブル
-------------------------
CREATE TABLE book_author (
    book_id INT REFERENCES book(id) ON DELETE CASCADE,
    author_id INT REFERENCES author(id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, author_id)
);