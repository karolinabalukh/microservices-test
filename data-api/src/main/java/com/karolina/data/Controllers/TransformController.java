package com.karolina.data.Controllers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.karolina.data.DTO.TransformRequest;
import com.karolina.data.DTO.TransformResponse;

@RestController
@RequestMapping("/api")
public class TransformController {

    @Value("${internal.token}")
    private String internalToken;

    @PostMapping("/transform")
    public TransformResponse transform(
            @RequestHeader(value = "X-Internal-Token", required = false) String token,
            @RequestBody TransformRequest request) {

        System.out.println("TOKEN FROM HEADER: " + token);
        System.out.println("EXPECTED TOKEN: " + internalToken);

        if (token == null || !internalToken.equals(token)) {
            throw new RuntimeException("Forbidden");
        }

        String result = request.getText().toUpperCase();
        return new TransformResponse(result);
    }
}