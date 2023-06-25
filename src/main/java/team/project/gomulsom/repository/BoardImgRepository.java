package team.project.gomulsom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import team.project.gomulsom.entity.BoardImg;

public interface BoardImgRepository extends JpaRepository <BoardImg, Long> {

    // 이미지 삭제
    @Modifying
    @Query("delete from BoardImg bi where bi.board.bno = :bno")
    void deleteImgByBno(Long bno);
}
