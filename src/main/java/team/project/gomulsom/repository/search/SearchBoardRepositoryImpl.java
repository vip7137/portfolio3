package team.project.gomulsom.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import team.project.gomulsom.entity.*;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl(){
        super(Board.class);
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable, String category) {

        QBoard board = QBoard.board;
        QReview review = QReview.review;
        QMember member = QMember.member;
        QBoardImg boardImg = QBoardImg.boardImg;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(review).on(review.board.eq(board));
        jpqlQuery.leftJoin(boardImg).on(boardImg.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, boardImg, member);

        BooleanBuilder booleanBuilder=new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);
        BooleanExpression expressionCategory = board.category.eq(category);

        booleanBuilder.and(expression);
        booleanBuilder.and(expressionCategory);


        if (type != null){
            String[] typeArr = type.split("");

            // 검색 조건
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            // 제목은 t, 내용은 c, 작성자는 w로 설정
            for (String t:typeArr){
                switch (t){
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                    case "w" :
                        conditionBuilder.or(member.id.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);

        // order by
        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC: Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Board.class,"board");

            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        tuple.groupBy(board);

        // page 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        log.info(result);

        long count = tuple.fetchCount();

        log.info("COUNT: " +count);

        return new PageImpl<Object[]>(
               result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
               pageable,
               count);
    }
}
