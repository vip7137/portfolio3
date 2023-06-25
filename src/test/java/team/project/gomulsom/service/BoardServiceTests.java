package team.project.gomulsom.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.project.gomulsom.dto.BoardDTO;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){

        BoardDTO dto = BoardDTO.builder()
                .title("게시글 작성 테스트")
                .category("1")
                .price(5000)
                .writer("user1")
                .content("게시글 작성 테스트 입니다.")
                .build();

        Long bno = boardService.register(dto);
    }
}
