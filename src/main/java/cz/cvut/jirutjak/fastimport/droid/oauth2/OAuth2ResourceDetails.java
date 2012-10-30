/*
 * Copyright (c) 2012 Jakub Jirutka <jakub@jirutka.cz>
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package cz.cvut.jirutjak.fastimport.droid.oauth2;

import android.webkit.URLUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
public class OAuth2ResourceDetails implements Serializable {
    
    protected static final String DEFAULT_REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    protected static final String RESPONSE_TYPE = "code";
    
    private final String id;
    private String accessTokenEndpoint;
    private String clientId;
    private String clientSecret;
    private String redirectUri = DEFAULT_REDIRECT_URI;
    private List<String> scope = new ArrayList<String>(3);
    private String authorizationEndpoint;

    
    public OAuth2ResourceDetails(String resourceId) {
        this.id = resourceId;
    }

    
    public String getId() {
        return id;
    }
    
    public String getAccessTokenEndpoint() {
        return accessTokenEndpoint;
    }

    public OAuth2ResourceDetails setAccessTokenEndpoint(String accessTokenEndpoint) {
        assert URLUtil.isValidUrl(accessTokenEndpoint) : "Access Token Endpoint should be a valid URL";
        
        this.accessTokenEndpoint = accessTokenEndpoint;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public OAuth2ResourceDetails setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public OAuth2ResourceDetails setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public OAuth2ResourceDetails setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public List<String> getScope() {
        return scope;
    }
    
    public String getScopeAsString() {
        StringBuilder result = new StringBuilder(scope.size());
        for (String item : scope) {
            if (result.length() != 0) result.append(" ");
            result.append(item);
        }
        return result.toString();
    }
    
    public OAuth2ResourceDetails setScope(List<String> scope) {
        this.scope = scope;
        return this;
    }

    public OAuth2ResourceDetails addScope(String scope) {
        this.scope.add(scope);
        return this;
    }

    public String getAuthorizationEndpoint() {
        return authorizationEndpoint;
    }

    public OAuth2ResourceDetails setAuthorizationEndpoint(String AuthorizationEndpoint) {
        assert URLUtil.isValidUrl(accessTokenEndpoint) : "Authorization Endpoint should be a valid URL";
        
        this.authorizationEndpoint = AuthorizationEndpoint;
        return this;
    }
    
    
    public String buildAuthorizationRedirectUrl() {
        assert authorizationEndpoint != null : "Authorization Endpoint should not be null";
        assert clientId != null : "Authorization Endpoint should not be null";
        assert redirectUri != null : "Redirect URI should not be null";
        
        StringBuilder urlBuilder = new StringBuilder()
                .append(authorizationEndpoint)
                .append("?client_id=").append(clientId)
                .append("&redirect_uri=").append(redirectUri)
                .append("&response_type=").append(RESPONSE_TYPE);
        
        if (! scope.isEmpty()) {
            urlBuilder.append("&scope=").append(getScopeAsString());
        }
        
        String url = urlBuilder.toString();
        assert URLUtil.isValidUrl(url) : "Built URL is not valid";
        
        return url;
    }
    
}
