package team.project.gomulsom.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.project.gomulsom.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.fromSocial = :social and m.id = :id")
    Optional<Member> findByMemberId(String id, boolean social);

    Optional<Member> findById(String username);

    boolean existsByEmail(String email);

    boolean existsById(String id);
}
