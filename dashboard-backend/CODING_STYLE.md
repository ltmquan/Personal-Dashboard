# Backend Coding Style Guide

**Project:** Dashboard Backend (Java Spring Boot)
**Last Updated:** 2025-10-29

---

## Package Structure

```
com.quanluu.dashboard
├── constants/           # Enums and constant values
├── model/               # Entity classes
│   └── dto/             # Data Transfer Objects
├── repository/          # Data access layer (JpaRepository interfaces)
├── service/             # Business logic interfaces
│   └── impl/            # Service implementations
├── controller/          # REST API endpoints
└── config/              # Configuration classes (CORS, etc.)
```

---

## Model Layer

- All entities **must extend `BaseEntity`** for automatic timestamp management
- Use standard Lombok annotations: `@Data`, `@EqualsAndHashCode(callSuper = true)`, `@NoArgsConstructor`, `@AllArgsConstructor`

**Example:**
```java
@Entity
@Table(name = "tasks")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;
    private boolean completed = false;
    private LocalDateTime dueDate;
}
```

---

## Repository Layer

- Every `Entity.class` must have a corresponding `EntityRepository.class`
- Use common JPA-style function naming (e.g., `findByCompleted`, `findByDueDateBetween`)
- Use `@Query` annotation **only for tricky database queries** (complex joins, custom logic)

**Example:**
```java
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCompleted(boolean completed);
    List<Task> findByDueDateBetween(LocalDateTime start, LocalDateTime end);

    // Use @Query only when necessary
    @Query("SELECT DISTINCT c FROM Content c JOIN c.tags t WHERE t.name = :tagName")
    List<Content> findByTagName(@Param("tagName") String tagName);
}
```

---

## Service Layer

- Always have **interface then implementation** pattern
- For update operations: **always search for existing data using id first**, then set field attributes accordingly
- Use `@Transactional` for operations involving multiple repositories

**Example:**
```java
// Interface
public interface TaskService {
    Task updateTask(Long id, Task taskDetails);
}

// Implementation
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task updateTask(Long id, Task taskDetails) {
        // Always fetch existing entity first
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        // Set fields explicitly
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());

        return taskRepository.save(task);
    }
}
```

---

## Controller Layer

- API endpoints **always start with `/api/`**
- Include `@Tag` and `@Operation` annotations for API doc generation with Swagger UI

**Example:**
```java
@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "Task management API")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    @Operation(summary = "Get all tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new task")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update task")
    public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        return taskService.updateTask(id, taskDetails);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## Coding Style

### Comment Style

**All entity files must have comments at the top describing the entity:**
```java
/**
 * Task entity representing a todo item.
 *
 * Tasks can optionally have a due date and are displayed in both
 * the task list and calendar views.
 */
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {
    // ...
}
```

**Only comment when necessary:**
- Avoid comments where the code is self-explanatory
- Don't comment obvious methods (getters, setters, simple CRUD operations)
- Don't comment every field or parameter

**Comments on functions and code segments only when technical/complex:**
```java
@Override
public List<Task> getTasksDueToday() {
    // Get tasks due within today's 24-hour period
    // Start at midnight, end at 23:59:59
    LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
    LocalDateTime endOfDay = startOfDay.plusDays(1);
    return taskRepository.findByDueDateBetween(startOfDay, endOfDay);
}
```
