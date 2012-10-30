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

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
public class AccessTokenUtils {
    
    private static final String GRANT_TYPE = "authorization_code";
    
    
    public static MultiValueMap<String, String> createAccessTokenRequestForm(String authCode, OAuth2ResourceDetails resource) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("code", authCode);
        form.add("client_id", resource.getClientId());
        form.add("client_secret", resource.getClientSecret());
        form.add("redirect_uri", resource.getRedirectUri());
        form.add("grant_type", GRANT_TYPE);
        
        return form;
    }
    
    public static RestTemplate createRestTemplate() {
        RestTemplate template = new RestTemplate();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        template.getMessageConverters().add(new GsonHttpMessageConverter(gson));
        
        return template;
    }
    
}
