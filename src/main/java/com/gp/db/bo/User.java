package com.gp.db.bo;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.Date;


@Entity
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)
public class User {

	@Id
    @Column(name="emailAddress",length=40)
    private String emailAddress;

	@Column(name="userName",length=100)
	private String userName;
	
	@Column(name="password",length=60)
	private String password;
	
	@Column(name="createdDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name="updatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	
}
