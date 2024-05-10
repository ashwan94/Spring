package kr.or.nextit.springmvc.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final FileMapper mapper;

    public void saveFiles(int boardNo, List<FileVO> files) {
        if (files.isEmpty()) {
            return;
        }
        for (FileVO file : files) {
            file.setBoardNo(boardNo);
            // 각 파일에 대한 게시글 번호를 setter 로 입력해줘야 함
        }
        mapper.saveFiles(files);
    }

    public List<FileVO> selectFileList(int boardNo){
        return mapper.selectFileList(boardNo);
    }

    public FileVO selectFile(int id){
        return mapper.selectFile(id);
    }

}
