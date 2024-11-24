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

}
