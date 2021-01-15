package com.jung.springbook.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * (1) env.getActiveProfiles() 는
 * 현재 실행 중인 ActiveProfile 을 모두 가져온다.
 * 즉, real, oauth, real-db 등이 활성화되어 있다면 3개가 모두 담겨 있다.
 * 여기서 real, real1, real2 는 모두 배포에 사용될 profile 이라 이 중 하나라도 있으면 그 값을 변환하도록 한다.
 */
@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final Environment env;

    @GetMapping("/profile")
    public String profile(){
        List<String> profiles = Arrays.asList(env.getActiveProfiles()); // (1)

        List<String> realProfiles = Arrays.asList("real","real1","real2");

        String defaultProfiles = profiles.isEmpty() ? "default" : profiles.get(0);

        return profiles.stream()
                .filter(realProfiles::contains)
                .findAny()
                .orElse(defaultProfiles);
    }
}
