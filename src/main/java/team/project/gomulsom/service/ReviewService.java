package team.project.gomulsom.service;

import team.project.gomulsom.dto.ReviewDTO;
import team.project.gomulsom.entity.Board;
import team.project.gomulsom.entity.Member;
import team.project.gomulsom.entity.Review;

import java.util.List;

public interface ReviewService {

    // 리뷰 등록하기
    Long reviewRegister(ReviewDTO reviewDTO);

    // 리뷰 목록 불러오기
    List<ReviewDTO> getReviewList(Long bno);

    // 리뷰 수정
    void  reviewModify(ReviewDTO reviewDTO);

    // 리뷰 삭제
    void  reviewRemove(Long rno);

    // 리뷰 목록 처리
    default Review dtoToEntity(ReviewDTO reviewDTO) {

        Review review = Review.builder()
                .rno(reviewDTO.getRno())
                .board(Board.builder().bno(reviewDTO.getBno()).build())
                .text(reviewDTO.getText())
                .writer(Member.builder().id(reviewDTO.getWriter()).build())
                .build();

        return review;
    }

    default ReviewDTO entityToDTO(Review review) {

        ReviewDTO reviewDTO = ReviewDTO.builder()
                .rno(review.getRno())
                .text(review.getText())
                .writer(review.getWriter().getId())
                .bno(review.getBoard().getBno())
                .build();

        return reviewDTO;

    }
}
