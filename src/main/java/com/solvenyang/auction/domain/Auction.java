package com.solvenyang.auction.domain;

import com.solvenyang.ownedavatar.domain.OwnedAvatar;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "auction")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owned_avatar_id", nullable = false)
    private OwnedAvatar ownedAvatar;

    @Min(1)
    @Column(nullable = false)
    private Long price;

    @Builder.Default
    @Column(nullable = false)
    private Boolean sold = false;

    @Builder.Default
    @Column(nullable = false)
    private Boolean cancelled = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public void cancel() {
        this.cancelled = true;
    }

    public void sold() {
        this.sold = true;
    }
}
