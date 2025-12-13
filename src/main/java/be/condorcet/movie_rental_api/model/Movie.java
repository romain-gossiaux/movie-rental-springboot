package be.condorcet.movie_rental_api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String director;
    private Integer releaseYear;

    @Column(unique = true)
    private String imdbId;

    private BigDecimal pricePerDay;
    private boolean available;

    public Movie() {}

    public Movie(String title, String director, Integer releaseYear, String imdbId, BigDecimal pricePerDay, boolean available) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.imdbId = imdbId;
        this.pricePerDay = pricePerDay;
        this.available = available;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    public String getImdbId() { return imdbId; }
    public void setImdbId(String imdbId) { this.imdbId = imdbId; }

    public BigDecimal getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(BigDecimal pricePerDay) { this.pricePerDay = pricePerDay; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
