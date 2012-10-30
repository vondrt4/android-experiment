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

package cz.cvut.jirutjak.fastimport.droid.activities;

import android.app.ListActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ItemClick;
import cz.cvut.jirutjak.fastimport.droid.R;
import cz.cvut.jirutjak.fastimport.droid.activities.helpers.EnhancedListAdapter;
import cz.cvut.jirutjak.fastimport.droid.controllers.HistoryController;
import java.util.Date;

@EActivity(R.layout.history)
public class HistoryActivity extends ListActivity {

    private BaseAdapter adapter;
    
    @Bean
    protected HistoryController controller;
    

    //--------------- Initialization ---------------//
    @AfterViews
    protected void initialize() {
        controller.initialize();
        adapter = new EnhancedListAdapter<ItemViewAdapter>(this, R.layout.history_list_item) {
            public int getCount() {
                return controller.getSnippetsCount();
            }
            protected void fillListItem(int position, ItemViewAdapter adapter) {
                controller.fillListItem(position, adapter);
            }
            protected ItemViewAdapter newItemViewAdapter(View itemView) {
                return new ItemViewAdapter(itemView);
            }
        };
        setListAdapter(adapter);

        registerForContextMenu(getListView());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        getMenuInflater().inflate(R.menu.history_list_ctx, menu);
    }

    public void showNoConnection() {
        Toast.makeText(this, R.string.no_connection, Toast.LENGTH_SHORT).show();
    }

    
    //--------------- Events handling ---------------//
    
    @ItemClick
    protected void listItemClicked(int position) {
        controller.showSnippet(position);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.history_list_delete:
                controller.deleteSnippet(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    
    
    //--------------- Adapters ---------------//
    
    public static class ItemViewAdapter {
        private TextView titleView;
        private TextView dateView;

        private ItemViewAdapter(View itemView) {
            titleView = (TextView) itemView.findViewById(R.id.history_item_title);
            dateView = (TextView) itemView.findViewById(R.id.history_item_date);
        }

        public void setTitle(String title) {
            titleView.setText(title);
        }
        public void setDate(Date date) {
            dateView.setText(date.toString());
        }
        @Override
        public String toString() {
            return titleView.getText().toString();
        }
    }
    
}
