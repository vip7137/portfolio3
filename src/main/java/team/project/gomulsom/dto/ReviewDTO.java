package team.project.gomulsom.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private Long rno;

    private String text;

    private String writer;  // entity -> Member 의 id 참조

    private Long bno;  // entity -> Board 의 bno 참조
}
