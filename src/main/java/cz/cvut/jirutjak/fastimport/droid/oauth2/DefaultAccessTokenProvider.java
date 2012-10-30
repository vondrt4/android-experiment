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

import android.util.Log;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
public class DefaultAccessTokenProvider implements AccessTokenProvider {
    
    private static final String TAG = DefaultAccessTokenProvider.class.getSimpleName();
    
    private final OAuth2ClientContext oauthContext;

    
    public DefaultAccessTokenProvider(OAuth2ClientContext oauthContext) {
        this.oauthContext = oauthContext;
    }
    

    @Override
    public OAuth2AccessToken obtainAccessToken(OAuth2ResourceDetails resource) throws AuthorizationRequiredException {
        OAuth2AccessToken cachedToken = oauthContext.getAccessToken(resource);
        
        if (cachedToken == null || cachedToken.isExpired()) {
            OAuth2AccessToken newToken = retreiveAccessToken(resource, getAuthorizationCode(resource));
            oauthContext.addAccessToken(resource, newToken);
            return newToken;
            
        } else {
            return cachedToken;
        }
    }

    public OAuth2ClientContext getOAuth2ClientContext() {
        return oauthContext;
    }
    
    
    protected String getAuthorizationCode(OAuth2ResourceDetails resource) throws AuthorizationRequiredException {
        String authCode = oauthContext.getAuthorizationCode(resource);
        if (authCode == null) {
            throw new AuthorizationRequiredException(resource.buildAuthorizationRedirectUrl());
        }
        return authCode;
    }

    protected OAuth2AccessToken retreiveAccessToken(OAuth2ResourceDetails resource, String authCode) {
        MultiValueMap<String, String> form = AccessTokenUtils.createAccessTokenRequestForm(authCode, resource);
        OAuth2AccessToken token = null;

        Log.d(TAG, "Request new access token for resource: " + resource.getId());
        try {
            ResponseEntity<OAuth2AccessToken> response = AccessTokenUtils.createRestTemplate().exchange(
                    resource.getAccessTokenEndpoint(), HttpMethod.POST, 
                    createAccessTokenRequestEntity(form), OAuth2AccessToken.class);
            
            token = response.getBody();
            Log.d(TAG, "Retreived access token: " + token.toString());
            
        } catch (RestClientException ex) {
            throw new InvalidTokenException("Invalid token", ex);
        }
        
        if (token == null || token.getAccessToken() == null) {
            throw new InvalidTokenException("Invalid token");   
        }
        
        return token;
    }
    
    protected HttpEntity<MultiValueMap<String, String>> 
            createAccessTokenRequestEntity(MultiValueMap<String, String> formData) {
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        return new HttpEntity<MultiValueMap<String, String>>(formData, headers);
    }

}
