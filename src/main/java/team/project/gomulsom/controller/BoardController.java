package team.project.gomulsom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.project.gomulsom.dto.BoardDTO;
import team.project.gomulsom.dto.PageRequestDTO;
import team.project.gomulsom.service.BoardService;

@Controller
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 메인 화면
    @GetMapping("/main")
    public void main(){

    }

    // 상품게시판 목록
    @GetMapping("/goodsboard_list")
    public void goodsboardlist(PageRequestDTO pageRequestDTO, Model model){

        model.addAttribute("result", boardService.getSearchList(pageRequestDTO));

    }

    // 상품게시판 상세, 수정
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping({"/goodsboard_view", "/goodsboard_modify"})
    public void goodsboardview(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model){

        BoardDTO boardDTO = boardService.get(bno);

        model.addAttribute("dto", boardDTO);

    }

    // 상품게시판 수정하기
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/modify")
    public String goodsboardmodify(BoardDTO boardDTO, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                                   RedirectAttributes redirectAttributes){

        boardService.modify(boardDTO);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/goodsboard_view";
    }

    // 상품게시판 등록
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/goodsboard_register")
    public void goodsboardregister(){

    }

    // 상품게시판 등록하기
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/goodsboard_register")
    public String goodsboardregister(BoardDTO boardDTO, RedirectAttributes redirectAttributes){

        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/goodsboard_list?category=0";
    }

    // 상품게시판 삭제하기
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/remove")
    public String remove(long bno){

        boardService.removeBoard(bno);

        return "redirect:/goodsboard_list?category=0";
    }

    // 자유 게시판 목록
    @GetMapping("/board_list")
    public void boardlist(PageRequestDTO pageRequestDTO, Model model){

        model.addAttribute("result", boardService.getSearchList(pageRequestDTO));
    }

    // 일반게시판 등록
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/board_register")
    public void boardregister(){

    }

    // 일반게시판 상세, 수정
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping({"/board_view", "/board_modify"})
    public void boardview(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model){

        BoardDTO boardDTO = boardService.get(bno);

        model.addAttribute("dto", boardDTO);

    }

    // 일반게시판 수정하기
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/boardmodify")
    public String boardmodify(BoardDTO boardDTO, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                              RedirectAttributes redirectAttributes){

        boardService.boardmodify(boardDTO);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board_view";
    }

    // 일반게시판 등록하기
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/board_register")
    public String boardregister(BoardDTO boardDTO, RedirectAttributes redirectAttributes){

        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board_list?category=1";

    }

    @PostMapping("/boardremove")
    public String boardremove(long bno, RedirectAttributes redirectAttributes){

        boardService.removeBoard(bno);

        return "redirect:/board_list?category=1";
    }

    // 상품게시판 목록
    @GetMapping("/service_center_list")
    public void service_center_list(PageRequestDTO pageRequestDTO, Model model){

        model.addAttribute("result", boardService.getSearchList(pageRequestDTO));
    }

}
