package com.chernytskyi.university.wad.domain;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Movie {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private byte[] cover;
    private String director;
    private Integer duration; // minutes
    private Integer releaseYear;
    private Integer ratingSum;
    private Integer ratingCount;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy = "movie",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Movie() {}
    public Movie(String title, byte[] cover, String director, Integer duration, Integer releaseYear, Set<Genre> genres) {
        this.title = title;
        this.cover = cover;
        this.director = director;
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.ratingSum = 0;
        this.ratingCount = 0;
        this.genres = genres;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(Integer ratingSum) {
        this.ratingSum = ratingSum;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cover=" + Arrays.toString(cover) +
                ", director='" + director + '\'' +
                ", duration=" + duration +
                ", releaseYear=" + releaseYear +
                ", ratingSum=" + ratingSum +
                ", ratingCount=" + ratingCount +
                ", genres=" + genres +
                ", reviews=" + reviews +
                '}';
    }
}
