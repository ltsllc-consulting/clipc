//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the util library.
//
//  The util library is free software; you can redistribute it and/or modify it
//  under the terms of the Lesser GNU General Public License as published by
//  the Free Software Foundation; either version 2.1 of the License, or (at
//  your option) any later version.
//
//  The util library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
//  or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
//  License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the util library; if not, write to the Free Software Foundation,
//  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
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

package com.lts.lang.classloader;
// XXX move it to util
//package org.apache.java.io;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Class that implements the java.io.FilenameFilter
 * interface.
 *
 * @author <a href="mailto:mjenning@islandnet.com">Mike Jennings</a>
 * @version $Revision: 1.1 $
 */
public class SimpleFileFilter 
	implements FilenameFilter 
{
     private String[] extensions;

     public SimpleFileFilter(String ext)
     {
         this(new String[]{ext});
     }

     public SimpleFileFilter(String[] exts) 
     {
         extensions=new String[exts.length];
         for (int i=0;i<exts.length;i++) 
         {
             extensions[i]=exts[i].toLowerCase();
         }
     }

     /** filenamefilter interface method */
     public boolean accept(File dir,String _name) 
     {
         String name=_name.toLowerCase();
         for (int i=0;i<extensions.length;i++) 
         {
             if (name.endsWith(extensions[i])) return true;
         }
         return false;
     }

     /** 
      * this method checks to see if an asterisk
      * is imbedded in the filename, if it is, it
      * does an "ls" or "dir" of the parent directory
      * returning a list of files that match
      * eg. /usr/home/mjennings/*.jar
      * would expand out to all of the files with a .jar
      * extension in the /usr/home/mjennings directory 
      */
    public static String[] fileOrFiles(File f) 
    {
        if (f==null) 
            return null;
            
        File parent=new File(f.getParent());
        String fname=f.getName();
        String[] files;
        if (fname.charAt(0)=='*') 
        {
            String filter=fname.substring(1,fname.length());
            files=parent.list(new SimpleFileFilter(filter));
            return files;
        } 
        else 
        {
            files=new String[1];
            files[0]=f.getPath();// was:fname;
            return files;
        }
    }
}
