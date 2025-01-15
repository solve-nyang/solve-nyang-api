package com.ssafy.solvedpick.members.domain;

import com.ssafy.solvedpick.ownedavatar.domain.OwnedAvatar;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "members")
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", columnDefinition = "INT UNSIGNED")
    private Long id;

    @Column(unique = true, nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 72)
    private String password;

    @Column
    private int point = 0;

    @Column
    private boolean verified = false;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OwnedAvatar> avatars = new ArrayList<>();

}
