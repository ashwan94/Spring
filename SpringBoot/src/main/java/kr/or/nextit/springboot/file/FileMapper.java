package kr.or.nextit.springboot.file;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    void saveFiles(List<FileVO> files);
    FileVO selectFileByBoardNo(long boardNo);
}
