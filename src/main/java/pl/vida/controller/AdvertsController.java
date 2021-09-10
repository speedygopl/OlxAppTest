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
import pl.vida.repository.AttributesRepository;
import pl.vida.repository.ImagesRepository;
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
    @Autowired
    AttributesRepository attributesRepository;
    @Autowired
    ImagesRepository imagesRepository;


    @RequestMapping("/saveadverts")
    public String saveAdverts() throws IOException {
        HttpEntity<String> requestEntity = entity.requestEntityProvider();
        String url = "https://www.olx.pl/api/partner/adverts";
        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String adverts = responseEntity.getBody();
        adverts = adverts.replaceAll("\\{\"data\":\\[", "\\[" );
        adverts = adverts.replaceAll("\\}\\]\\}", "\\}\\]");
        System.out.println(adverts);
        try{
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Advert [] array = objectMapper.readValue(adverts, Advert[].class);
            for(Advert a : array){
                advertRepository.save(a);
                attributesRepository.saveAll(a.getAttributes());
                imagesRepository.saveAll(a.getImages());
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
