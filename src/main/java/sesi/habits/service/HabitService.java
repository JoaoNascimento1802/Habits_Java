package sesi.habits.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sesi.habits.dto.HabitCreationRequest;
import sesi.habits.dto.TaskDto;
import sesi.habits.habits_enum.TaskStatus;
import sesi.habits.model.Habit;
import sesi.habits.model.Task;
import sesi.habits.repository.Habits_Repository;
import sesi.habits.repository.Task_Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HabitService {

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
            taskRepository.saveAll(tasks);
        }

        savedHabit.setTasks(tasks);
        return savedHabit;
    }

    // MODIFICADO: Retorna uma lista de TaskDto
    @Transactional(readOnly = true) // Boa prática para métodos de leitura
    public List<TaskDto> getTasksForDate(LocalDate date) {
        return taskRepository.findByTaskDate(date).stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    // MODIFICADO: Retorna uma lista de TaskDto
    @Transactional(readOnly = true)
    public List<TaskDto> getTasksForPeriod(LocalDate startDate, LocalDate endDate) {
        return taskRepository.findByTaskDateBetween(startDate, endDate).stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    // MODIFICADO: Retorna um Optional<TaskDto>
    @Transactional
    public Optional<TaskDto> updateTaskStatus(Long taskId, TaskStatus newStatus) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setStatus(newStatus);
                    Task savedTask = taskRepository.save(task);
                    return new TaskDto(savedTask);
                });
    }

    // NOVO: Método para deletar um hábito (e suas tarefas em cascata)
    @Transactional
    public boolean deleteHabit(Long habitId) {
        if (habitRepository.existsById(habitId)) {
            habitRepository.deleteById(habitId);
            return true;
        }
        return false;
    }

    // NOVO: Método para deletar uma tarefa específica
    @Transactional
    public boolean deleteTask(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
            return true;
        }
        return false;
    }
}