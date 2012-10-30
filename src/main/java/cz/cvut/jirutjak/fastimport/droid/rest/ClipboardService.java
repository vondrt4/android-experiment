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

package cz.cvut.jirutjak.fastimport.droid.rest;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Delete;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;
import cz.cvut.jirutjak.fastimport.droid.AppConfig;
import cz.cvut.jirutjak.fastimport.droid.models.Snippet;
import cz.cvut.jirutjak.fastimport.droid.models.Snippet.SnippetsList;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Rest(AppConfig.clipboard_base_uri)
@Accept(MediaType.APPLICATION_JSON)
public interface ClipboardService {

    @Get("/snippets")
    SnippetsList getSnippets() throws RestClientException;

    @Get("/snippets?limit={limit}&offset={offset}")
    SnippetsList getSnippets(int limit, int offset) throws RestClientException;

    @Get("/snippets/{id}")
    Snippet getSnippet(Long id) throws RestClientException;

    @Delete("/snippets/{id}")
    void deleteSnippet(Long id) throws RestClientException;

    
    void setRestTemplate(RestTemplate restTemplate);
}
