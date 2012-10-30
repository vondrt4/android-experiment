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

package cz.cvut.jirutjak.fastimport.droid.controllers;

import android.widget.BaseAdapter;
import com.googlecode.androidannotations.annotations.App;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import cz.cvut.jirutjak.fastimport.droid.Actions;
import cz.cvut.jirutjak.fastimport.droid.AppContext;
import cz.cvut.jirutjak.fastimport.droid.activities.HistoryActivity;
import cz.cvut.jirutjak.fastimport.droid.activities.HistoryActivity.ItemViewAdapter;
import cz.cvut.jirutjak.fastimport.droid.models.Snippet;
import cz.cvut.jirutjak.fastimport.droid.rest.RESTServicesFacade;
import java.util.Date;
import java.util.List;
import org.springframework.web.client.RestClientException;

@EBean
public class HistoryController {

    private List<Snippet> snippets;
    
    @App
    protected AppContext context;
    
    @RootContext
    protected HistoryActivity view;
    
    @Bean
    protected RESTServicesFacade services;

    
    public void initialize() {
        syncSnippets();
        snippets = getSnippetsFromCache();
    }
    

    public int getSnippetsCount() {
        return snippets.size();
    }

    public void fillListItem(int positon, ItemViewAdapter itemView) {
        Snippet snippet = snippets.get(positon);

        itemView.setTitle(snippet.getTitle());
        itemView.setDate(new Date(snippet.getCreated()));
    }

    public void deleteSnippet(int position) {
        Snippet snippet = snippets.get(position);
        BaseAdapter adapter = (BaseAdapter) view.getListAdapter();

        try {
            services.clipboard().deleteSnippet(snippet.getId());
            context.getDAO().delete(snippet.getId(), Snippet.class);
            snippets.remove(position);
            adapter.notifyDataSetChanged();

        } catch (RestClientException ex) {
            view.showNoConnection();
        }
    }

    public void showSnippet(int position) {
        Snippet snippet = snippets.get(position);
        view.startActivity(Actions.showSnippet(snippet));
    }

    private List<Snippet> getSnippetsFromCache() {
        return context.getDAO().getAll(Snippet.class);
    }

    //FIXME! stupid, but don't have time...
    private void syncSnippets() {
        try {
            List<Snippet> snippets = services.clipboard().getSnippets().values();
            for (Snippet snippet : snippets) {
                if (!context.getDAO().isPersistent(snippet.getId(), Snippet.class)) {
                    context.getDAO().saveOrUpdate(snippet);
                }
            }

        } catch (RestClientException ex) {
            view.showNoConnection();
        }
    }
}
