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
    @Column(name = "sun_light")
    private Integer sunLight;
    @Column(name = "lower_temp")
    private Double lowerTemp;
    @Column(name = "higher_temp")
    private Double higherTemp;
    @Column(name = "watering_quantity")
    private Double wateringQuantity;
    @Column(name = "watering_frequency")
    private Integer wateringFrequency;
    @Column(name = "watering_container")
    private String wateringContainer;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "plant")
    private List<PlantImage> plantImages;
}
