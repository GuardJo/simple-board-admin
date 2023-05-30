package com.guardjo.simpleboard.admin.domain;

import com.guardjo.simpleboard.admin.domain.constant.RoleType;
import com.guardjo.simpleboard.admin.domain.converter.RoleTypesConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode
@Entity
@Table(indexes = {
        @Index(name = "email", columnList = "email"),
        @Index(name = "name", columnList = "name")
})
public class AdminAccount extends MetaInfoData{
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

    @Convert(converter = RoleTypesConverter.class)
    @Column(nullable = false)
    private Set<RoleType> roleTypes = new LinkedHashSet<>();

    protected AdminAccount(String email, String name, Set<RoleType> roleTypes, String password, String creator) {
        this.email = email;
        this.name = name;
        this.roleTypes = roleTypes;
        this.password = password;
        this.creator = creator;
        this.modifier = creator;
    }

    protected AdminAccount() {

    }

    public static AdminAccount of(String email, String name, Set<RoleType> roleTypes, String password) {
        return new AdminAccount(email, name, roleTypes, password, email);
    }

    /**
     * OAuth2를 통해 외부로부터 회원을 받기 위해 생성자를 별도 파라미터로 추가
     * @param email 회원 메일 및 id
     * @param name 회원명
     * @param password 회원 비밀번호
     * @param creator 생성자
     * @return 회원 Entity
     */
    public static AdminAccount of(String email, String name, Set<RoleType> roleTypes, String password, String creator) {
        return new AdminAccount(email, name, roleTypes, password, creator);
    }
}
