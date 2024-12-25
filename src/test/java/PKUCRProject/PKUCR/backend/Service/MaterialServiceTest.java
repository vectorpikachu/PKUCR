package PKUCRProject.PKUCR.backend.Service;
import PKUCRProject.PKUCR.backend.Dao.MaterialMapper;
import PKUCRProject.PKUCR.backend.Entity.Material;
import PKUCRProject.PKUCR.backend.Service.MaterialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MaterialServiceTest {

    @Mock
    private MaterialMapper materialMapper;

    @InjectMocks
    private MaterialService materialService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // 初始化 Mockito 注解
    }

    @Test
    public void testSelectByCourseID() {
        // 模拟 MaterialMapper.selectByCourseID 方法的行为
        Material material1 = new Material(1L,1L, "Course 1", "Material 1", "path", "time");
        Material material2 = new Material(2L, 1L, "Course 1", "Material 2", "path", "time");
        List<Material> mockMaterials = Arrays.asList(material1, material2);
        when(materialMapper.selectByCourseID("Course 1")).thenReturn(mockMaterials);

        // 调用 Service 层的 selectByCourseID 方法并验证结果
        List<Material> materials = materialService.selectByCourseID("Course 1");
        assertEquals(2, materials.size());
        assertEquals("Material 1", materials.get(0).getFilename());
        assertEquals("Material 2", materials.get(1).getFilename());

        // 验证 MaterialMapper 的方法是否被调用
        verify(materialMapper, times(1)).selectByCourseID("Course 1");
    }

    @Test
    public void testSelectByID() {
        // 模拟 MaterialMapper.selectByID 方法的行为
        Material mockMaterial = new Material(1L, 1L, "Course 1", "Material 1", "path", "time");
        when(materialMapper.selectByID(1L)).thenReturn(mockMaterial);

        // 调用 Service 层的 selectByID 方法并验证结果
        Material material = materialService.selectByID(1L);
        assertNotNull(material);
        assertEquals("Material 1", material.getFilename());

        // 验证 MaterialMapper 的方法是否被调用
        verify(materialMapper, times(1)).selectByID(1L);
    }

    @Test
    public void testInsertMaterial() {
        // 创建一个 Material 对象
        Material material = new Material(1L, "Course 1", "Material 1", "path", "time");

        // 调用 Service 层的 insertMaterial 方法
        materialService.insertMaterial(material);

        // 验证 MaterialMapper 的 insertMaterial 方法是否被调用
        verify(materialMapper, times(1)).insertMaterial(material);
    }

    @Test
    public void testDeleteMaterial() {
        // 设置 ID
        Long materialId = 1L;

        // 调用 Service 层的 deleteMaterial 方法
        materialService.deleteMaterial(materialId);

        // 验证 MaterialMapper 的 deleteMaterial 方法是否被调用
        verify(materialMapper, times(1)).deleteMaterial(materialId);
    }
}