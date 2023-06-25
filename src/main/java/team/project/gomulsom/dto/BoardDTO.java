package team.project.gomulsom.dto;

import lombok.*;
import team.project.gomulsom.entity.Member;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;

    private String title;

    private String content;

    private int price;

    private String category;

    private String writer;  // entity -> Member 의 id 참조

    @Builder.Default
    private List<BoardImgDTO> imgDTOList = new ArrayList<>();

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
