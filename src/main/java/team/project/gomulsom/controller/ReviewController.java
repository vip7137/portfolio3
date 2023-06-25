package team.project.gomulsom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.project.gomulsom.dto.ReviewDTO;
import team.project.gomulsom.service.ReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 목록
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{bno}/all")
    public ResponseEntity<List<ReviewDTO>> getReviewList(@PathVariable("bno") Long bno) {

        List<ReviewDTO> reviewDTOList = reviewService.getReviewList(bno);

        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);

    }

    // 리뷰 등록
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/{bno}")
    public ResponseEntity<Long> reviewRegister(@RequestBody ReviewDTO reviewDTO) {

        Long rno = reviewService.reviewRegister(reviewDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);

    }

    // 리뷰 수정
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{bno}/{rno}")
    public ResponseEntity<Long> reviewModify(@PathVariable Long rno, @RequestBody ReviewDTO reviewDTO) {

        reviewService.reviewModify(reviewDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);

    }

    // 리뷰 삭제
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/{bno}/{rno}")
    public ResponseEntity<Long> reviewRemove(@PathVariable Long rno) {

        reviewService.reviewRemove(rno);

        return new ResponseEntity<>(rno, HttpStatus.OK);

    }
}
