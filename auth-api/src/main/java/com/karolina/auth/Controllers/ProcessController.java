package com.karolina.auth.Controllers;

import com.karolina.auth.DTO.ProcessRequest;
import com.karolina.auth.DTO.TransformResponse;
import com.karolina.auth.Entity.ProcessingLog;
import com.karolina.auth.Entity.User;
import com.karolina.auth.Repositories.ProcessingLogRepository;
import com.karolina.auth.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProcessController {
    private final RestTemplate restTemplate;
    private final ProcessingLogRepository processingLogRepository;
    private final UserRepository userRepository;

    @Value("${internal.token}")
    private String internalToken;

    @PostMapping("/process")
    public TransformResponse process(@RequestBody ProcessRequest request, Principal principal) {
        String email = principal.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));

        String url ="http://data-api:8081/api/transform";
        //String url = "http://localhost:8081/api/transform";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Token", internalToken);

        HttpEntity<ProcessRequest> entity = new HttpEntity<>(request, headers);

        TransformResponse response = restTemplate.postForObject(
                url,
                entity,
                TransformResponse.class
        );


        ProcessingLog log = new ProcessingLog();
        log.setUserId(user.getId());
        log.setInputText(request.getText());
        log.setOutputText(response.getResult());
        log.setCreatedAt(LocalDateTime.now());

        processingLogRepository.save(log);

        return response;
    }
}
