package sesi.habits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sesi.habits.dto.HabitCreationRequest;
import sesi.habits.dto.TaskDto;
import sesi.habits.habits_enum.TaskStatus;
import sesi.habits.model.Habit;
import sesi.habits.service.HabitService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/habits")
@CrossOrigin(origins = "http://localhost:3000") // Permite requisições do front-end
public class HabitController {

    @Autowired
    private HabitService habitService;

    @PostMapping
    public ResponseEntity<Habit> createHabit(@RequestBody HabitCreationRequest request) {
        Habit newHabit = habitService.createHabitWithTasks(request);
        return new ResponseEntity<>(newHabit, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Habit>> getAllHabits() {
        List<Habit> habits = habitService.getAllHabits();
        return ResponseEntity.ok(habits);
    }

    @GetMapping("/tasks/by-date")
    public ResponseEntity<List<TaskDto>> getTasksByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TaskDto> tasks = habitService.getTasksForDate(date);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/tasks/by-period")
    public ResponseEntity<List<TaskDto>> getTasksByPeriod(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TaskDto> tasks = habitService.getTasksForPeriod(startDate, endDate);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/tasks/{taskId}/status")
    public ResponseEntity<TaskDto> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam("status") TaskStatus newStatus) {
        Optional<TaskDto> updatedTask = habitService.updateTaskStatus(taskId, newStatus);

        return updatedTask.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{habitId}")
    public ResponseEntity<Void> deleteHabit(@PathVariable Long habitId) {
        if (habitService.deleteHabit(habitId)) {
            return ResponseEntity.noContent().build(); // Sucesso, sem conteúdo
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        if (habitService.deleteTask(taskId)) {
            return ResponseEntity.noContent().build(); // Sucesso, sem conteúdo
        }
        return ResponseEntity.notFound().build();
    }
}