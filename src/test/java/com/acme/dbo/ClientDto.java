package com.acme.dbo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


/**
 * Client
 * <p>
 * Entity with personalized information about client
 */
@Entity
@Table(name = "CLIENT")
public class ClientDto {


    /**
     * Client id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "SALT")
    private String salt;

    @Column(name = "CREATED")
    private LocalDateTime created;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @Column(name = "SECRET")
    private String secret;


    public ClientDto() {

    }

    public ClientDto(String login, String secret, LocalDateTime created, String salt, boolean enabled) {
        this.login = login;
        this.secret = secret;
        this.salt = salt;
        this.created = created;
        this.enabled = enabled;
    }

    /**
     * Client login for auth
     * (Required)
     */
    @JsonProperty("login")
    public String getLogin() {
        return login;
    }

    /**
     * Client login for auth
     * (Required)
     */
    @JsonProperty("login")
    public ClientDto setLogin(String login) {
        this.login = login;
        return this;
    }

    @JsonProperty("enabled")
    public ClientDto setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }


    @JsonProperty("created")
    public ClientDto setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    @JsonProperty("id")
    public ClientDto setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Client salt
     * (Required)
     */
    @JsonProperty("salt")
    public String getSalt() {
        return salt;
    }

    @JsonProperty("created")
    public LocalDateTime getCreated() {
        return created;
    }

    @JsonProperty("enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * Client salt
     * (Required)
     */
    @JsonProperty("salt")
    public ClientDto setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    /**
     * Client secret
     * (Required)
     */
    @JsonProperty("secret")
    public String getSecret() {
        return secret;
    }

    /**
     * Client secret
     * (Required)
     */
    @JsonProperty("secret")
    public ClientDto setSecret(String secret) {
        this.secret = secret;
        return this;
    }

}
