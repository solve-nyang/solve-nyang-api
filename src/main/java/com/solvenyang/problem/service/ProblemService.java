package com.solvenyang.problem.service;

import com.solvenyang.api.dto.ProblemData;
import com.solvenyang.api.dto.SolvedProblemsApiResponse;
import com.solvenyang.common.utils.point.Tier;
import com.solvenyang.members.domain.Member;
import com.solvenyang.problem.domain.Problem;
import com.solvenyang.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    public void updateSolvedProblems(Problem problem, SolvedProblemsApiResponse newProblems) {
        int totalPointDiff = calculateTotalPointDiff(problem, newProblems);

        if (totalPointDiff > 0) {
            Member member = problem.getMember();
            member.addPoint(totalPointDiff);
        }
    }

    private int calculateTotalPointDiff(Problem problem, SolvedProblemsApiResponse newProblems ) {
        return newProblems.stream()
                .filter(problemData -> problemData.getLevel() != null && problemData.getSolved() != null)
                .mapToInt(problemData -> calculatePointDiff(problem, problemData))
                .sum();
    }

    private int calculatePointDiff(Problem problem, ProblemData problemData) {
        int level = problemData.getLevel();
        int newSolvedCount = problemData.getSolved();

        int currentCount = switch(level){
            case 0 -> problem.getUnrated_solved();
            case 1 -> problem.getBronze5_solved();
            case 2 -> problem.getBronze4_solved();
            case 3 -> problem.getBronze3_solved();
            case 4 -> problem.getBronze2_solved();
            case 5 -> problem.getBronze1_solved();
            case 6 -> problem.getSilver5_solved();
            case 7 -> problem.getSilver4_solved();
            case 8 -> problem.getSilver3_solved();
            case 9 -> problem.getSilver2_solved();
            case 10 -> problem.getSilver1_solved();
            case 11 -> problem.getGold5_solved();
            case 12 -> problem.getGold4_solved();
            case 13 -> problem.getGold3_solved();
            case 14 -> problem.getGold2_solved();
            case 15 -> problem.getGold1_solved();
            case 16 -> problem.getPlatinum5_solved();
            case 17 -> problem.getPlatinum4_solved();
            case 18 -> problem.getPlatinum3_solved();
            case 19 -> problem.getPlatinum2_solved();
            case 20 -> problem.getPlatinum1_solved();
            case 21 -> problem.getDiamond5_solved();
            case 22 -> problem.getDiamond4_solved();
            case 23 -> problem.getDiamond3_solved();
            case 24 -> problem.getDiamond2_solved();
            case 25 -> problem.getDiamond1_solved();
            case 26 -> problem.getRuby5_solved();
            case 27 -> problem.getRuby4_solved();
            case 28 -> problem.getRuby3_solved();
            case 29 -> problem.getRuby2_solved();
            case 30 -> problem.getRuby1_solved();
            default -> problem.getUnrated_solved();
        };

        if (currentCount != newSolvedCount) {
            problem.updateProblemCount(level, newSolvedCount);
            return (newSolvedCount - currentCount) * Tier.getPointFromLevel(level);
        }

        return 0;
    }

    public Problem findByMember(Member member) {
        return problemRepository.findByMember(member)
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.NOT_FOUND,
                        "해당 사용자의 문제 데이터를 찾을 수 없습니다."
                ));
    }

    public void save(Problem problem){
        problemRepository.save(problem);
    }
}
