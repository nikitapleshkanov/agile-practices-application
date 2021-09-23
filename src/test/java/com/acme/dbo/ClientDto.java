package com.acme.dbo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;


/**
 * Client
 * <p>
 * Entity with personalized information about client
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "login",
        "salt",
        "secret"
})
@Generated("jsonschema2pojo")
public class ClientDto {


    /**
     * Client login for auth
     * (Required)
     */
    @JsonProperty("login")
    @JsonPropertyDescription("Client login for auth")
    private String login;
    /**
     * Client salt
     * (Required)
     */
    @JsonProperty("salt")
    @JsonPropertyDescription("Client salt")
    private String salt;

    /**
     * Client id
     */
    @JsonProperty("id")
    @JsonPropertyDescription("Client id")
    private int id;
    /**
     * Client id
     */
    @JsonProperty("created")
    @JsonPropertyDescription("Client created")
    private String created;
    /**
     * Client enabled info
     */
    @JsonProperty("enabled")
    @JsonPropertyDescription("Client enabled")
    private Boolean enabled;
    /**
     * Client secret
     * (Required)
     */
    @JsonProperty("secret")
    @JsonPropertyDescription("Client secret")
    private String secret;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
    public ClientDto setCreated(String created) {
        this.created = created;
        return this;
    }

    @JsonProperty("id")
    public ClientDto setId(int id) {
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
    public String getCreated() {
        return created;
    }

    @JsonProperty("enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    @JsonProperty("id")
    public int getId() {
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}