package PKUCRProject.PKUCR.backend.Service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import PKUCRProject.PKUCR.backend.Dao.AllCoursesMapper;
import PKUCRProject.PKUCR.backend.Entity.BasicCourse;
import PKUCRProject.PKUCR.backend.Service.AllCourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class AllCourseServiceTest {

    @Mock
    private AllCoursesMapper allCoursesMapper;

    @InjectMocks
    private AllCourseService allCourseService;

    private BasicCourse course;

    @BeforeEach
    public void setUp() {
        // 初始化 Mockito 注解
        MockitoAnnotations.openMocks(this);

        // 创建一个 BasicCourse 对象
        course = new BasicCourse();
        course.setId(1L);
        course.setCourseID("CS101");
        course.setCourseName("Introduction to Computer Science");
    }

    @Test
    public void testInsert() {
        // 调用 insert 方法
        allCourseService.insert(course);

        // 验证 insert 方法是否被调用
        verify(allCoursesMapper, times(1)).insert(course);
    }

    @Test
    public void testSelectById() {
        // 模拟 selectById 方法的返回值
        when(allCoursesMapper.selectById(1L)).thenReturn(course);

        // 调用 selectById 方法
        BasicCourse result = allCourseService.selectById(1L);

        // 验证返回的结果是否与预期一致
        assertEquals(course, result);

        // 验证 selectById 方法是否被调用
        verify(allCoursesMapper, times(1)).selectById(1L);
    }

    @Test
    public void testSelectAll() {
        // 模拟 selectAll 方法的返回值
        List<BasicCourse> courseList = Arrays.asList(course);
        when(allCoursesMapper.selectAll()).thenReturn(courseList);

        // 调用 selectAll 方法
        List<BasicCourse> result = allCourseService.selectAll();

        // 验证返回的列表大小是否为1
        assertEquals(1, result.size());

        // 验证 selectAll 方法是否被调用
        verify(allCoursesMapper, times(1)).selectAll();
    }

    @Test
    public void testSelectByCourseID() {
        // 模拟 selectByCourseID 方法的返回值
        when(allCoursesMapper.selectByCourseID("CS101")).thenReturn(course);

        // 调用 selectByCourseID 方法
        BasicCourse result = allCourseService.selectByCourseID("CS101");

        // 验证返回的结果是否与预期一致
        assertEquals(course, result);

        // 验证 selectByCourseID 方法是否被调用
        verify(allCoursesMapper, times(1)).selectByCourseID("CS101");
    }
}
