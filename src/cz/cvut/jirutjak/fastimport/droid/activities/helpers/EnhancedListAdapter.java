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

package cz.cvut.jirutjak.fastimport.droid.activities.helpers;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 *
 * @param <IA> List item adapter
 */
public abstract class EnhancedListAdapter<IA> extends BaseAdapter {

    private static final String TAG = EnhancedListAdapter.class.toString();
    
    protected final Activity context;
    protected final int itemLayoutId;

    
    public EnhancedListAdapter(Activity context, int itemLayoutId) {
        this.context = context;
        this.itemLayoutId = itemLayoutId;
    }

    
    @Override
    public abstract int getCount();

    protected abstract IA newItemViewAdapter(View itemView);

    protected abstract void fillListItem(int position, IA itemAdapter);

    
    @Override
    public Object getItem(int position) {
        throw new UnsupportedOperationException("This implementation does not support getItem().");
    }

    @Override
    public long getItemId(int position) {
        Log.d(TAG, "Get item id for position: " + position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        IA itemAdapter;

        Log.d(TAG, "Get view for position: " + position);

        if (itemView == null) {
            Log.d(TAG, "Inflate item layout for position: " + position);
            itemView = context.getLayoutInflater().inflate(itemLayoutId, null, true);

            itemAdapter = newItemViewAdapter(itemView);
            itemView.setTag(itemAdapter);

        } else {
            itemAdapter = (IA) itemView.getTag();
            Log.d(TAG, "Recycle item adapter: " + itemAdapter);
        }

        fillListItem(position, itemAdapter);
        Log.d(TAG, "Filled item adapter: " + itemAdapter);

        return itemView;
    }
    
}
