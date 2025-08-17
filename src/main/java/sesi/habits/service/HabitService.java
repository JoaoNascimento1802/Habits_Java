package sesi.habits.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sesi.habits.dto.HabitCreationRequest;
import sesi.habits.habits_enum.TaskStatus;
import sesi.habits.model.Habit;
import sesi.habits.model.Task;
import sesi.habits.repository.Habits_Repository;
import sesi.habits.repository.Task_Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Habits_Service {

    @Autowired
    private Habits_Repository habitRepository;

    @Autowired
    private Task_Repository taskRepository;

    @Transactional
    public Habit createHabitWithTasks(HabitCreationRequest request) {
        Habit newHabit = new Habit(request.getTitle(), request.getDescription());
        Habit savedHabit = habitRepository.save(newHabit);


        List<Task> tasks = new ArrayList<>();
        if (request.getDates() != null) {
            for (LocalDate date : request.getDates()) {
                Task task = new Task(date, savedHabit);
                tasks.add(task);
            }
            taskRepository.saveAll(tasks); // Salva todas as tarefas de uma vez
        }

        savedHabit.setTasks(tasks);
        return savedHabit;
    }

    public List<Task> getTasksForDate(LocalDate date) {
        return taskRepository.findByTaskDate(date);
    }

    public List<Task> getTasksForPeriod(LocalDate startDate, LocalDate endDate) {
        return taskRepository.findByTaskDateBetween(startDate, endDate);
    }

    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    @Transactional
    public Optional<Task> updateTaskStatus(Long taskId, TaskStatus newStatus) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setStatus(newStatus);
            taskRepository.save(task);
            return Optional.of(task);
        }
        return Optional.empty(); // Retorna vazio se a tarefa não for encontrada
    }
}

