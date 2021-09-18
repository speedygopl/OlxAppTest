package pl.vida.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "advert")
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ident", unique = true, nullable = false)
    public Long ident;
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
    @OneToMany(mappedBy = "advert", cascade = CascadeType.PERSIST)
    private List<Images> images;
    @OneToMany(mappedBy = "advert", cascade = CascadeType.PERSIST)
    private List<Attributes> attributes;


    public void setAdvertInImages (Images image){
        image.setAdvert(this);
    }
    public void setAdvertInAttributes(Attributes attribute){
        attribute.setAdvert(this);
    }
}








