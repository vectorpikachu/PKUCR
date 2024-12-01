package PKUCRProject.PKUCR.backend.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PKUCRProject.PKUCR.backend.Dao.ResourceMapper;
import PKUCRProject.PKUCR.backend.Entity.Resource;

@Service
public class ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 将课程资料插入数据库
     * @param resource
     */
    public String insertResource(main.java.PKUCRProject.PKUCR.backend.Entity.Resource resource) {
        resourceMapper.insertResource(resource);
        return "insert success with course " + resource.getCourseID() + "resourse" + resource.getID();
    }

    /**
     * 根据课程ID获取课程资料
     * @param courseID
     * @return 获取的课程资料列表
     */
    public List<Resource> getResourcesByCourseID(String courseID) {
        return resourceMapper.selectResourcesByCourseID(courseID);
    }

    /**
     * 根据资料ID获取对应的课程资料
     * @param materialID 用户ID
     * @return 对应的资料
     */
    public Resource getResourcesByUserID(Long userID) {
        return resourceMapper.selectResourcesByUserID(userID);
    }
}
