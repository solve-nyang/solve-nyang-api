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
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	private Long bronze5_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long bronze4_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long bronze3_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long bronze2_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long bronze1_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long silver5_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long silver4_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long silver3_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long silver2_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long silver1_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long gold5_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long gold4_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long gold3_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long gold2_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long gold1_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long platinum5_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long platinum4_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long platinum3_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long platinum2_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long platinum1_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long diamond5_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long diamond4_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long diamond3_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long diamond2_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long diamond1_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long ruby5_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long ruby4_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long ruby3_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long ruby2_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long ruby1_solved = 0L;
	@Builder.Default
	@Column(columnDefinition = "Integer unsigned")
	private Long unrated_solved = 0L;

	@OneToOne
	@JoinColumn(name = "user_id", unique = true)
	private Member member;

	public void updateSolvedProblems(SolvedProblemsApiResponse newProblems) {
		Long plusPoint = 0L;
	    for (ProblemData  problem : newProblems) {
	    	int level = problem.getLevel();
	        int solved = problem.getSolved();

	        switch (level) {
	            case 0:
	                if (this.unrated_solved != solved) this.unrated_solved = (long) solved;
	                break;
	            case 1:
	                if (this.bronze5_solved != solved) {
	                	plusPoint += (solved - this.bronze5_solved) * Point.getPointFromLevel(level);
	                	this.bronze5_solved = (long) solved;
	                }
	                break;
	            case 2:
	                if (this.bronze4_solved != solved) {
	                	plusPoint += (solved - this.bronze4_solved) * Point.getPointFromLevel(level);
	                	this.bronze4_solved = (long) solved;
	                }
	                break;
	            case 3:
	                if (this.bronze3_solved != solved) {
	                	plusPoint += (solved - this.bronze3_solved) * Point.getPointFromLevel(level);
						this.bronze3_solved = (long) solved;
					}
	                break;
	            case 4:
	                if (this.bronze2_solved != solved) {
	                	plusPoint += (solved - this.bronze2_solved) * Point.getPointFromLevel(level);
						this.bronze2_solved = (long) solved;
					}
	                break;
	            case 5:
	                if (this.bronze1_solved != solved) {
	                	plusPoint += (solved - this.bronze1_solved) * Point.getPointFromLevel(level);
						this.bronze1_solved = (long) solved;
					}
	                break;
	            case 6:
	                if (this.silver5_solved != solved) {
	                	plusPoint += (solved - this.silver5_solved) * Point.getPointFromLevel(level);
						this.silver5_solved = (long) solved;
					}
	                break;
	            case 7:
	                if (this.silver4_solved != solved) {
	                	plusPoint += (solved - this.silver4_solved) * Point.getPointFromLevel(level);
						this.silver4_solved = (long) solved;
					}
	                break;
	            case 8:
	                if (this.silver3_solved != solved) {
	                	plusPoint += (solved - this.silver3_solved) * Point.getPointFromLevel(level);
						this.silver3_solved = (long) solved;
					}
	                break;
	            case 9:
	                if (this.silver2_solved != solved) {
	                	plusPoint += (solved - this.silver2_solved) * Point.getPointFromLevel(level);
						this.silver2_solved = (long) solved;
					}
	                break;
	            case 10:
	                if (this.silver1_solved != solved) {
	                	plusPoint += (solved - this.silver1_solved) * Point.getPointFromLevel(level);
						this.silver1_solved = (long) solved;
					}
	                break;
	            case 11:
	                if (this.gold5_solved != solved) {
	                	plusPoint += (solved - this.gold5_solved) * Point.getPointFromLevel(level);
						this.gold5_solved = (long) solved;
					}
	                break;
	            case 12:
	                if (this.gold4_solved != solved) {
	                	plusPoint += (solved - this.gold4_solved) * Point.getPointFromLevel(level);
						this.gold4_solved = (long) solved;
					}
	                break;
	            case 13:
	                if (this.gold3_solved != solved) {
	                	plusPoint += (solved - this.gold3_solved) * Point.getPointFromLevel(level);
						this.gold3_solved = (long) solved;
					}
	                break;
	            case 14:
	                if (this.gold2_solved != solved) {
	                	plusPoint += (solved - this.gold2_solved) * Point.getPointFromLevel(level);
						this.gold2_solved = (long) solved;
					}
	                break;
	            case 15:
	                if (this.gold1_solved != solved) {
	                	plusPoint += (solved - this.gold1_solved) * Point.getPointFromLevel(level);
						this.gold1_solved = (long) solved;
					}
	                break;
	            case 16:
	                if (this.platinum5_solved != solved) {
	                	plusPoint += (solved - this.platinum5_solved) * Point.getPointFromLevel(level);
						this.platinum5_solved = (long) solved;
					}
	                break;
	            case 17:
	                if (this.platinum4_solved != solved) {
	                	plusPoint += (solved - this.platinum4_solved) * Point.getPointFromLevel(level);
						this.platinum4_solved = (long) solved;
					}
	                break;
	            case 18:
	                if (this.platinum3_solved != solved) {
	                	plusPoint += (solved - this.platinum3_solved) * Point.getPointFromLevel(level);
						this.platinum3_solved = (long) solved;
					}
	                break;
	            case 19:
	                if (this.platinum2_solved != solved) {
	                	plusPoint += (solved - this.platinum2_solved) * Point.getPointFromLevel(level);
						this.platinum2_solved = (long) solved;
					}
	                break;
	            case 20:
	                if (this.platinum1_solved != solved) {
	                	plusPoint += (solved - this.platinum1_solved) * Point.getPointFromLevel(level);
						this.platinum1_solved = (long) solved;
					}
	                break;
	            case 21:
	                if (this.diamond5_solved != solved) {
	                	plusPoint += (solved - this.diamond5_solved) * Point.getPointFromLevel(level);
						this.diamond5_solved = (long) solved;
					}
	                break;
	            case 22:
	                if (this.diamond4_solved != solved) {
	                	plusPoint += (solved - this.diamond4_solved) * Point.getPointFromLevel(level);
						this.diamond4_solved = (long) solved;
					}
	                break;
	            case 23:
	                if (this.diamond3_solved != solved) {
	                	plusPoint += (solved - this.diamond3_solved) * Point.getPointFromLevel(level);
						this.diamond3_solved = (long) solved;
					}
	                break;
	            case 24:
	                if (this.diamond2_solved != solved) {
	                	plusPoint += (solved - this.diamond2_solved) * Point.getPointFromLevel(level);
						this.diamond2_solved = (long) solved;
					}
	                break;
	            case 25:
	                if (this.diamond1_solved != solved) {
	                	plusPoint += (solved - this.diamond1_solved) * Point.getPointFromLevel(level);
						this.diamond1_solved = (long) solved;
					}
	                break;
	            case 26:
	                if (this.ruby5_solved != solved) {
	                	plusPoint += (solved - this.ruby5_solved) * Point.getPointFromLevel(level);
						this.ruby5_solved = (long) solved;
					}
	                break;
	            case 27:
	                if (this.ruby4_solved != solved) {
	                	plusPoint += (solved - this.ruby4_solved) * Point.getPointFromLevel(level);
						this.ruby4_solved = (long) solved;
					}
	                break;
	            case 28:
	                if (this.ruby3_solved != solved) {
	                	plusPoint += (solved - this.ruby3_solved) * Point.getPointFromLevel(level);
						this.ruby3_solved = (long) solved;
					}
	                break;
	            case 29:
	                if (this.ruby2_solved != solved) {
	                	plusPoint += (solved - this.ruby2_solved) * Point.getPointFromLevel(level);
						this.ruby2_solved = (long) solved;
					}
	                break;
	            case 30:
	                if (this.ruby1_solved != solved) {
	                	plusPoint += (solved - this.ruby1_solved) * Point.getPointFromLevel(level);
						this.ruby1_solved = (long) solved;
					}
	                break;
	        }
	    }
		this.member.updatePoint(plusPoint.intValue());
		log.debug("point : {}", plusPoint);
	}
}
