package pl.vida.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.mysql.cj.protocol.ColumnDefinition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import pl.vida.jsonnode.JsonNodeStringType;

import javax.persistence.*;

@Setter
@Getter
@ToString
@Entity
@Table(name = "advert")
@TypeDef(name = "JsonNode", typeClass = JsonNodeStringType.class)
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ident", unique = true, nullable = false)
    private Long ident;
    private int id;
    private String status;
    private String url;
    private String created_at;
    private String activated_at;
    private String valid_to;
    private String title;
    @Lob
    private String description;
    private int category_id;
    private String advertiser_type;
    private Long external_id;
    private String external_url;
    private String salary;
    private String courier;
    @Embedded
    private Location location;
    @Embedded
    private Contact contact;
    @Embedded
    private Price price;
    @Type(type = "JsonNode")
    @Column(columnDefinition = "VARCHAR(1000)")
    private JsonNode images;
    @Type(type = "JsonNode")
    private JsonNode attributes;
}








