CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    published_year INT CHECK (published_year > 0),
    genre VARCHAR(100)
);

CREATE TABLE readers (
    reader_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(15) UNIQUE
);

CREATE TABLE borrowed_books (
    borrow_id SERIAL PRIMARY KEY,
    book_id INT NOT NULL REFERENCES books(book_id),
    reader_id INT NOT NULL REFERENCES readers(reader_id),
    borrow_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(20) CHECK (status IN ('borrowed', 'returned'))
);

-- Индексы
CREATE INDEX idx_books_title ON books (title);
CREATE INDEX idx_borrowed_books_status ON borrowed_books (status);
