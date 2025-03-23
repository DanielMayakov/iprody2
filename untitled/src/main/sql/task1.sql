-- Создаем базу данных "Библиотека"
CREATE DATABASE library;

-- Подключаемся к базе данных
c library;

-- Таблица "Книги"
CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    published_year INT CHECK (published_year > 0),
    genre VARCHAR(100)
);

-- Таблица "Читатели"
CREATE TABLE readers (
    reader_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(15) UNIQUE
);

-- Таблица "Выдача книг"
CREATE TABLE borrowed_books (
    borrow_id SERIAL PRIMARY KEY,
    book_id INT NOT NULL,
    reader_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(20) CHECK (status IN ('borrowed', 'returned')),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
    CONSTRAINT fk_reader FOREIGN KEY (reader_id) REFERENCES readers(reader_id) ON DELETE CASCADE
);

-- Индексы
CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_borrowed_books_status ON borrowed_books(reader_id) WHERE status = 'borrowed';
