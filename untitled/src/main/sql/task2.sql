-- Добавляем книги
INSERT INTO books (title, author, published_year, genre)
VALUES
('1984', 'George Orwell', 1949, 'Dystopian'),
('Brave New World', 'Aldous Huxley', 1932, 'Science Fiction'),
('To Kill a Mockingbird', 'Harper Lee', 1960, 'Fiction'),
('The Great Gatsby', 'F. Scott Fitzgerald', 1925, 'Classic');

-- Добавляем читателей
INSERT INTO readers (name, email, phone)
VALUES
('Alice Smith', 'alice@example.com', '1234567890'),
('Bob Johnson', 'bob@example.com', '0987654321'),
('Charlie Brown', 'charlie@example.com', NULL);

-- Добавляем записи выдачи книг
INSERT INTO borrowed_books (book_id, reader_id, borrow_date, status)
VALUES
(1, 1, '2023-10-01', 'borrowed'),
(2, 2, '2023-10-02', 'returned'),
(3, 1, '2023-10-03', 'borrowed');

-- Обновляем информацию о книге
UPDATE books
SET published_year = 1950
WHERE book_id = 1;

-- Обновляем статус выдачи книги
UPDATE borrowed_books
SET status = 'returned', return_date = '2023-10-10'
WHERE borrow_id = 1;

-- Удаляем читателя по reader_id
DELETE FROM readers
WHERE reader_id = 3;

-- Удаляем все записи выдачи для конкретной книги
DELETE FROM borrowed_books
WHERE book_id = 2;

-- Проверяем результаты
SELECT * FROM books;
SELECT * FROM readers;
SELECT * FROM borrowed_books;
