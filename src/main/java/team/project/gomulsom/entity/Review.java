package team.project.gomulsom.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"writer","board"})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer; // id 참조

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;  // bno 참조

    public void changeText(String text) {
        this.text = text;
    }
}
