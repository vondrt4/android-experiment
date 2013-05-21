/*
 * Copyright (c) 2012 Jakub Jirutka <jakub@jirutka.cz>, Tomas Vondra <vondrto6@fel.cvut.cz>
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

package cz.cvut.jirutjak.fastimport.droid.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.App;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.rest.RestService;
import org.springframework.http.MediaType;

import cz.cvut.jirutjak.fastimport.droid.AppConfig;
import cz.cvut.jirutjak.fastimport.droid.AppContext;
import cz.cvut.jirutjak.fastimport.droid.oauth2.OAuth2ClientHttpRequestInterceptor;
import cz.cvut.jirutjak.fastimport.droid.rest.helpers.ClipboardAuthProvider;
import cz.cvut.jirutjak.fastimport.droid.utils.ExtraKeyStoreHttpClientFactory;

@EBean
public class RESTServicesFacade {

    @App
    protected AppContext context;
    
    @RestService
    protected ClipboardService clipboard;
    
    @Bean
    protected ClipboardAuthProvider clipboardAuth;

    
    @AfterInject
    protected void initialize() {
        clipboard.setRestTemplate(createRestTemplate());
    }

    
    public ClipboardService clipboard() {
        return clipboard;
    }
    
    
    public RestTemplate createRestTemplate() {
        RestTemplate template = new RestTemplate(createClientHttpRequestFactory());
        template.setMessageConverters(createMessageConverters());
        template.setInterceptors(createRequestInterceptors());
        
        return template;
    }
    
    private List<HttpMessageConverter<?>> createMessageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(1);
        AtomFeedHttpMessageConverter newConverter = new AtomFeedHttpMessageConverter();//new SimpleXmlHttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(new MediaType("application", "xml"));
        newConverter.setSupportedMediaTypes(mediaTypes);
        converters.add(newConverter);
        return converters;
    }
    
    private List<ClientHttpRequestInterceptor> createRequestInterceptors() {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>(1);
        interceptors.add(new OAuth2ClientHttpRequestInterceptor(clipboardAuth, AppConfig.clipboard_auth));
        
        return interceptors;
    }
    
    private ClientHttpRequestFactory createClientHttpRequestFactory() {
        HttpClient client = new ExtraKeyStoreHttpClientFactory(context).createHttpClient();
        
        return new HttpComponentsClientHttpRequestFactory(client);
    }
}
