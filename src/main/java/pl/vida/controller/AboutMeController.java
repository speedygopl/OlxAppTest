package pl.vida.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import pl.vida.service.RequestEntity;

import java.io.IOException;

@Controller
public class AboutMeController {

    RestTemplate template = new RestTemplate();
    RequestEntity requestEntity = new RequestEntity();


    @GetMapping("/aboutMe")
    public String aboutMePage(Model model) throws IOException {
        String url = "https://www.olx.pl/api/partner/users/me";
        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.GET, requestEntity.requestEntityProvider(), String.class);
        String response = responseEntity.getBody();
        JsonNode aboutMe = new ObjectMapper().readValue(response, JsonNode.class);
        model.addAttribute("aboutMe", aboutMe);
        return "index";
    }


}
