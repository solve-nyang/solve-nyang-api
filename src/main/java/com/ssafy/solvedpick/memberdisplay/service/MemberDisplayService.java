package com.ssafy.solvedpick.memberdisplay.service;

import com.ssafy.solvedpick.api.dto.UserData;
import com.ssafy.solvedpick.memberdisplay.domain.MemberDisplay;
import com.ssafy.solvedpick.memberdisplay.repository.MemberDisplayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberDisplayService {

    private final MemberDisplayRepository memberDisplayRepository;

    @Transactional
    public void updateMemberDisplay(MemberDisplay memberDisplay, UserData userData) {
        memberDisplay.updateInfo(userData);
    }
}
