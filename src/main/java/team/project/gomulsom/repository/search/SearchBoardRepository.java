package team.project.gomulsom.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team.project.gomulsom.entity.Board;

public interface SearchBoardRepository {

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable, String category);

}
