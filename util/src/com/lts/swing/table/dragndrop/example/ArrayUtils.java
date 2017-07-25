/*******************************************************************************
 * Copyright 2009, Clark N. Hobbie
 * 
 * This file is part of the CLIPC library.
 * 
 * The CLIPC library is free software; you can redistribute it and/or modify it
 * under the terms of the Lesser GNU General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 * 
 * The CLIPC library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
 * License for more details.
 * 
 * You should have received a copy of the Lesser GNU General Public License
 * along with the CLIP library; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * 
 *******************************************************************************/
package com.lts.swing.table.dragndrop.example;

import java.util.Arrays;

public class ArrayUtils
{
	/**
	 * Sort an array of integers in descending order.
	 * 
	 * <P>
	 * Same as {@link Arrays#sort(int[])} except that the array is sorted with the 
	 * largest int at index 0, then the second-largest at 1, etc.  The method has 
	 * an O(n) cost on top of the cost incurred by the regular sort, since it makes 
	 * a pass over the array after sorting.
	 * </P>
	 * 
	 * @param inputArray The array to sort.
	 */
	static public void sortDescending (int[] inputArray)
	{
		Arrays.sort(inputArray);
		int low = 0;
		int high = inputArray.length - 1;
		while (low < high)
		{
			int temp = inputArray[high];
			inputArray[high] = inputArray[low];
			inputArray[low] = temp;
			low++;
			high--;
		}
	}
}
