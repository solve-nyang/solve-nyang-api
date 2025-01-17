package com.ssafy.solvedpick.api.service;

import com.ssafy.solvedpick.api.dto.UserInfoApiResponse;
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

    public UserInfoApiResponse getUserInfo(String username) {
        String url = baseUrl + "/search/user?query=" + username;
        log.info("Calling API: {}", url);

        ResponseEntity<String> rawResponse = restTemplate.getForEntity(url, String.class);
        log.info("Raw API Response: {}", rawResponse.getBody());  // 실제 JSON 응답을 확인

        UserInfoApiResponse response = restTemplate.getForObject(url, UserInfoApiResponse.class);
        log.info("Parsed Response: {}", response);
        return response;
    }
}
