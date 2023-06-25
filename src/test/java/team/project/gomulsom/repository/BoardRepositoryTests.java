package team.project.gomulsom.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import team.project.gomulsom.entity.Board;
import team.project.gomulsom.entity.BoardImg;
import team.project.gomulsom.entity.Member;

import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardImgRepository boardImgRepository;

    // 1~50번까진 판매 카테고리, 51~100번까진 일반 카테고리
    @Test
    public void insertBoards(){
        IntStream.rangeClosed(1,50).forEach(i -> {

            Member member = Member.builder().id("user"+i).build();


            Board board = Board.builder().content("테스트용 게시..."+i)
                    .title("테스트용 게시...."+i)
                    .writer(member)
                    .category("0")
                    .price(10000)
                    .build();
            boardRepository.save(board);

            int count = (int)(Math.random()*5)+1;

            for(int j=0; j<count; j++){ //이미지 랜덤 생성
                BoardImg boardImg = BoardImg.builder()
                        .uuid(UUID.randomUUID().toString())
                        .board(board)
                        .imgName("test"+j+".jpg")
                        .build();
                boardImgRepository.save(boardImg);
            }

        });
        IntStream.rangeClosed(51,100).forEach(i -> {

            Member member = Member.builder().id("user"+i).build();


            Board board = Board.builder().content("테스트용 게시..."+i)
                    .title("테스트용 게시...."+i)
                    .writer(member)
                    .category("1")
                    .price(10000)
                    .build();
            boardRepository.save(board);

            int count = (int)(Math.random()*5)+1;

            for(int j=0; j<count; j++){ //이미지 랜덤 생성
                BoardImg boardImg = BoardImg.builder()
                        .uuid(UUID.randomUUID().toString())
                        .board(board)
                        .imgName("test"+j+".jpg")
                        .build();
                boardImgRepository.save(boardImg);
            }

        });
    }

}
