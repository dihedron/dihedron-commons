/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.cache.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dihedron.core.regex.Regex;
import org.dihedron.patterns.cache.Storage;

/**
 * This class provides support for iteration on the storage resources; sub-classes
 * can override the {@link #iterator()} and {@link #iterator(Regex)} methods if
 * they have a more efficient solution to the generation of the resources list,
 * or if they want to provide a custom or more specialised iterator instead of 
 * the stock one.
 * 
 * @author Andrea Funto'
 */
public abstract class AbstractStorage implements Storage {

	/**
	 * @see org.dihedron.patterns.cache.Storage#iterator()
	 */
	@Override
	public Iterator<String> iterator() {
		return new StorageIterator(list(null));
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#iterator(org.dihedron.core.regex.Regex)
	 */
	@Override
	public Iterator<String> iterator(Regex regex) {
		return new StorageIterator(list(regex));
	}
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#size()
	 */
	@Override
	public long size() {
		String [] list = list(null);
		return (list == null) ? 0 : list.length;
	}
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#list()
	 */
	@Override
	public String [] list() {
		return list(null);
	}
	
	/**
	 * An iterator class supporting simple iteration over the names of the resources
	 * in the storage.
	 * 
	 * @author Andrea Funto'
	 */
	protected class StorageIterator implements Iterator<String> {

		/**
		 * The list of the names of resources in the storage.
		 */
		private List<String> resources = null;
		
		/**
		 * An iterator over the list of names; it's this object that keeps status 
		 * across invocations to {@link #next()}.
		 */
		private Iterator<String> iterator = null;
		
		/**
		 * Constructor.
		 *
		 * @param resources
		 *   the list of the names of resources in the storage.
		 */
		protected StorageIterator(List<String> resources) {
			this.resources = resources;
			this.iterator = this.resources.iterator();
		}
		
		/**
		 * Constructor.
		 *
		 * @param resources
		 *   an array containing the names of resources in the storage.
		 */
		protected StorageIterator(String[] resources) {
			this.resources = new ArrayList<String>();
			if(resources != null) {
				for(String resource : resources) {
					this.resources.add(resource);
				}
			}
			this.iterator = this.resources.iterator();	
		}
		
		/**
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
	    public boolean hasNext() {	    
	        return iterator.hasNext();
	    }

		/**
		 * @see java.util.Iterator#next()
		 */
		@Override
	    public String next() {
	        return iterator.next();
	    }

		/**
		 * @see java.util.Iterator#remove()
		 */
		@Override
	    public void remove() {
	        throw new UnsupportedOperationException("resource deletion during iteration is not supported");
	    }
	}	
}
