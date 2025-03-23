-- Принципы ACID:
-- A (Atomicity): Транзакция либо полностью выполнится, либо полностью откатится.
-- C (Consistency): После выполнения транзакции база данных остается в согласованном состоянии.
-- I (Isolation): Транзакции выполняются независимо друг от друга.
-- D (Durability): После фиксации транзакции данные сохраняются даже при сбоях.

-- Нарушение атомарности:
BEGIN;
INSERT INTO books (title, author, published_year, genre)
VALUES ('Incomplete Book', 'Unknown Author', 2023, NULL);

-- Ошибка: Вторая операция не выполняется из-за нарушения ограничения NOT NULL.
INSERT INTO borrowed_books (book_id, reader_id, borrow_date, status)
VALUES ((SELECT book_id FROM books WHERE title = 'Incomplete Book'), NULL, '2023-10-20', 'borrowed');

-- Решение: Откат транзакции.
ROLLBACK;
