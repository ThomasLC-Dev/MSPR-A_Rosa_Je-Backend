package fr.cttt.arosaje.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plant_images")
public class PlantImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "image_url")
    private String imageUrl;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "plant_id", referencedColumnName = "id")
    private Plant plant;
}
