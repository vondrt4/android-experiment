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
import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
public class OAuth2ClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    
    private static final String TAG = OAuth2ClientHttpRequestInterceptor.class.getSimpleName();
    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    
    private final AccessTokenProvider authProvider;
    private final OAuth2ResourceDetails resource;

    
    public OAuth2ClientHttpRequestInterceptor(AccessTokenProvider authProvider, OAuth2ResourceDetails resource) {
        this.authProvider = authProvider;
        this.resource = resource;
    }
      

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) 
            throws IOException {
        
        try {
            OAuth2AccessToken token = authProvider.obtainAccessToken(resource);
            String accessToken = token.getAccessToken();

            Log.d(TAG, AUTH_HEADER + ": " + TOKEN_PREFIX + accessToken);
            request.getHeaders().set(AUTH_HEADER, TOKEN_PREFIX + accessToken);
            
        } catch (AuthorizationRequiredException ex) {
            Log.w(TAG, "Authorization required!");
        }
        
        return execution.execute(request, body);
    }

}
