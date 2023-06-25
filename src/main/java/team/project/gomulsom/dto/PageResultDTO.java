package team.project.gomulsom.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// Generic 과, Function 을 이용해서 확장성 용이하게 구현
// Entity, DTO, 함수를 미리 정의하지 않고, PageResultDTO 를 사용할 때 결정.
@Data
public class PageResultDTO<DTO, EN> {
    // DTO 리스트
    private List<DTO> dtoList;

    // 총 페이지 번호
    private int totalPage;                // 전체 페이지 수, Pageable 에서 구해줌.

    // 현재 페이지 번호
    private int page;                     // 현재 페이지 번호, 게시판 목록에 페이지 번호 누를 때 구해짐.
    // 목록 사이즈
    private int size;

    // 시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    // 이전, 다음
    private boolean prev, next;

    // 페이지 번호 목록
    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();              // 전체 페이지 수
        makePageList(result.getPageable());              // 페이지 구간을 구하는 함수 호출
    }

    private void makePageList(Pageable pageable) {

        this.page = pageable.getPageNumber() + 1;        // 0 부터 시작하므로 1 추가, 현재 페이지 번호
        this.size = pageable.getPageSize();              // 페이지당 글 수

        // temp end page
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;  // 현재 페이지 번호가 속한 구간의 끝 번호
        start = tempEnd - 9;                             // 현재 페이지 번호가 속한 구간의 시작 번호
        prev = start > 1;                                // 현재 페이지 번호가 1 보다 커야 prev 존재(true)
        end = totalPage > tempEnd ? tempEnd : totalPage; // 계산해서 구해놓은 끝 번호 보다 실제 마지막페이지 번호가 작으면, end 페이지를 실제 마지막 페이지로 교체
        next = totalPage > tempEnd;                      // 계산해서 구해놓은 끝 번호 보다 실제 마지막 페이지 번호가 커야 next 존재(true)

        // 페이지 번호를 start 부터 end 까지 하나씩 증가시켜서 list 에 저장
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
