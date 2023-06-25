package team.project.gomulsom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.gomulsom.dto.BoardDTO;
import team.project.gomulsom.dto.BoardImgDTO;
import team.project.gomulsom.dto.PageRequestDTO;
import team.project.gomulsom.dto.PageResultDTO;
import team.project.gomulsom.entity.Board;
import team.project.gomulsom.entity.BoardImg;
import team.project.gomulsom.repository.BoardImgRepository;
import team.project.gomulsom.repository.BoardRepository;
import team.project.gomulsom.repository.ReviewRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final BoardImgRepository boardImgRepository;
    private final ReviewRepository reviewRepository;

    // 글 등록하기
    @Transactional
    @Override
    public Long register(BoardDTO boardDTO) {

        Map<String, Object> entityMap = dtoToEntity(boardDTO);
        Board board = (Board) entityMap.get("board");
        List<BoardImg> boardImgList = (List<BoardImg>) entityMap.get("imgList");

        boardRepository.save(board);

        if (boardImgList != null && boardImgList.size() > 0) {
            boardImgList.forEach(boardImg -> {
                boardImgRepository.save(boardImg);
            });
        } else {
            BoardImg boardImg = BoardImg.builder()
                    .imgName("noimg.jpg")
                    .path("2022\\12\\29")
                    .uuid("610f148a-b0c1-4b96-b61d-20b646745ff3")
                    .board(board)
                    .build();

            boardImgRepository.save(boardImg);
        }

        return board.getBno();

    }

    // 상품게시물 수정
    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {

        Board board = boardRepository.findById(boardDTO.getBno()).get();

        if (board != null) {
            board.changeTitle(boardDTO.getTitle());
            board.changeContent(boardDTO.getContent());
        }
        boardRepository.save(board);

        boardImgRepository.deleteImgByBno(boardDTO.getBno());

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
            boardImgList.forEach(boardImg -> {
                boardImgRepository.save(boardImg);
            });
        } else {
            BoardImg boardImg = BoardImg.builder()
                    .imgName("noimg.jpg")
                    .path("2022\\12\\29")
                    .uuid("610f148a-b0c1-4b96-b61d-20b646745ff3")
                    .board(board)
                    .build();

            boardImgRepository.save(boardImg);
        }

    }
    // 일반게시물 수정
    @Transactional
    @Override
    public void boardmodify(BoardDTO boardDTO) {

        Board board = boardRepository.findById(boardDTO.getBno()).get();

        if (board != null) {
            board.changeTitle(boardDTO.getTitle());
            board.changeContent(boardDTO.getContent());
        }
        boardRepository.save(board);

        boardImgRepository.deleteImgByBno(boardDTO.getBno());

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
            boardImgList.forEach(boardImg -> {
                boardImgRepository.save(boardImg);
            });
        } else {
            BoardImg boardImg = BoardImg.builder()
                    .imgName("noimg.jpg")
                    .path("2022\\12\\29")
                    .uuid("610f148a-b0c1-4b96-b61d-20b646745ff3")
                    .board(board)
                    .build();

            boardImgRepository.save(boardImg);
        }

    }

    // 게시물 삭제
    @Transactional
    @Override
    public void removeBoard(Long bno) {

        boardImgRepository.deleteImgByBno(bno);
        reviewRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);

    }

    // 상품게시판 상세보기
    @Override
    public BoardDTO get(Long bno) {

        List<Object[]> result =boardRepository.getGoodsByBno(bno);
        Board board = (Board) result.get(0)[0];
        List<BoardImg> boardImgList = new ArrayList<>();
        result.forEach(arr -> {
            BoardImg boardImg = (BoardImg) arr[1];
            boardImgList.add(boardImg);
        });
        return entitiesToDTO(board, boardImgList);

    }

    // 게시물 검색
    @Override
    public PageResultDTO<BoardDTO, Object[]> getSearchList(PageRequestDTO requestDTO) {

        Function<Object[], BoardDTO> fn = (arr -> entitiesToDTO(
                (Board) arr[0],
                (List<BoardImg>) (Arrays.asList((BoardImg) arr[1]))
        ));

        Page<Object[]> result = boardRepository.searchPage(
                requestDTO.getType(),
                requestDTO.getKeyword(),
                requestDTO.getPageable(Sort.by("bno").descending()),
                requestDTO.getCategory()
        );

        return new PageResultDTO<>(result, fn);
    }

}
