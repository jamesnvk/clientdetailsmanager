package oauthclientmanager.clientmanager.clientdetails;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import oauthclientmanager.clientmanager.user.User;

@Entity
@Table(name="OAUTH_CLIENT_DETAILS")
public class OauthClientDetails {
	
	@Id
	@Column(name="client_id")
	private String clientId;
	@Column(name="client_secret")
	private String clientSecret;
	@Column(name="resource_ids")
	private String resourceIds = "search-api";
	private String scope = "SEARCH_API_READ";
	@Column(name="authorized_grant_types")
	private String authorizedGrantTypes = "client_credentials,refresh_token";
	@Column(name="web_server_redirect_uri")
	private String webServerRedirectUri;
	private String authorities = "ROLE_CLIENT,ROLE_TRUSTED_CLIENT"; 
	@Column(name="access_token_validity")
	private Long accessTokenValidity = (long) 3600;
	@Column(name="refresh_token_validity")
	private Long refreshTokenValidity = (long) 3600;
	@Column(name="additional_information")
	private String additionalInformation; 
	private String autoapprove;
	
	@OneToOne
	@JoinTable
	  (
	      name="USER_CLIENT",
	      joinColumns={ @JoinColumn(name="client_id", referencedColumnName="client_id", unique=true) },
	      inverseJoinColumns={ @JoinColumn(name="user_name", referencedColumnName="user_name") }
	  )
	private User user;
	
	
	public void setUser(User user){
		this.user = user;
	}
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getResourceIds() {
		return resourceIds;
	}
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}
	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}
	public String getWebServerRedirectUri() {
		return webServerRedirectUri;
	}
	public void setWebServerRedirectUri(String webServerRedirectUri) {
		this.webServerRedirectUri = webServerRedirectUri;
	}
	public String getAuthorities() {
		return authorities;
	}
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	public Long getAccessTokenValidity() {
		return accessTokenValidity;
	}
	public void setAccessTokenValidity(Long accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}
	public Long getRefreshTokenValidity() {
		return refreshTokenValidity;
	}
	public void setRefreshTokenValidity(Long refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	public String getAutoapprove() {
		return autoapprove;
	}
	public void setAutoapprove(String autoapprove) {
		this.autoapprove = autoapprove;
	}
	
	@Override
	public String toString() {
		return "OauthClientDetails [clientId=" + clientId + ", clientSecret=" + clientSecret + ", resourceIds="
				+ resourceIds + ", scope=" + scope + ", authorizedGrantTypes=" + authorizedGrantTypes
				+ ", webServerRedirectUri=" + webServerRedirectUri + ", authorities=" + authorities
				+ ", accessTokenValidity=" + accessTokenValidity + ", refreshTokenValidity=" + refreshTokenValidity
				+ ", additionalInformation=" + additionalInformation + ", autoapprove=" + autoapprove + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
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
		OauthClientDetails other = (OauthClientDetails) obj;
		
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		return true;
	} 
	

}
