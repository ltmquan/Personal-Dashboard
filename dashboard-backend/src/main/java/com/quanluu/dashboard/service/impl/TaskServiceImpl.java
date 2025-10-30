package com.quanluu.dashboard.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quanluu.dashboard.constants.EntityType;
import com.quanluu.dashboard.constants.ErrorCode;
import com.quanluu.dashboard.exception.ResourceNotFoundException;
import com.quanluu.dashboard.model.Task;
import com.quanluu.dashboard.repository.TaskRepository;
import com.quanluu.dashboard.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	@Override
	public Optional<Task> getTaskById(Long id) {
		return taskRepository.findById(id);
	}

	@Override
	public Task createTask(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public Task updateTask(Long id, Task taskDetails) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Task not found with id: " + id, EntityType.TASK,
						ErrorCode.NOT_FOUND_WITH_ID));

		task.setTitle(taskDetails.getTitle());
		task.setDescription(taskDetails.getDescription());
		task.setCompleted(taskDetails.isCompleted());

		return taskRepository.save(task);
	}

	@Override
	public void deleteTask(Long id) {
		taskRepository.deleteById(id);
	}

	@Override
	public List<Task> getCompletedTasks() {
		return taskRepository.findByCompleted(true);
	}

	@Override
	public List<Task> getIncompleteTasks() {
		return taskRepository.findByCompleted(false);
	}

	@Override
	public List<Task> getTasksWithDeadlines() {
		return taskRepository.findByDueDateIsNotNullOrderByDueDateAsc();
	}

	@Override
	public List<Task> getOverdueTasks() {
		// Returns incomplete tasks where due date has passed
		// Used for dashboard "overdue" section and urgent task alerts
		return taskRepository
				.findByDueDateBeforeAndCompletedFalse(LocalDateTime.now());
	}

	@Override
	public List<Task> getTasksDueToday() {
		// Get tasks due within today's 24-hour period
		// Start at midnight, end at 23:59:59
		LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
		LocalDateTime endOfDay = startOfDay.plusDays(1);
		return taskRepository.findByDueDateBetween(startOfDay, endOfDay);
	}
}