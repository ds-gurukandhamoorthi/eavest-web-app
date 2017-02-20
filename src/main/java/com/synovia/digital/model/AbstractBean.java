package com.synovia.digital.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public abstract class AbstractBean {
	// ------------------------------ FIELDS ------------------------------

	@Column(name = "CREATED_AT", updatable = false)
	@DateTimeFormat(style = "M-")
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "CREATED_BY", updatable = false)
	private Long createdBy;

	@Column(name = "UPDATED_AT")
	@DateTimeFormat(style = "M-")
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	// --------------------- GETTER / SETTER METHODS ---------------------

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	// -------------------------- OTHER METHODS --------------------------

	@PrePersist
	public void prePersist() {
		// TODO Plug user helper
		// UserHelper x = UserHelper.getLoggedOne();
		Long idUser = null;
		this.setCreatedAt(new Date());
		this.setUpdatedAt(this.createdAt);
		if (idUser != null) {
			this.setCreatedBy(idUser);
			this.setUpdatedBy(this.createdBy);
		}
	}

	@PreUpdate
	public void preUpdate() {
		// TODO Plug user helper
		Long idUser = null;
		// UserHelper x = UserHelper.getLoggedOne();
		this.setUpdatedAt(new Date());
		if (idUser != null) {
			this.setUpdatedBy(idUser);
		}
	}
}
