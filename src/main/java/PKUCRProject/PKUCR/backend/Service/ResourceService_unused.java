/*
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
     * @return
     *
    public String insertResource(Resource resource) {
        resourceMapper.insertResource(resource);
        return "insert success with course " + resource.getCourseID() + "resourse" + resource.getResourceID();
    }

    /**
     * 根据课程ID获取课程资料
     * @param courseID
     * @return 
     */
    /* 
    public List<Resource> getResourcesByCourseID(String courseID) {
        return resourceMapper.selectResourcesByCourseID(courseID);
    }*

    **
     * 根据资料ID获取数据库中资料条目
     * @param courseID 课程ID
     * @param resourceID 资料ID
     * @return resource
     *
    public Resource getResource(String courseID, Long resourceID) {
        Resource resource = resourceMapper.selectResource(courseID, resourceID);
        if (resource == null) {
            throw new RuntimeException("Resource not found in the database");
        }
        return resource;
    }
}
*/