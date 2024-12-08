package PKUCRProject.PKUCR.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PKUCRProject.PKUCR.backend.Dao.MaterialMapper;
import PKUCRProject.PKUCR.backend.Entity.Material;

import java.util.List;

@Service
public class MaterialService {
    
    @Autowired
    private MaterialMapper materialMapper;

    public List<Material> selectByCourseID(String courseID) {
        return materialMapper.selectByCourseID(courseID);
    }

    /**
     * 根据资料ID获取数据库中资料条目
     * @param materialID 资料ID
     * @return resource
     */
    public Material selectByID(Long id) {
        Material material = materialMapper.selectByID(id);
        if (material == null) {
            throw new RuntimeException("Material not found in the database");
        }
        return material;
    }

    public void insertMaterial(Material material) {
        materialMapper.insertMaterial(material);
    }

}
