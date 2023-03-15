package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        /**
         * 엔티티 매니저 팩토리 생성
         * 1. persistence.xml 설정 정보 조회
         * 2. Persistence -> EMF 생성 => EMF 애플리케이션 전체에서 딱 한 번만 생성 후, 공유
         * 3. EMF -> EM 생성
         * 4. EM -> 트랜잭션 획득 => 엔티티 매니저는 DB 커넥션과 밀접한 관계 있음 -> 스레드 간 공유, 재사용 X
         */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();       // 엔티티 매니저 생성
        EntityTransaction tx = em.getTransaction();         // 트랜잭션 획득 -> 이때 엔티티 매니저가 커넥션 획득.

        try {
            tx.begin();                                     // 트랜잭션 시작
            logic(em);
            tx.commit();                                    // 트랜잭션 커밋
        } catch (Exception e) {
            tx.rollback();                                  // 트랜잭션 롤백
        } finally {
            em.close();                                     // 엔티티 매니저 종료 -> 필수
        }

        emf.close();                                        // 엔티티 매니저 팩토리 종료 -> 필수
    }

    // 비즈니스 로직
    private static void logic(EntityManager em) {

        // 비영속 상태의 데이터
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("교진");
        member.setAge(22);

        // 등록
        // 영속성 컨텍스트 관리 시작 -> em.persist -> 하나의 em에 하나의 영속성 컨텍스트 생성 됨.
        em.persist(member);

        // 수정
        member.setAge(27);

        // 한 건 조회
        Member findMember = em.find(Member.class, id);      // select * from member where id= 'id1'
        System.out.println("findMember = " + findMember.getUsername() + ", age = " + findMember.getAge());

        // 목록 조회 -> JPQL -> 디비 테이블 대상이 아닌, 객체를 대상으로 진행.
        TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class); // (JPQL, 반환 타입)
        List<Member> findMembers = query.getResultList();
        System.out.println("findMembers.size() = " + findMembers.size());

        // 삭제
        em.remove(member);
    }
}