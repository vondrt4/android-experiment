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

package cz.cvut.jirutjak.fastimport.droid;

import cz.cvut.jirutjak.fastimport.droid.oauth2.OAuth2ResourceDetails;

/**
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
public class AppConfig {
    
    public static final String clipboard_base_uri = "http://kosapi.fit.cvut.cz/api/3";
    
    public static final OAuth2ResourceDetails clipboard_auth = new OAuth2ResourceDetails("CVUT")
            .setAccessTokenEndpoint("https://auth.fit.cvut.cz/oauth/oauth/token")
            .setAuthorizationEndpoint("https://auth.fit.cvut.cz/oauth/oauth/authorize")
            .setClientId("rOxVaJeAbdtPAR6jn31ZFckqy6xzDkrwAQuQf4wZ0gWKzBCMu0ktSeqNtiaiftqa")
            .setClientSecret("ziA4Szk9VHZiFmrCZLyQp35g8YoXYswO5D7FKNAzrHlMlW4a9qxNe4kiP6cZwCTB")
            .addScope("urn:ctu:oauth:kosapi:public.readonly");
}
