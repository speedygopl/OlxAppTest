package pl.vida.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@ToString
@Entity
@Table(name = "advert")
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
    @Transient
    @OneToMany(mappedBy = "advert", cascade = CascadeType.ALL)
    private List<Images> images;
    @Transient
    @OneToMany(mappedBy = "advert", cascade = CascadeType.ALL)
    private List<Attributes> attributes;
}








