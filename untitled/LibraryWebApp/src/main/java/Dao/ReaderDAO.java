package Dao;

import Model.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReaderDAO {
    private final List<Reader> readers = new ArrayList<>(); // Хранилище для данных о читателях

    // Добавить нового читателя
    public void addReader(Reader reader) {
        readers.add(reader);
        System.out.println("Reader added: " + reader);
    }

    // Получить список всех читателей
    public List<Reader> getAllReaders() {
        return new ArrayList<>(readers);
    }

    // Найти читателя по ID
    public Optional<Reader> findReaderById(int id) {
        return readers.stream()
                .filter(reader -> reader.getReaderId() == id)
                .findFirst();
    }

    // Удалить читателя по ID
    public boolean deleteReaderById(int id) {
        return readers.removeIf(reader -> reader.getReaderId() == id);
    }

    // Обновить данные о читателе
    public boolean updateReader(Reader updatedReader) {
        for (int i = 0; i < readers.size(); i++) {
            if (readers.get(i).getReaderId() == updatedReader.getReaderId()) {
                readers.set(i, updatedReader);
                return true;
            }
        }
        return false;
    }
}
