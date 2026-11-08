
package be.iccbxl.pid.reservations_springboot.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "representations")
public class Representation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    /**
     * Date de création de la représentation
     */
    private LocalDateTime when;

    /**
     * Lieu de prestation de la représentation
     */
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = true)
    private Location location;

    // Relation ManyToMany avec User
    @ManyToMany
    @JoinTable(
        name = "reservations",
        joinColumns = @JoinColumn(name = "representation_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();

    // Constructeur vide
    public Representation() {
    }

    // Constructeur avec paramètres
    public Representation(Show show, LocalDateTime when, Location location) {
        this.show = show;
        this.when = when;
        this.setLocation(location);
    }

    // Getters et setters

    public Long getId() {
        return id;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public LocalDateTime getWhen() {
        return when;
    }

    public void setWhen(LocalDateTime when) {
        this.when = when;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {

        if (this.location == location) {
            return;
        }

        // Retirer la représentation de l'ancien lieu
        if (this.location != null) {
            this.location.getRepresentations().remove(this);
        }

        // Définir le nouveau lieu
        this.location = location;

        // Ajouter la représentation au nouveau lieu
        if (this.location != null
                && !this.location.getRepresentations().contains(this)) {
            this.location.getRepresentations().add(this);
        }
    }

    // Récupérer les utilisateurs de la représentation
    public List<User> getUsers() {
        return users;
    }

    // Ajouter un utilisateur à la représentation
    public Representation addUser(User user) {

        if (!this.users.contains(user)) {
            this.users.add(user);
            user.addRepresentation(this);
        }

        return this;
    }

    // Retirer un utilisateur de la représentation
    public Representation removeUser(User user) {

        if (this.users.contains(user)) {
            this.users.remove(user);
            user.getRepresentations().remove(this);
        }

        return this;
    }

    @Override
    public String toString() {
        return "Representation [id=" + id
                + ", show=" + show
                + ", when=" + when
                + ", location=" + location + "]";
    }
}
