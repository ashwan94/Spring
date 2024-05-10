package kr.or.nextit.springmvc.board;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class BoardVO {
	private int no;
	private String writer;
	private String title;
	private String content;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate modifyDate;

	private int hits;
}
