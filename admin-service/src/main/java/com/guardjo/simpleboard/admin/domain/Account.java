package com.guardjo.simpleboard.admin.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
@Table(indexes = {
        @Index(name = "email", columnList = "email"),
        @Index(name = "name", columnList = "name")
})
public class Account extends MetaInfoData{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long id;

    @Setter
    @Column(nullable = false)
    private String email;

    @Setter
    @Column(nullable = false, length = 100)
    private String name;

    @Setter
    @Column(nullable = false, length = 100)
    private String password;

    protected Account(String email, String name, String password, String creator) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.creator = creator;
        this.modifier = creator;
    }

    public Account() {

    }

    public static Account of(String email, String name, String password) {
        return new Account(email, name, password, null);
    }

    /**
     * OAuth2를 통해 외부로부터 회원을 받기 위해 생성자를 별도 파라미터로 추가
     * @param email 회원 메일 및 id
     * @param name 회원명
     * @param password 회원 비밀번호
     * @param creator 생성자
     * @return 회원 Entity
     */
    public static Account of(String email, String name, String password, String creator) {
        return new Account(email, name, password, creator);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return Objects.equals(email, account.email) && Objects.equals(name, account.name) && Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, password);
    }
}
