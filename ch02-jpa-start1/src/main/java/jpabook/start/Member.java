package jpabook.start;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity                     // 해당 어노테이션이 붙어있는 클래스를 테이블과 매핑한다고 JPA에게 알림.
@Table(name = "MEMBER")     // 엔티티 클래스에 매핑할 테이블 정보를 알려줌.
public class Member {

    @Id                     // 기본 키 매핑 -> id 필드를 테이블의 id 기본 키 ㅋ러럼에 매핑 식별자 필드
    @Column(name = "ID")    // 필드를 컬럼에 매용
    private String id;

    @Column(name = "NAME")
    private String username;

    // 매핑 정보가 없는 필드
    private Integer age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}