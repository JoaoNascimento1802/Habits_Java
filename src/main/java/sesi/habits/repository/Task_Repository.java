package sesi.habits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sesi.habits.model.Task;

import java.time.LocalDate;
import java.util.List;

public interface Task_Repository extends JpaRepository<Task, Long> {

    List<Task> findByTaskDate(LocalDate date);

    List<Task> findByTaskDateBetween(LocalDate startDate, LocalDate endDate);
}
