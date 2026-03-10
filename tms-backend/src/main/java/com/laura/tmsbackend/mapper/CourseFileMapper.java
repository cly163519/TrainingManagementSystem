package com.laura.tmsbackend.mapper;
import java.util.List;
import com.laura.tmsbackend.model.CourseFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseFileMapper {
    void insert(CourseFile courseFile);
    void deleteById(Long courseFileId);
    void update(CourseFile courseFile);
    CourseFile findById(Long courseFileId);
    List<CourseFile> findAll();
}
