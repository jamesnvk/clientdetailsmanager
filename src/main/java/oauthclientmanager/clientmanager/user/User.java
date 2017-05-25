package oauthclientmanager.clientmanager.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ouathclientmanager.clientmanager.clientdetails.OauthClientDetails;


@Entity
@Table(name="USER")
public class User {
	@Id
	@Column(name="user_name")
	private String userName;
	@Column(name="cre_timest")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creTimest;
	@Column(name="mod_timest")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modTimest;
	
    @PrePersist
    protected void onCreate() {
    modTimest = creTimest = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
    modTimest = new Date();
    }
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="user")
	private Set<OauthClientDetails> clientDetails = new HashSet<>();
	@JoinTable
	  (
	      name="USER_CLIENT",
	      joinColumns={ @JoinColumn(name="user_name", referencedColumnName="user_name") },
	      inverseJoinColumns={ @JoinColumn(name="client_id", referencedColumnName="client_id", unique=true) }
	  )
	
	
	public Set<OauthClientDetails> getClientDetails() {
		return this.clientDetails;
	}

	public User(){}
	
	public User(String userName){
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreTimest() {
		return creTimest;
	}

	public void setCreTimest(Date creTimest) {
		this.creTimest = creTimest;
	}

	public Date getModTimest() {
		return modTimest;
	}

	public void setModTimest(Date modTimest) {
		this.modTimest = modTimest;
	}
	
    public void addClientDetails(OauthClientDetails details) {
        this.clientDetails.add(details);
    }

	@Override
	public String toString() {
		return "User [userName=" + userName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	
	
	


	
}
