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

package cz.cvut.jirutjak.fastimport.droid.utils;

import android.content.Context;
import java.io.InputStream;
import java.security.KeyStore;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

/**
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
public class ExtraKeyStoreHttpClientFactory {
    
    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;
    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 5;
    
    private static final String DEFAULT_KEY_STORE_NAME = "keystore";
    private static final String DEFAULT_KEY_STORE_PASSWORD = "kevinflynn";
    
    private final Context context;
    
    private int keyStoreResourceId = 0;
    private String keyStorePassword = DEFAULT_KEY_STORE_PASSWORD;

    
    
    public ExtraKeyStoreHttpClientFactory(Context context) {
        this.context = context;
    }
        
    
    public HttpClient createHttpClient() {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", createAdditionalCertsSSLSocketFactory(), 443));

        HttpParams params = new BasicHttpParams();
        params.setIntParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, DEFAULT_MAX_TOTAL_CONNECTIONS);
        params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(DEFAULT_MAX_CONNECTIONS_PER_ROUTE));
        
        ThreadSafeClientConnManager connManager = new ThreadSafeClientConnManager(params, schemeRegistry);
        
        return new DefaultHttpClient(connManager, params);
    }

    protected SSLSocketFactory createAdditionalCertsSSLSocketFactory() {
        try {
            KeyStore keyStore = KeyStore.getInstance("BKS");
            
            // the bks file we generated above
            InputStream in = context.getResources().openRawResource(getKeyStoreResourceId());
            try {
                // don't forget to put the password used above in strings.xml/mystore_password
                keyStore.load(in, keyStorePassword.toCharArray());
            } finally {
                in.close();
            }

            return new AdditionalKeyStoresSSLSocketFactory(keyStore);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    protected int getResourceIdentifier(String name, String defType) {
        return context.getResources().getIdentifier(name, defType, context.getPackageName());
    }

    
    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public int getKeyStoreResourceId() {
        if (keyStoreResourceId == 0) {
            keyStoreResourceId = getResourceIdentifier(DEFAULT_KEY_STORE_NAME, "raw");
        }
        return keyStoreResourceId;
    }

    public void setKeyStoreResourceId(int keyStoreResourceId) {
        this.keyStoreResourceId = keyStoreResourceId;
    }
}
