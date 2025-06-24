package com.sivalabs.moviebuffs.core.entity;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movie_cast")
@Setter
@Getter
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class CastMember implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "cast_id_generator", sequenceName = "cast_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "cast_id_generator")
	private Long id;

	@JsonProperty("cast_id")
	@Column(name = "cast_id")
	private String castId;

	private String character;

	@JsonProperty("credit_id")
	@Column(name = "credit_id")
	private String creditId;

	private String gender;

	private String uid;

	private String name;

	@Column(name = "cast_order")
	private String order;

	@JsonProperty("profile_path")
	@Column(name = "profile_path")
	private String profilePath;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id")
	private Movie movie;

}
