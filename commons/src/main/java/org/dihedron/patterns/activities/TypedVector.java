/**
 * Copyright (c) 2013, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Activities library ("Activities").
 *
 * Activities is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * Activities is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with Activities. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.patterns.activities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class TypedVector<E> extends java.util.Vector<E> {
	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = -3004673951241132829L;
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(TypedVector.class);

	/**
	 * Constructor.
	 */
	public TypedVector() {
		super();
	}
	
	/**
	 * Constructs a vector containing the elements of the specified collection, 
	 * in the order they are returned by the collection's iterator.
	 *
	 * @param collection
	 *   a collection of elements of type that can be cast to {@code E}.
	 */
	public TypedVector(Collection<? extends E> collection) {
		super(collection);
	}
	
	/**
	 * Constructs an empty vector with the specified initial capacity and with 
	 * its capacity increment equal to zero.
	 *
	 * @param initialCapacity
	 *   the initial capacity of the vector, in terms of number of elements.
	 */
	public TypedVector(int initialCapacity) {
		super(initialCapacity);
	}
	
	/**
	 * Constructs an empty vector with the specified initial capacity and capacity 
	 * increment.
	 *
	 * @param initialCapacity
	 *   the initial capacity of the vector, in terms of number of elements.
	 * @param capacityIncrement
	 *   the amount by which the vector will increase its capacity when needed.
	 */
	public TypedVector(int initialCapacity, int capacityIncrement) {
		super(initialCapacity, capacityIncrement);
	}
	
	/**
	 * Inserts the specified element at the specified position in this Vector.
	 * 
	 * @param index
	 *   the index can be a positive number, or a negative number that is smaller 
	 *   than the size of the vector; see {@link #getRealIndex(int)}.   
	 * @see java.util.Vector#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, E element) {
		int idx = getRealIndex(index);
		super.add(idx, element);
	}
	
	/**
	 * Inserts all of the elements in the specified Collection into this Vector 
	 * at the specified position.
	 * 
	 * @param index
	 *   the index can be a positive number, or a negative number that is smaller 
	 *   than the size of the vector; see {@link #getRealIndex(int)}. 
	 * @return
	 *   {@code true} if this vector changed as a result of the call.
	 * @see 
	 *   java.util.Vector#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
		int idx = getRealIndex(index);
		return super.addAll(idx, collection);
	}
	
	/**
	 * Returns the component at the specified index.
	 * 
	 * @param index
	 *   the index can be a positive number, or a negative number that is smaller 
	 *   than the size of the vector; see {@link #getRealIndex(int)}. 
	 * @return
	 *   the element at the specified index.
	 * @see java.util.Vector#elementAt(int)
	 */
	@Override
	public E elementAt(int index) {
		int idx = getRealIndex(index);
		return super.elementAt(idx);
	}
	
	/**
	 * Returns the element at the specified position in this Vector.
	 * 
	 * @param index
	 *   the index can be a positive number, or a negative number that is smaller 
	 *   than the size of the vector; see {@link #getRealIndex(int)}. 
	 * @return
	 *   the element at the specified index.
	 * @see java.util.Vector#get(int)
	 */
	@Override
	public E get(int index) {
		int idx = getRealIndex(index);
		return super.get(idx);
	}
	
	/**
	 * Returns the index of the first occurrence of the specified element in 
	 * this vector, searching forwards from index, or returns -1 if the element 
	 * is not found.
	 * 
	 * @param o
	 *   the object to look for.
	 * @param index
	 *   the index from which to start the lookup; it can be a positive number, 
	 *   or a negative number that is smaller than the size of the vector; see 
	 *   {@link #getRealIndex(int)}. 
	 * @see java.util.Vector#indexOf(java.lang.Object, int)
	 */
	@Override
	public int indexOf(Object o, int index) {
		int idx = getRealIndex(index);
		return super.indexOf(o, idx);
	}
	
	/**
	 * Inserts the specified object as a component in this vector at the specified index.
	 * 
	 * @param obj
	 *   the element to insert.
	 * @param index
	 *   the index at which the element should be inserted; it can be a positive 
	 *   number, or a negative number that is smaller than the size of the vector; 
	 *   see {@link #getRealIndex(int)}. 
	 * @see java.util.Vector#insertElementAt(java.lang.Object, int)
	 */
	@Override 
	public void insertElementAt(E obj, int index) {
		int idx = getRealIndex(index);
		super.insertElementAt(obj, idx);
	}
	
	/**
	 * Returns the index of the last occurrence of the specified element in this 
	 * vector, searching backwards from index, or returns -1 if the element is 
	 * not found.
	 * 
	 * @param o
	 *   the object to look for.
	 * @param index
	 *   the index at which the element lookup should start; it can be a positive 
	 *   number, or a negative number that is smaller than the size of the vector; 
	 *   see {@link #getRealIndex(int)}. 
	 * @see java.util.Vector#lastIndexOf(java.lang.Object, int)
	 */
	@Override 
	public int lastIndexOf(Object o, int index) {
		int idx = getRealIndex(index);
		return super.lastIndexOf(o, idx);
	}
	
	/**
	 * Returns a list iterator over the elements in this list (in proper sequence), 
	 * starting at the specified position in the list.
	 * 
	 * @param index
	 *   the index to start from; it can be a positive number, or a negative 
	 *   number that is smaller than the size of the vector; for details see 
	 *   {@link #getRealIndex(int)}. 
	 * @see java.util.Vector#listIterator(int)
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		int idx = getRealIndex(index);
		return super.listIterator(idx);
	}
	
	/**
	 * Removes the element at the specified position in this Vector.
	 * 
	 * @param index
	 *   the index of the element to remove; it can be a positive number, or a 
	 *   negative number that is smaller than the size of the vector; see 
	 *   {@link #getRealIndex(int)}. 
	 * @see java.util.Vector#remove(int)
	 */
	@Override
	public E remove(int index) {
		int idx = getRealIndex(index);
		return super.remove(idx);
	}
	
	/**
	 * Deletes the component at the specified index.
	 * 
	 * @param index
	 *   the index of the element to remove; it can be a positive number, or a 
	 *   negative number that is smaller than the size of the vector; see 
	 *   {@link #getRealIndex(int)}. 
	 * @see java.util.Vector#removeElementAt(int)
	 */
	@Override
	public void removeElementAt(int index) {
		int idx = getRealIndex(index);
		super.removeElementAt(idx);
	}
	
	/**
	 * Removes from this list all of the elements whose index is between fromIndex, 
	 * inclusive, and toIndex, exclusive; both indexes can be negative; for 
	 * details see {@link #getRealIndex(int)}.
	 * 
	 * @param fromIndex
	 *   the index to start from, inclusive.
	 * @param toIndex
	 *   the index to stop at, exclusive.
	 * @see java.util.Vector#removeRange(int, int)
	 */
	@Override
	public void removeRange(int fromIndex, int toIndex) {
		int fromIdx = getRealIndex(fromIndex);
		int toIdx = getRealIndex(toIndex);
		super.removeRange(fromIdx, toIdx);
	}
	
	/**
	 * Replaces the element at the specified position in this Vector with the 
	 * specified element.
	 * 
	 * @param index
	 *   the index at which the element will be placed; it can be a positive 
	 *   number, or a negative number that is smaller than the size of the vector; 
	 *   see {@link #getRealIndex(int)}. 
	 * @param element
	 *   the new value to store at the specified index.
	 * @return
	 *   the previous element at the specified index.
	 * @see java.util.Vector#set(int, java.lang.Object)
	 */
	@Override
	public E set(int index, E element) {
		int idx = getRealIndex(index);
		return super.set(idx, element);
	}
	
	/**
	 * Sets the component at the specified index of this vector to be the 
	 * specified object.
	 * 
	 * @param index
	 *   the index at which the element will be placed; it can be a positive 
	 *   number, or a negative number that is smaller than the size of the vector; 
	 *   see {@link #getRealIndex(int)}. 
	 * @param element
	 *   the new value to store at the specified index.
	 * @see java.util.Vector#setElementAt(java.lang.Object, int)
	 */
	@Override
	public void setElementAt(E obj, int index) {
		int idx = getRealIndex(index);
		super.setElementAt(obj, idx);
	}

	/**
	 * Returns a view of the portion of this List between fromIndex, inclusive, 
	 * and toIndex, exclusive; both indexes can be negative; for further 
	 * details see {@link #getRealIndex(int)}.
	 * 
	 * @param fromIndex
	 *   the index to start from, inclusive.
	 * @param toIndex
	 *   the index to stop at, exclusive.
	 * @see java.util.Vector#subList(int, int)
	 */
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		int fromIdx = getRealIndex(fromIndex);
		int toIdx = getRealIndex(toIndex);
		return super.subList(fromIdx, toIdx);		
	}
	
	/**
	 * Returns the vector as a {@link java.util.List}.
	 * 
	 * @return
	 *   the vector as a list.
	 */
	public List<E> toList() {
		return new ArrayList<E>(this);
	}
	
	/**
	 * Returns the positive value corresponding to the given positive or negative
	 * index value.
	 * 
	 * @param index
	 *   the positive or negative index value; if negative, its absolute value
	 *   must be less than the size of the vector, otherwise an exception is 
	 *   thrown. For small negative numbers, the index will be calculated as the
	 *   size of the vector minus the absolute value of the index, thus -1 
	 *   identifies the last element in the list, -2 the element before it, and 
	 *   so on.
	 * @return
	 *   the positive value corresponding to the given index.
	 */
	private int getRealIndex(int index) {
		int idx = index;
		if(idx < 0) {
			if(Math.abs(idx) <= this.size()) {
				idx = this.size() + idx;
			} else {
				logger.error("negative number {} is too large: results size is {}, maximum admitted value is {}", index, this.size(), -1 * this.size() + 1);
				throw new IndexOutOfBoundsException("Negative index is too large");
			}
		}
		return idx;
	}	
	
	
}
