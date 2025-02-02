package com.ssafy.solvedpick.problem.domain;

import com.ssafy.solvedpick.api.dto.ProblemData;
import com.ssafy.solvedpick.api.dto.SolvedProblemsApiResponse;
import com.ssafy.solvedpick.common.utils.point.Point;
import com.ssafy.solvedpick.members.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "solved_problems")
public class Problem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "Integer unsigned")
	private Long id;

	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer bronze5_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer bronze4_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer bronze3_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer bronze2_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer bronze1_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer silver5_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer silver4_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer silver3_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer silver2_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer silver1_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer gold5_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer gold4_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer gold3_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer gold2_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer gold1_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer platinum5_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer platinum4_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer platinum3_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer platinum2_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer platinum1_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer diamond5_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer diamond4_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer diamond3_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer diamond2_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer diamond1_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer ruby5_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer ruby4_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer ruby3_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer ruby2_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer ruby1_solved = 0;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Integer unrated_solved = 0;

	@OneToOne
	@JoinColumn(name = "user_id", unique = true)
	private Member member;

	public static Problem initSolvedProblems(Member member) {
        return Problem.builder()
                .member(member)
                .build();
	}

	public void updateProblemCount(int level, int newSolvedCount){

		switch(level){
			case 0 -> this.unrated_solved = newSolvedCount;
			case 1 -> this.bronze5_solved = newSolvedCount;
			case 2 -> this.bronze4_solved = newSolvedCount;
			case 3 -> this.bronze3_solved = newSolvedCount;
			case 4 -> this.bronze2_solved = newSolvedCount;
			case 5 -> this.bronze1_solved = newSolvedCount;
			case 6 -> this.silver5_solved = newSolvedCount;
			case 7 -> this.silver4_solved = newSolvedCount;
			case 8 -> this.silver3_solved = newSolvedCount;
			case 9 -> this.silver2_solved = newSolvedCount;
			case 10 -> this.silver1_solved = newSolvedCount;
			case 11 -> this.gold5_solved = newSolvedCount;
			case 12 -> this.gold4_solved = newSolvedCount;
			case 13 -> this.gold3_solved = newSolvedCount;
			case 14 -> this.gold2_solved = newSolvedCount;
			case 15 -> this.gold1_solved = newSolvedCount;
			case 16 -> this.platinum5_solved = newSolvedCount;
			case 17 -> this.platinum4_solved = newSolvedCount;
			case 18 -> this.platinum3_solved = newSolvedCount;
			case 19 -> this.platinum2_solved = newSolvedCount;
			case 20 -> this.platinum1_solved = newSolvedCount;
			case 21 -> this.diamond5_solved = newSolvedCount;
			case 22 -> this.diamond4_solved = newSolvedCount;
			case 23 -> this.diamond3_solved = newSolvedCount;
			case 24 -> this.diamond2_solved = newSolvedCount;
			case 25 -> this.diamond1_solved = newSolvedCount;
			case 26 -> this.ruby5_solved = newSolvedCount;
			case 27 -> this.ruby4_solved = newSolvedCount;
			case 28 -> this.ruby3_solved = newSolvedCount;
			case 29 -> this.ruby2_solved = newSolvedCount;
			case 30 -> this.ruby1_solved = newSolvedCount;
		}
	}
}
