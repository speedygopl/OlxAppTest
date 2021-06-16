package pl.vida.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import pl.vida.model.Token;

import java.io.File;
import java.io.IOException;

public class RequestEntity {
    public HttpEntity<String>  requestEntityProvider() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Token token = objectMapper.readValue(new File("token.txt"), Token.class);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Version", "2.0");
        headers.add("Authorization", "Bearer " + token.getAccess_token());
        String body="";
        return new HttpEntity<String>(body, headers);
    }
}
