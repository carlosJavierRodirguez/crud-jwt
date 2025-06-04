package com.example.crud_jwt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "last-name", length = 100)
    private String lastName;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean status = true;

    @Column(nullable = false, length = 100)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    // private List<RecoveryRequest> recoveryRequests;

}
