package kr.or.nextit.springmvc.board;

import kr.or.nextit.springmvc.common.PaginationInfo;
import kr.or.nextit.springmvc.common.SearchVO;
import kr.or.nextit.springmvc.file.FileService;
import kr.or.nextit.springmvc.file.FileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/board/")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService service;
    private final FileService fileService;
    private final Path filePath = Paths.get("/Users","/na/workspace/spring/attach");

    @GetMapping("list")
    public String boardList(SearchVO vo, @RequestParam(value = "currentPageNo", defaultValue = "1") int currentPageNo, Model model) {
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(currentPageNo);
        paginationInfo.setRecordCountPerPage(3);
        paginationInfo.setPageSize(5);

        int totalCount = service.getBoardListCount(vo);
        paginationInfo.setTotalRecordCount(totalCount);
        // 페이징된 게시글 목록을 가져오기 위해
        vo.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
        vo.setLastRecordIndex(paginationInfo.getLastRecordIndex());

        List<BoardVO> list = service.getBoardList(vo);
        model.addAttribute("boards", list);
        model.addAttribute("pagination", paginationInfo);

        return "board/list";
    }

    @GetMapping("view")
    public String boardView(@RequestParam(value = "no") int searchNo, Model model) {
        BoardVO vo = service.getBoard(searchNo);
        List<FileVO> files = fileService.selectFileList(searchNo);
        model.addAttribute("board", vo);
        model.addAttribute("files", files);
        return "board/view";
    }

    @GetMapping("add")
    public String boardAddView() {
        return "board/add";
    }

    @PostMapping("add")
    public String boardAdd(BoardVO vo, Model model, List<MultipartFile> files) {
        /*
         * spring 은 첨부파일을 MultipartFile 이라는 객체를 지원한다
         * HTML 의 name 속성과 @PostMapping 의 parameter 는 동일해야한다
         * 첨부파일이 2개 이상일 경우 array 또는 List 로 받아온다
         * BoardVO 에 List<MultipartFile> files 로 필드를 선언하는것으로도 사용 가능하다
         * */
        int insertBoard = service.insertBoard(vo);

        // MultipartFile 을 FileVO 에 맞게 타입을 변환해줘야 DB 에 query 로 전달할 수 있다
        List<FileVO> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; // List 에 추가하지 않고 pass
            }

            // 사용자로부터 입력받은 file 정보를 DB 에 쏠 수 있게 FileVO 에 맞게 변환
            FileVO f = new FileVO();
            f.setFilePath(filePath.toString()); // field 로 깔아둔 filePath 로 첨부파일 저장 경로 지정
            f.setOriginalName(file.getOriginalFilename());
            f.setFileSize(file.getSize());
            String fileName = UUID.randomUUID().toString();
            f.setFileName(fileName); // 파일명이 중복될 경우 덮어씌워버리는데 이를 방지하기 위해
                                     // 중복되지 않게 파일명을 지정해줌
                                     // UUID 의 사용 이유

            // 나중에 UUID 로만 보여지는 파일에 실제 파일명을 붙여서 이름을 설정
            fileName = fileName + "_" + file.getOriginalFilename();
            fileList.add(f);

            // 첨부파일을 실제 경로에 등록
            // 첨부파일을 등록할 경로를 체크해서 없으면 해당 경로에 폴더 생성
            if(Files.notExists(filePath)){
                try {
                    Files.createDirectory(filePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            // 파일 경로와 파일명을 결합해 실제 물리적인 경로에 파일을 저장
            try {
                file.transferTo(Paths.get(filePath.toString(), fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        fileService.saveFiles(insertBoard, fileList);
        if (insertBoard > 0) {
            // 등록 성공
            return "redirect:/board/list";
        } else {
            // 등록 실패
            model.addAttribute("msg", "등록 실패");
            return "board/add";
        }
    }

    @GetMapping("update")
    public String boardUpdateView(int boardNo, Model model) {

        log.debug("Board Controller : {}", boardNo);
        BoardVO vo = service.getBoard(boardNo);
        log.debug("조회된 게시글 정보 : {}",vo);
        model.addAttribute("board", vo);
        return "board/update";
    }

    @PostMapping("update")
    public String boardUpdate(BoardVO vo, Model model) {
        int updated = service.updateBoard(vo);
        if (updated > 0) {
            // 등록 성공
            return "redirect:/board/list";
        } else {
            // 등록 실패
            model.addAttribute("msg", "수정 실패");
            return "/board/update";
        }
    }
    @GetMapping("delete")
    public String boardDelete(@RequestParam(value = "boardNo") int deleteNo, Model model) {
        int deletedBoard = service.deleteBoard(deleteNo);
        log.debug("Board Delete : {}", deleteNo);
        if (deletedBoard > 0) {
            return "redirect:/board/list";
        } else {
            model.addAttribute("msg", "삭제 실패");
            return "board/update";
        }
    }

    @GetMapping("download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("fileId") int fileId){
        FileVO file = fileService.selectFile(fileId);

        /* spring mvc 에서 지원하는 응답 객체(ResponseEntity)를 사용하면 편리하게 파일 전송 가능
        * => Builder Pattern 형식으로 data 를 저장할 수 있다
        * */
        FileUrlResource resource;
        Path path = Paths.get(filePath.toString(), file.getFileName() + "_" + file.getOriginalName());

        try {
            resource = new FileUrlResource(path.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String fileName = URLEncoder.encode(file.getOriginalName(), StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.getFileSize()))
                .body(resource);
    }

}
