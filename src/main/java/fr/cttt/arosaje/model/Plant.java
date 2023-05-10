package fr.cttt.arosaje.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "advises")
    private String advises;
    @Column(name = "sunLight")
    private Integer sunLight;
    @Column(name = "lowerTemp")
    private Double lowerTemp;
    @Column(name = "higherTemp")
    private Double higherTemp;
    @Column(name = "wateringQuantity")
    private Double wateringQuantity;
    @Column(name = "wateringFrequency")
    private Integer wateringFrequency;
    @Column(name = "wateringContainer")
    private String wateringContainer;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "plant")
    private List<PlantImage> plantImages;
}
