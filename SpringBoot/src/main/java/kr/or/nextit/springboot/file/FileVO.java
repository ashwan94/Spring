package kr.or.nextit.springboot.file;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FileVO {
    private long id;
    private long boardNo;
    private String filePath;
    private String fileName;
    private String originalName;
    private long fileSize;
    private LocalDate createDate;
}
