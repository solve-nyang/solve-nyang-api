package com.ssafy.solvedpick.accounts.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "verification_keys")
public class VerificationKey {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "Integer unsigned")
	private Long id;
	
	@Column(nullable = false, unique = true, length = 30)
	private String username;
	
	@Column(nullable = false, length = 12)
	private String verificationCode;
	
	@Builder.Default
	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
	
	public void updateVerificationCode(String newCode) {
		this.verificationCode = newCode;
		this.createdAt = LocalDateTime.now();
	}
}
