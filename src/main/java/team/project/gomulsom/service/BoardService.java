package team.project.gomulsom.service;

import org.springframework.data.domain.Pageable;
import team.project.gomulsom.dto.BoardDTO;
import team.project.gomulsom.dto.BoardImgDTO;
import team.project.gomulsom.dto.PageRequestDTO;
import team.project.gomulsom.dto.PageResultDTO;
import team.project.gomulsom.entity.Board;
import team.project.gomulsom.entity.BoardImg;
import team.project.gomulsom.entity.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface BoardService {

    // 등록
    Long register(BoardDTO dto);

    // 상품게시판 상세보기
    BoardDTO get(Long bno);

    // 상품게시물 수정
    void modify(BoardDTO boardDTO);

    // 일반게시물 수정
    void boardmodify(BoardDTO boardDTO);

    // 상품게시물 삭제
    void removeBoard(Long bno);

    // 상품게시판 목록처리 ----------------------------------------------------------------------------------------------->
    PageResultDTO<BoardDTO, Object[]> getSearchList(PageRequestDTO requestDTO);

    default Map<String, Object> dtoToEntity(BoardDTO boardDTO){

        Map<String, Object> entityMap = new HashMap<>();

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .price(boardDTO.getPrice())
                .category(boardDTO.getCategory())
                .writer(Member.builder().id(boardDTO.getWriter()).build())
                .build();

        entityMap.put("board", board);

        List<BoardImgDTO> imgDTOList = boardDTO.getImgDTOList();

        if (imgDTOList != null && imgDTOList.size() > 0) {

            List<BoardImg> boardImgList = imgDTOList.stream().map(boardImgDTO -> {

                BoardImg boardImg = BoardImg.builder()
                        .path(boardImgDTO.getPath())
                        .uuid(boardImgDTO.getUuid())
                        .imgName(boardImgDTO.getImgName())
                        .board(board)
                        .build();
                return boardImg;
            }).collect(Collectors.toList());
            entityMap.put("imgList", boardImgList);
        }

        return entityMap;
    }

    default BoardDTO entitiesToDTO(Board board, List<BoardImg> boardImgs){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .price(board.getPrice())
                .category(board.getCategory())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writer(board.getWriter().getId())
                .build();

            List<BoardImgDTO> boardImgDTOList = boardImgs.stream().map(boardImg -> {
                return BoardImgDTO.builder().imgName(boardImg.getImgName())
                        .path(boardImg.getPath())
                        .uuid(boardImg.getUuid())
                        .build();
            }).collect(Collectors.toList());

            boardDTO.setImgDTOList(boardImgDTOList);

        return boardDTO;
    }
}
