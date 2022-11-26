package com.sivalabs.moviebuffs.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Setter
@Getter
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "movie_id_generator", sequenceName = "movie_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "movie_id_generator")
	private Long id;

	private String title;

	@JsonProperty("tmdb_id")
	@Column(name = "tmdb_id")
	private String tmdbId;

	@JsonProperty("imdb_id")
	@Column(name = "imdb_id")
	private String imdbId;

	private String overview;

	private String tagline;

	private String runtime;

	private String revenue;

	@JsonProperty("release_date")
	@Column(name = "release_date")
	private LocalDate releaseDate;

	@JsonProperty("poster_path")
	@Column(name = "poster_path")
	private String posterPath;

	private String budget;

	private String homepage;

	@JsonProperty("original_language")
	@Column(name = "original_language")
	private String originalLanguage;

	@JsonProperty("vote_average")
	@Column(name = "vote_average")
	private Double voteAverage;

	@JsonProperty("vote_count")
	@Column(name = "vote_count")
	private Double voteCount;

	@Column(nullable = false)
	private BigDecimal price;

	@JsonProperty("created_at")
	@Column(updatable = false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@JsonProperty("updated_at")
	@Column(insertable = false)
	protected LocalDateTime updatedAt = LocalDateTime.now();

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "movie_genre", joinColumns = { @JoinColumn(name = "MOVIE_ID", referencedColumnName = "ID") },
			inverseJoinColumns = { @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID") })
	private Set<Genre> genres = new HashSet<>();

	@OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
	private Set<CastMember> castMembers;

	@OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
	private Set<CrewMember> crewMembers;

	@PrePersist
	public void onCreate() {
		createdAt = LocalDateTime.now();
	}

	@PreUpdate
	public void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

}
