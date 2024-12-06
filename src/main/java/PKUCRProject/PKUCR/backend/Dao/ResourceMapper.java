package PKUCRProject.PKUCR.backend.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import PKUCRProject.PKUCR.backend.Entity.User;
import PKUCRProject.PKUCR.backend.Entity.Resource;

@Mapper
public interface ResourceMapper {

    /**
     * 将资料信息插入数据库
     * @param resource
     * @return 
     */
    @Insert("INSERT INTO resources (resourceID, courseID, userID, fileName, uploadTime, filePath) " +
            "VALUES (#{resourceID}, #{courseID}, #{userID}, #{fileName}, #{uploadTime}, #{filePath})")
    @Options(useGeneratedKeys = true, keyProperty = "resourceID", keyColumn = "resourceID")
    Long insertResource(Resource resource);
    /**
     * 根据资料ID在数据库中查找对应资料
     * @param courseID
     * @param resourceID
     * @return 资料类
     */
    @Select("SELECT resourceID, courseID, userID, fileName, filePath, uploadTime FROM resources WHERE courseID = #{courseID} AND resourceID = #{resourceID}")
    Resource selectResource(@Param("courseID") String courseID, @Param("resourceID") Long resourceID);

    //@Delete("delete from users where id = #{id}")
    //void delete(int id);
}
