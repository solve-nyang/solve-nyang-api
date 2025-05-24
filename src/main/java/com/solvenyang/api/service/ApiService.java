package com.solvenyang.api.service;

import com.solvenyang.api.dto.SolvedProblemsApiResponse;
import com.solvenyang.api.dto.UserData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

    private final RestTemplate restTemplate;
    @Value("${API.BASEURL}")
    private String baseUrl;

    public UserData getUserInfo(String username) {
        String url = baseUrl + "/user/show?handle=" + username;
        log.debug("Calling API: {}", url);

        ResponseEntity<String> rawResponse = restTemplate.getForEntity(url, String.class);
        log.debug("Raw API Response: {}", rawResponse.getBody());
        
        UserData response = restTemplate.getForObject(url, UserData.class);
        log.debug("Parsed Response: {}", response);
        return response;
    }

    public SolvedProblemsApiResponse getSolvedProblems(String username) {
    	String url = baseUrl + "/user/problem_stats?handle=" + username;
    	log.debug("Calling API: {}", url);
    	
    	ResponseEntity<String> rawResponse = restTemplate.getForEntity(url, String.class);
        log.debug("Raw API Response: {}", rawResponse.getBody());
        
        SolvedProblemsApiResponse response = restTemplate.getForObject(url, SolvedProblemsApiResponse.class);
        log.debug("Parsed Response: {}", response);
        return response;
    }
}
