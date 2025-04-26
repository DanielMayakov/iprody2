package org.example;

import org.example.dao.StudentDAO;
import org.example.entity.Student;

public class Main {

    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();

        // Добавление студентов
        studentDAO.addStudent(new Student("Alice", "alice@example.com"));
        studentDAO.addStudent(new Student("Bob", "bob@example.com"));

        // Получение всех студентов
        System.out.println("Все студенты:");
        studentDAO.getAllStudents().forEach(System.out::println);

        // Обновление студента
        Student studentToUpdate = studentDAO.getStudentById(1L);
        if (studentToUpdate != null) {
            studentToUpdate.setName("Alice Updated");
            studentDAO.updateStudent(studentToUpdate);
        }

        // Удаление студента
        studentDAO.deleteStudent(2L);

        // Получение студента по ID
        System.out.println("Студент с ID=1:");
        System.out.println(studentDAO.getStudentById(1L));
    }
}
