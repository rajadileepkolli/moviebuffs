package com.sivalabs.moviebuffs.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
public abstract class BaseEntity implements Serializable {

	@Override
	public int hashCode() {
		return 31;
	}

	@JsonProperty("created_at")
	@Column(updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@JsonProperty("updated_at")
	@Column(insertable = false)
	private LocalDateTime updatedAt = LocalDateTime.now();

	@PrePersist
	public void onCreate() {
		createdAt = LocalDateTime.now();
	}

	@PreUpdate
	public void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

}
