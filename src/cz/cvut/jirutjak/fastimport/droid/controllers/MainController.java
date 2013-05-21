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

package cz.cvut.jirutjak.fastimport.droid.controllers;

import android.text.ClipboardManager;
import android.util.Log;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.atom.Content;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.atom.Entry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.module.Module;
import com.googlecode.androidannotations.annotations.*;
import cz.cvut.jirutjak.fastimport.droid.Actions;
import cz.cvut.jirutjak.fastimport.droid.AppContext;
import cz.cvut.jirutjak.fastimport.droid.Data;
import cz.cvut.jirutjak.fastimport.droid.activities.MainActivity;
import cz.cvut.jirutjak.fastimport.droid.models.Snippet;
import cz.cvut.jirutjak.fastimport.droid.rest.RESTServicesFacade;
import cz.cvut.jirutjak.fastimport.droid.rest.helpers.ClipboardAuthProvider;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.web.client.RestClientException;

@EBean
public class MainController {
    
    private static final String TAG = MainController.class.getSimpleName();

    @App
    protected AppContext context;
    
    @RootContext
    protected MainActivity view;
    
    @Bean
    protected RESTServicesFacade services;
    
    @Bean
    protected ClipboardAuthProvider clipboardAuth;
    
    @SystemService
    protected ClipboardManager clipboard;
    
    @Extra(Data.SNIPPET)
    protected Snippet snippet = Snippet.NULL_OBJECT;
    
    
    @AfterViews
    public void initialize() {
        if (snippet == Snippet.NULL_OBJECT) {
            //reload();

            if (snippet == Snippet.NULL_OBJECT) {
                snippet = getSnippetFromCache();
            }
        }
        viewSnippet(snippet);
    }

    
    //TODO do in background thread
    public void reload() {
        Snippet newSnippet = getSnippetFromServer();

        if (newSnippet != Snippet.NULL_OBJECT) {
            context.getDAO().saveOrUpdate(newSnippet);
            viewSnippet(newSnippet);
        } else {
            view.showNoConnection();
        }
    }

    public void copyToClipboard() {
        clipboard.setText(snippet.getContent());
    }

    public void showHistory() {
        view.startActivity(Actions.showHistory());
    }
    
    public void showAuthorizationDialog() {
        clipboardAuth.authorizeClient(view);
    }
    
    
    private void viewSnippet(Snippet snippet) {
        view.setContent(snippet.getContent());
        view.setTitle(snippet.getTitle());
        view.setCreated(formatDate(snippet.getCreated()));
    }
    
    private String formatDate(Long timestamp) {
        if (timestamp != null && timestamp != 0) {
            return SimpleDateFormat.getInstance().format(new Date(timestamp));
        } else {
            return null;
        }
    }

    private Snippet getSnippetFromServer() {
        try {
            Entry entry = (Entry) services.clipboard().getSnippets(1, 0).getEntries().get(0);
        	Content content = (Content) entry.getContents().get(0);
        	
            Snippet result = new Snippet(new Long(1), content.toString(), new Long(1), "text/plain", "me", entry.getTitle());          
            
            return entry.equals(null) ? Snippet.NULL_OBJECT : result;
            
        } catch (RestClientException ex) {
            Log.w(TAG, "REST exception thrown", ex);
            return Snippet.NULL_OBJECT;
        }
    }

    private Snippet getSnippetFromCache() {
        List<Snippet> snippets = context.getDAO().getPage(0, 1, Snippet.class);
        return snippets.isEmpty() ? Snippet.NULL_OBJECT : snippets.get(0);
    }
    
}
