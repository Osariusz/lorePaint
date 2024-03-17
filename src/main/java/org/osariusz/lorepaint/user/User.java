package org.osariusz.lorepaint.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.systemRole.SystemRole;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Column
    private String username;

    @NotNull(message = "Password hash cannot be null")
    @NotBlank(message = "Password hash cannot be blank")
    @Column
    private String password;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime last_edit;

    @Column
    private LocalDateTime last_online;

    @Column
    private LocalDateTime removed_at;

    @OneToMany(mappedBy = "user")
    private List<LoreRole> loreRoles;

    @OneToMany(mappedBy = "user")
    private List<SystemRole> systemRoles;

    @OneToMany(mappedBy = "owner")
    private List<Place> places;
}
