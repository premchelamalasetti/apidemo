package com.example.demo.api.model;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="api")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String job;
	private String mail;
	private Date createdDate;
	private Time createdTime;
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
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Time getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Time createdTime) {
		this.createdTime = createdTime;
	}
	public User(Long id, String name, String job, String mail, Date createdDate,Time createdTime) {
		super();
		this.id = id;
		this.name = name;
		this.job = job;
		this.mail = mail;
		this.createdDate = createdDate;
		this.createdTime=createdTime;
	}
	public User() {
		super();
	}
	
	
}
