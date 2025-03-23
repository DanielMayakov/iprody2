-- Начинаем транзакцию
BEGIN;

-- Добавляем новую книгу и запись выдачи книги
INSERT INTO books (title, author, published_year, genre)
VALUES ('Moby Dick', 'Herman Melville', 1851, 'Adventure');

INSERT INTO borrowed_books (book_id, reader_id, borrow_date, status)
VALUES ((SELECT book_id FROM books WHERE title = 'Moby Dick'), 1, '2023-10-15', 'borrowed');

-- Откатываем изменения
ROLLBACK;

-- Проверяем отсутствие изменений
SELECT * FROM books;
SELECT * FROM borrowed_books;

-- Начинаем новую транзакцию
BEGIN;

-- Добавляем новую книгу и запись выдачи книги
INSERT INTO books (title, author, published_year, genre)
VALUES ('Moby Dick', 'Herman Melville', 1851, 'Adventure');

INSERT INTO borrowed_books (book_id, reader_id, borrow_date, status)
VALUES ((SELECT book_id FROM books WHERE title = 'Moby Dick'), 1, '2023-10-15', 'borrowed');

-- Фиксируем изменения
COMMIT;

-- Проверяем наличие изменений
SELECT * FROM books;
SELECT * FROM borrowed_books;
