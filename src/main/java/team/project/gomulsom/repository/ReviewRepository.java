package team.project.gomulsom.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import team.project.gomulsom.entity.Board;
import team.project.gomulsom.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository <Review,Long> {

    // 리뷰 삭제
    @Modifying
    @Query("delete from Review r where r.board.bno = :bno")
    void deleteByBno(Long bno);

    @EntityGraph(attributePaths = {"writer"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByBoard(Board board);

}
