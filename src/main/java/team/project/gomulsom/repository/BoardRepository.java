package team.project.gomulsom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.project.gomulsom.entity.Board;
import team.project.gomulsom.repository.search.SearchBoardRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {

    // 상품게시판 목록 불러오기(카테고리 : 판매)
    @Query("select b, bi from Board b " +
            "left outer join BoardImg bi on bi.board = b " +
            "where b.category = '0' group by b" )
    Page<Object[]> getGoodsListPage(Pageable pageable);

    // 상품게시판 상세보기 불러오기
    @Query("select b, bi from Board b " +
            "left outer join BoardImg bi on bi.board = b " +
            "left outer join Review r on r.board = b where b.bno = :bno group by bi")
    List<Object[]> getGoodsByBno(@Param("bno") Long bno);

    // 일반게시판 목록 불러오기(카테고리 : 일반)
    @Query("select b, coalesce(bi) from Board b " +
            "left outer join BoardImg bi on bi.board = b " + "" +
            "where b.category = '1' group by b" )
    Page<Object[]> getBoardPage(Pageable pageable);
}
