
package be.iccbxl.pid.reservations_springboot.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.github.slugify.Slugify;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String slug;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "poster_url")
    private String posterUrl;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = true)
    private Location location;

    private boolean bookable;

    private double price;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relation avec les représentations
    @OneToMany(mappedBy = "show")
    private List<Representation> representations = new ArrayList<>();

    // Relation avec les artistes et leurs fonctions
    @ManyToMany(mappedBy = "shows")
    private List<ArtistType> artistTypes = new ArrayList<>();


    // CONSTRUCTEURS

    public Show() {
    }

    public Show(String title, String description, String posterUrl,
                Location location, boolean bookable, double price) {

        Slugify slg = new Slugify();

        this.slug = slg.slugify(title);
        this.title = title;
        this.description = description;
        this.posterUrl = posterUrl;
        this.setLocation(location);
        this.bookable = bookable;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }


    // GETTERS ET SETTERS

    public Long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    private void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

        Slugify slg = new Slugify();
        this.setSlug(slg.slugify(title));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {

        if (this.location == location) {
            return;
        }

        if (this.location != null) {
            this.location.getShows().remove(this);
        }

        this.location = location;

        if (this.location != null
                && !this.location.getShows().contains(this)) {
            this.location.getShows().add(this);
        }
    }

    public boolean isBookable() {
        return bookable;
    }

    public void setBookable(boolean bookable) {
        this.bookable = bookable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    // RELATION AVEC REPRESENTATION

    public List<Representation> getRepresentations() {
        return representations;
    }

    public Show addRepresentation(Representation representation) {

        if (representation != null
                && !this.representations.contains(representation)) {

            this.representations.add(representation);
            representation.setShow(this);
        }

        return this;
    }

    public Show removeRepresentation(Representation representation) {

        if (representation != null
                && this.representations.contains(representation)) {

            this.representations.remove(representation);

            if (representation.getShow() == this) {
                representation.setShow(null);
            }
        }

        return this;
    }


    // RELATION AVEC ARTISTTYPE

    public List<ArtistType> getArtistTypes() {
        return artistTypes;
    }

    public Show addArtistType(ArtistType artistType) {

        if (artistType != null
                && !this.artistTypes.contains(artistType)) {

            this.artistTypes.add(artistType);
            artistType.addShow(this);
        }

        return this;
    }

    public Show removeArtistType(ArtistType artistType) {

        if (artistType != null
                && this.artistTypes.contains(artistType)) {

            this.artistTypes.remove(artistType);
            artistType.getShows().remove(this);
        }

        return this;
    }


    // AFFICHAGE DE L'OBJET

    @Override
    public String toString() {
        return "Show [id=" + id
                + ", slug=" + slug
                + ", title=" + title
                + ", description=" + description
                + ", posterUrl=" + posterUrl
                + ", bookable=" + bookable
                + ", price=" + price
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + ", representations=" + representations.size()
                + ", artistTypes=" + artistTypes.size()
                + "]";
    }
}
