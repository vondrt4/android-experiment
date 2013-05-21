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

package cz.cvut.jirutjak.fastimport.droid.daos;

import android.content.Context;
import com.google.gson.Gson;
import cz.cvut.jirutjak.fastimport.droid.models.Persistable;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * I think that SQLite is little overkill for caching snippets so I tried to
 * implement simple JSON storage on file system. It was only an experiment ;-)
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
public class FileStorageGenericDAO implements GenericDAO {

    private final Gson gson = new Gson();
    private final Context context;

    
    public FileStorageGenericDAO(Context context) {
        this.context = context;
    }
    

    @Override
    public int count(Class<? extends Persistable> clazz) {
        return getDirectory(clazz).list().length;
    }

    @Override
    public void delete(Long id, Class<? extends Persistable> clazz) {
        getFile(id, clazz).delete();

    }

    @Override
    public <E extends Persistable> E get(Long id, Class<E> clazz) {
        return read(id, clazz);
    }

    @Override
    public <E extends Persistable> List<E> getAll(Class<E> clazz) {

        List<E> objects = new ArrayList<E>();
        File[] files = getSortedFiles(getDirectory(clazz), false);

        for (File file : files) {
            objects.add(read(file, clazz));
        }

        return objects;
    }

    @Override
    public <E extends Persistable> List<E> getPage(int first, int max, Class<E> clazz) {

        List<E> objects = new ArrayList<E>();
        File[] files = getSortedFiles(getDirectory(clazz), false);

        if (first <= 0) {
            first = 0;
        }
        if (max <= 0 || first + max > files.length) {
            max = files.length - first;
        }

        for (int i = first; i < max; i++) {
            objects.add(read(files[i], clazz));
        }
        return objects;
    }

    @Override
    public boolean isPersistent(Long id, Class<? extends Persistable> clazz) {
        return getFile(id, clazz).exists();
    }

    @Override
    public void saveOrUpdate(Persistable object) {
        write(object);
    }

    private File getFile(Long id, Class<?> clazz) {
        String filename = id.toString();
        return new File(getDirectory(clazz), filename);
    }

    private File getDirectory(Class<?> clazz) {
        File root = context.getCacheDir();
        File dir = new File(root, clazz.getSimpleName());
        dir.mkdir();

        return dir;
    }

    private File[] getSortedFiles(File dir, boolean ascending) {
        File[] files = dir.listFiles();
        final int order = ascending ? 1 : -1;

        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return f1.getName().compareTo(f2.getName()) * order;
            }
        });
        return files;
    }

    private void write(Persistable object) {
        File file = getFile(object.getId(), object.getClass());

        try {
            file.createNewFile();
            FileWriter writer = null;
            try {
                writer = new FileWriter(file);

                gson.toJson(object, writer);

            } finally {
                writer.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private <E extends Persistable> E read(Long id, Class<E> clazz) {
        File file = getFile(id, clazz);
        return read(file, clazz);
    }

    private <E extends Persistable> E read(File file, Class<E> clazz) {
        if (!file.exists()) return null;

        try {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));

                return gson.fromJson(reader, clazz);

            } finally {
                reader.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
