-- INNER JOIN: Название книги, автор, идентификатор читателя и дата выдачи для книг со статусом "borrowed"
SELECT b.title AS book_title, b.author AS book_author, bb.reader_id, bb.borrow_date
FROM books b
INNER JOIN borrowed_books bb ON b.book_id = bb.book_id
WHERE bb.status = 'borrowed';

-- GROUP BY: Подсчет количества книг, выданных каждым читателем
SELECT r.name AS reader_name, COUNT(bb.book_id) AS books_borrowed
FROM readers r
LEFT JOIN borrowed_books bb ON r.reader_id = bb.reader_id
GROUP BY r.name;

-- HAVING: Читатели с количеством выданных книг больше двух
SELECT r.name AS reader_name, COUNT(bb.book_id) AS books_borrowed
FROM readers r
LEFT JOIN borrowed_books bb ON r.reader_id = bb.reader_id
GROUP BY r.name
HAVING COUNT(bb.book_id) > 2;

-- LEFT JOIN: Список всех книг и информация о выдаче для книг жанра "фантастика"
SELECT b.title AS book_title, b.genre AS book_genre, bb.borrow_date, bb.status
FROM books b
LEFT JOIN borrowed_books bb ON b.book_id = bb.book_id
WHERE b.genre = 'Science Fiction';
