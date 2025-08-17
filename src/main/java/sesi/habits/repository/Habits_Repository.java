package sesi.habits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sesi.habits.model.Habit;

@Repository
public interface Habits_Repository extends JpaRepository<Habit, Long>{
}
