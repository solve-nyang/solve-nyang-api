package com.ssafy.solvedpick.avatars.domain;

import com.ssafy.solvedpick.ownedavatar.domain.OwnedAvatar;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "avatars")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Min(1)
    @Max(5)
    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private int grade;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "avatar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OwnedAvatar> avatars = new ArrayList<>();
}
