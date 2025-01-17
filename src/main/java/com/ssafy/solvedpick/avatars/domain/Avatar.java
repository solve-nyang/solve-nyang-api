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
@Setter
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
    private String title;

    @Min(1)
    @Max(5)
    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private int grade;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "avatar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OwnedAvatar> avatars = new ArrayList<>();
}
