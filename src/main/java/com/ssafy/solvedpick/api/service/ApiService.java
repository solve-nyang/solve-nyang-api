package com.ssafy.solvedpick.api.service;

import com.ssafy.solvedpick.api.dto.SolvedProblemsApiResponse;
import com.ssafy.solvedpick.api.dto.UserInfoApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

    private static final String USER_QUERY_PATH = "/search/user?query=";
    private static final String USER_SOLVED_PATH = "/user/problem_stats?handle=";

    private final RestTemplate restTemplate;

    @Value("${API.BASEURL}")
    private String baseUrl;

    public UserInfoApiResponse getUserInfo(String username) {
        String url = baseUrl + USER_QUERY_PATH + username;
        log.debug("Calling API: {}", url);
        
        return restTemplate.getForObject(url, UserInfoApiResponse.class);
    }
    
    
    public SolvedProblemsApiResponse getSolvedProblems(String username) {
    	String url = baseUrl + USER_SOLVED_PATH + username;
    	log.debug("Calling API: {}", url);

        return restTemplate.getForObject(url, SolvedProblemsApiResponse.class);
    }
}
