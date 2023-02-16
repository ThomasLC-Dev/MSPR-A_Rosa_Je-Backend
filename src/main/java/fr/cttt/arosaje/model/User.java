package fr.cttt.arosaje.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @JsonIgnore
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "image_url")
    private String imageUrl;
    @OneToOne(mappedBy = "user")
    private Address address;
    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Plant> plants;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Slot> userSlots;
    @JsonIgnore
    @OneToMany(mappedBy = "keeper")
    private List<Slot> keeperSlots;
}
