package team.project.gomulsom.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.project.gomulsom.entity.Board;
import team.project.gomulsom.entity.Member;
import team.project.gomulsom.entity.Review;

import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;



    //랜덤한 유저가 랜덤한 갯수의 댓글을 랜덤한 보드에 작성
    @Test
    public void insertReply(){
        IntStream.rangeClosed(1,200).forEach(i -> {
            Long bno = (long)(Math.random() * 100) +1;
            Long bid = ((long)(Math.random()*100) +1);
            Member member = Member.builder().id("user"+bid).build();
            Board board = Board.builder().bno(bno).build();

            Review review = Review.builder()
                    .text("테스트용 텍스트..."+i)
                    .board(board)
                    .writer(member)
                    .build();
            reviewRepository.save(review);
        });
    }
}
