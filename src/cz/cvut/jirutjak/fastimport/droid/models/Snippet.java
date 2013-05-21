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

package cz.cvut.jirutjak.fastimport.droid.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Snippet implements Serializable, Persistable {
    
    public static final Snippet NULL_OBJECT = new Snippet();

    private Long id;
    private String content;
    private Long created;
    private String mediaType;
    private String owner;
    private String title;
    
    public Snippet(Long id, String content, Long created, String mediaType,
			String owner, String title) {
		super();
		this.id = id;
		this.content = content;
		this.created = created;
		this.mediaType = mediaType;
		this.owner = owner;
		this.title = title;
	}
    
	public Snippet() {
		super();
	}

	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getCreated() { return created; }
    public void setCreated(Long created) { this.created = created; }

    public String getMediaType() { return mediaType; }
    public void setMediaType(String mediaType) { this.mediaType = mediaType; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    
    
    public static class SnippetsList {
        private List<Snippet> entries;

        public SnippetsList() {
            this.entries = new ArrayList<Snippet>();
        }
        public SnippetsList(List<Snippet> values) {
            this.entries = values;
        }

        public List<Snippet> values() {
            return entries;
        }
    }
}
