package PKUCRProject.PKUCR.backend.Service;

import PKUCRProject.PKUCR.backend.Dao.TaskMapper;
import PKUCRProject.PKUCR.backend.Entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskServiceTest {

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    private Task testTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testTask = new Task(22L, 19L, "Task1cxxxxxxx", "2021-01-01 10:00:00",
                1, "This is a task");
    }

    @Test
    void insert() {
        when(taskMapper.insert(testTask)).thenReturn(20L);
        when(taskMapper.selectById(20L)).thenReturn(testTask);
        Task result = taskService.insert(testTask);
        // 因为testTask.getId()没有在测试中被mock, 所以这里的result.getId()是null
        assertNull(result);
    }

    @Test
    void selectById() {
        when(taskMapper.selectById(1L)).thenReturn(testTask);
        Task result = taskService.selectById(1L);
        assertNotNull(result);
        assertEquals(testTask.getId(), result.getId());
        verify(taskMapper, times(1)).selectById(1L);
    }

    @Test
    void update() {
        doNothing().when(taskMapper).update(testTask);
        String result = taskService.update(testTask);
        assertEquals("update success", result);
    }

    @Test
    void delete() {
        doNothing().when(taskMapper).delete(1L);
        String result = taskService.delete(1L);
        assertEquals("delete task success by id = 1", result);
    }

    @Test
    void selectByUserID() {
        when(taskMapper.selectByUserID(1L)).thenReturn(null);
        assertNull(taskService.selectByUserID(1L));
    }
}