package com.eit.gateway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "emailconfig", schema = "mvt")
//@Getter 
//@Setter
public class EmailConfig {
	
	public EmailConfig() {
		// TODO Auto-generated constructor stub
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Bigserial equivalent in JPA
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "subject", columnDefinition = "json")
    private String subject; // Stored as JSON in database, but handled as String here

    @Column(name = "contenttype", columnDefinition = "json")
    private String contentType; // Stored as JSON in database, but handled as String here

    @Column(name = "sentto", length = 255)
    private String sentTo;

    @Column(name = "custommail")
    private Boolean customMail;

    @Column(name = "withattachment", nullable = false)
    private Boolean withAttachment = false; // Default value of false

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getSentTo() {
		return sentTo;
	}

	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}

	public Boolean getCustomMail() {
		return customMail;
	}

	public void setCustomMail(Boolean customMail) {
		this.customMail = customMail;
	}

	public Boolean getWithAttachment() {
		return withAttachment;
	}

	public void setWithAttachment(Boolean withAttachment) {
		this.withAttachment = withAttachment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EmailConfig(Long id, String name, String subject, String contentType, String sentTo, Boolean customMail,
			Boolean withAttachment, String description) {
		super();
		this.id = id;
		this.name = name;
		this.subject = subject;
		this.contentType = contentType;
		this.sentTo = sentTo;
		this.customMail = customMail;
		this.withAttachment = withAttachment;
		this.description = description;
	}
    
    
}

