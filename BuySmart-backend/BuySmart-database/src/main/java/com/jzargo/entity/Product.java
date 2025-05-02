package com.jzargo.entity;

import com.jzargo.shared.common.Tags;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
@Table(name = "products")
public class Product implements BaseEntity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;

    @Column(name = "created_at")
    private LocalDateTime created;

    @OneToMany(
            mappedBy = "product", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @ElementCollection
    @Builder.Default
    @CollectionTable(name = "Product_images",joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image")
    List<String> images = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    @Builder.Default
    @CollectionTable(name = "Product_tags",joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag")
    List<Tags> tags = new ArrayList<>();

    private String name;
    private String description;
    private Double price;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "product")
    private ProductDiscount discount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    

}
