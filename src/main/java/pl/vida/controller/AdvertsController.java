package pl.vida.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import pl.vida.model.Advert;
import pl.vida.repository.AdvertRepository;
import pl.vida.service.RequestEntity;

import java.io.IOException;
import java.util.*;

@Controller
public class AdvertsController {

    RequestEntity entity = new RequestEntity();
    RestTemplate template = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();
    Advert advert = new Advert();


    @Autowired
    AdvertRepository advertRepository;


    @RequestMapping("/saveadverts")
    public String saveAdverts() throws IOException {
        HttpEntity<String> requestEntity = entity.requestEntityProvider();
        String url = "https://www.olx.pl/api/partner/adverts";
        ResponseEntity<JsonNode> responseEntity = template.exchange(url, HttpMethod.GET, requestEntity, JsonNode.class);
        String adverts = responseEntity.getBody().get("data").toString();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Advert[] array = objectMapper.readValue(adverts, Advert[].class);
            for(Advert advert : array) {
                Advert saved = advertRepository.save(advert);
                System.out.println(saved);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "index";
    }

    @RequestMapping("/getadverts")
    public String getAdverts(Model model) throws IOException {
        HttpEntity<String> requestEntity = entity.requestEntityProvider();
        List<Advert> advertsList = new ArrayList<>();
        String url = "https://www.olx.pl/api/partner/adverts";
        ResponseEntity<JsonNode> responseEntity = template.exchange(url, HttpMethod.GET, requestEntity, JsonNode.class);
        JsonNode adverts = responseEntity.getBody().get("data");
        model.addAttribute("adverts", adverts);
        return "index";
    }



}
