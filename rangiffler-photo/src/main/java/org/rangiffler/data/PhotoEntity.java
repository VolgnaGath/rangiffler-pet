package org.rangiffler.data;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "photo")
public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    String countryCode;

    @Column
    String description;

    @Column(name = "image")
    String image;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PhotoEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                '}';
    }
}
