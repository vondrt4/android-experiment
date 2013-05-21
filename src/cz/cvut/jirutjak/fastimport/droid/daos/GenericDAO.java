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

import cz.cvut.jirutjak.fastimport.droid.models.Persistable;
import java.util.List;

public interface GenericDAO {

    int count(Class<? extends Persistable> clazz);

    void delete(Long id, Class<? extends Persistable> clazz);

    <E extends Persistable> E get(Long id, Class<E> clazz);

    <E extends Persistable> List<E> getAll(Class<E> clazz);

    <E extends Persistable> List<E> getPage(int first, int max, Class<E> clazz);

    boolean isPersistent(Long id, Class<? extends Persistable> clazz);

    void saveOrUpdate(Persistable object);
}
