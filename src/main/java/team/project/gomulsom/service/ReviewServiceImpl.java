package team.project.gomulsom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import team.project.gomulsom.dto.ReviewDTO;
import team.project.gomulsom.entity.Board;
import team.project.gomulsom.entity.Review;
import team.project.gomulsom.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    // 리뷰 등록
    @Override
    public Long reviewRegister(ReviewDTO reviewDTO) {

        Review review = dtoToEntity(reviewDTO);

        log.info(review.getWriter());
        reviewRepository.save(review);

        return review.getRno();
    }

    // 리뷰 목록 불러오기
    @Override
    public List<ReviewDTO> getReviewList(Long bno) {

        Board board = Board.builder().bno(bno).build();

        List<Review> result = reviewRepository.findByBoard(board);

        return result.stream().map(review -> entityToDTO(review)).collect(Collectors.toList());

    }

    // 리뷰 수정
    @Override
    public void reviewModify(ReviewDTO reviewDTO) {

        Optional<Review> result = reviewRepository.findById(reviewDTO.getRno());

        if (result.isPresent()) {

            Review review = result.get();
            review.changeText(reviewDTO.getText());

            reviewRepository.save(review);
        }

    }

    // 리뷰 삭제
    @Override
    public void reviewRemove(Long rno) {

        reviewRepository.deleteById(rno);

    }
}
