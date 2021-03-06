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

import android.content.Intent;
import cz.cvut.jirutjak.fastimport.droid.activities.HistoryActivity;
import cz.cvut.jirutjak.fastimport.droid.activities.MainActivity;
import cz.cvut.jirutjak.fastimport.droid.models.Snippet;

public class Actions {

    private static final String PACKAGE = "cz.cvut.jirutjak.fastimport.droid";

    public static Intent showSnippet(Snippet snippet) {
        return newIntentFor(MainActivity.class).putExtra(Data.SNIPPET, snippet);
    }

    public static Intent showHistory() {
        return newIntentFor(HistoryActivity.class);
    }

    public static Intent newIntentFor(Class<?> targetClass) {
        return new Intent().setClassName(PACKAGE, targetClass.getName() + "_");
    }
}
