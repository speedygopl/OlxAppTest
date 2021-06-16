package pl.vida.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import pl.vida.model.Token;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;

@Controller
public class TokenController {


    RestTemplate template = new RestTemplate();

    @RequestMapping("/refresh")
    public String useRefreshToken(@ModelAttribute Token token, Model model) throws IOException, URISyntaxException {
        //utworzenie mappera json
        ObjectMapper objectMapper = new ObjectMapper();
        //utworzenie obiektu tokena
        token = objectMapper.readValue(new File("token.txt"), Token.class);
        //utworzenie obiektu nagłówków
        HttpHeaders headers = new HttpHeaders();
        //dodanie nagłówka/ów
        headers.add("Content-Type", "application/json");
        //sprecyzowanie body requestu
        String body = "{\"grant_type\": \"refresh_token\", \"client_id\": \"201155\",\"client_secret\": \"fDP2GUdeACI1KMpvXqTIwX1fji5uiCo6tR5HM2kwsqluPJtH\",\"refresh_token\": \""+token.getRefresh_token()+"\"}";
        //utworzenie obiektu żądania składającego się z ciała i nagłówków
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        //URL olx do odświeżenia tokena
        String url = "https://www.olx.pl/api/open/oauth/token";
        //utworzenie obiektu odpowiedzi response
        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String response = responseEntity.getBody();
        //utworzenie streamu dla System.Out.Println do zapisywania w pliku i na konsoli
        PrintStream file = new PrintStream(new File("token.txt"));
        PrintStream console = System.out;
        //stream odpowiedzi serwera do pliku
        System.setOut(file);
        System.out.println(response);
        //przestawienie streamu na konsolę i wyświetlenie informacji o obiekcie token
        System.setOut(console);
        //wczytanie do obiektu tokena nowych wartości po odświeżeniu
        token = objectMapper.readValue(new File("token.txt"), Token.class);
        model.addAttribute("newToken", token);
        return "index";
    }
}
