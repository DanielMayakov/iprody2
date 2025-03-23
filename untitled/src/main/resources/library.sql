-- Создаем базу данных "Библиотека"
CREATE DATABASE library;

-- Используем созданную базу данных
c library;

-- Таблица "Книги" (books)
CREATE TABLE books (
    book_id SERIAL PRIMARY KEY, -- Уникальный идентификатор книги
    title VARCHAR(255) NOT NULL, -- Название книги
    author VARCHAR(255) NOT NULL, -- Автор книги
    published_year INT CHECK (published_year > 0), -- Год издания книги (положительное число)
    genre VARCHAR(100) -- Жанр книги
);

-- Таблица "Читатели" (readers)
CREATE TABLE readers (
    reader_id SERIAL PRIMARY KEY, -- Уникальный идентификатор читателя
    name VARCHAR(100) NOT NULL, -- Имя читателя
    email VARCHAR(255) UNIQUE NOT NULL, -- Электронная почта (уникальная)
    phone VARCHAR(15) UNIQUE -- Номер телефона (уникальный)
);

-- Таблица "Выдача книг" (borrowed_books)
CREATE TABLE borrowed_books (
    borrow_id SERIAL PRIMARY KEY, -- Уникальный идентификатор операции выдачи
    book_id INT NOT NULL, -- Идентификатор книги (внешний ключ)
    reader_id INT NOT NULL, -- Идентификатор читателя (внешний ключ)
    borrow_date DATE NOT NULL, -- Дата выдачи книги
    return_date DATE, -- Дата возврата книги
    status VARCHAR(20) CHECK (status IN ('borrowed', 'returned')), -- Статус выдачи книги

    -- Связь с таблицей books
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,

    -- Связь с таблицей readers
    CONSTRAINT fk_reader FOREIGN KEY (reader_id) REFERENCES readers(reader_id) ON DELETE CASCADE
);

-- Индексы для оптимизации запросов

-- Индекс для ускорения поиска книг по названию
CREATE INDEX idx_books_title ON books(title);

-- Индекс для оптимизации запросов по читателям, которые удерживают книги
CREATE INDEX idx_borrowed_books_status ON borrowed_books(reader_id) WHERE status = 'borrowed';

-- Дополнительно: Индекс для ускорения поиска по автору книг
CREATE INDEX idx_books_author ON books(author);
