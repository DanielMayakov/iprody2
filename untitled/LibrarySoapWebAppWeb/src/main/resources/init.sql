-- Создание таблицы "Книги"
CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    published_year INT CHECK (published_year > 0),
    genre VARCHAR(100)
);

-- Создание таблицы "Читатели"
CREATE TABLE readers (
    reader_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(15) UNIQUE
);

-- Создание таблицы "Выдача книг"
CREATE TABLE borrowed_books (
    borrow_id SERIAL PRIMARY KEY,
    book_id INT NOT NULL REFERENCES books(book_id),
    reader_id INT NOT NULL REFERENCES readers(reader_id),
    borrow_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(20) CHECK (status IN ('borrowed', 'returned'))
);

-- Создание индекса для поиска по названию книги
CREATE INDEX idx_books_title ON books(title);

-- Создание индекса для оптимизации запросов по статусу borrowed
CREATE INDEX idx_borrowed_books_status ON borrowed_books(status);
