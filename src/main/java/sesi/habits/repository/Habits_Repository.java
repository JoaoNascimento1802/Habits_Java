package sesi.habits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sesi.habits.model.Habits_Model;

@Repository
public interface Habits_Interface extends JpaRepository<Habits_Model , Long>{
}
