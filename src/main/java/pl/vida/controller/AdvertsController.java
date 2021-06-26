package pl.vida.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import pl.vida.service.RequestEntity;

import java.io.IOException;
import java.util.Iterator;

@Controller
public class AdvertsController {

    RequestEntity entity = new RequestEntity();
    RestTemplate template = new RestTemplate();

    @RequestMapping("/adverts")
    public String getAllAdverts(Model model) throws IOException {
        HttpEntity<String> requestEntity = entity.requestEntityProvider();
        String url = "https://www.olx.pl/api/partner/adverts";
        ResponseEntity<JsonNode> responseEntity = template.exchange(url, HttpMethod.GET, requestEntity, JsonNode.class);
        int size = responseEntity.getBody().get("data").size();
        JsonNode adverts = responseEntity.getBody().get("data");
        model.addAttribute("size", size);
        model.addAttribute("adverts", adverts);
        return "index";
    }
}
