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

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.util.Assert;

/**
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
public class OAuth2ClientContext {
    
    private static final String TAG = OAuth2ClientContext.class.getSimpleName();
    private static final String AUTH_CODE_KEY_PREFIX = "auth_code_";

    private final Map<String, OAuth2AccessToken> accessTokens = new ConcurrentHashMap<String, OAuth2AccessToken>(1);

    private final Context appContext;

    
    public OAuth2ClientContext(Context appContext) {
        this.appContext = appContext;
    }
    

    public void addAccessToken(OAuth2ResourceDetails resource, OAuth2AccessToken accessToken) {
        accessTokens.put(resource.getId(), accessToken);
    }
    
    public OAuth2AccessToken getAccessToken(OAuth2ResourceDetails resource) {
        return accessTokens.get(resource.getId());
    }

    public void removeAccessToken(OAuth2ResourceDetails resource) {
        accessTokens.remove(resource.getId());
    }
    
    
    public void addAuthorizationCode(OAuth2ResourceDetails resource, String authCode) {
        appContext.getSharedPreferences(TAG, 0)
                .edit()
                .putString(getAuthorizationCodePrefKey(resource), authCode)
                .commit();
    }
    
    public String getAuthorizationCode(OAuth2ResourceDetails resource) {
        return getPreferences().getString(getAuthorizationCodePrefKey(resource), null);
    }
    
    public void removeAuthorizationCode(OAuth2ResourceDetails resource) {
        getPreferences().edit().remove(getAuthorizationCodePrefKey(resource));
    }
    
    
    private String getAuthorizationCodePrefKey(OAuth2ResourceDetails resource) {
        Assert.notNull(resource.getId(), "Resource ID should not be null");
        return AUTH_CODE_KEY_PREFIX + resource.getId();
    }
    
    private SharedPreferences getPreferences() {
        return appContext.getSharedPreferences(TAG, 0);
    }

}
