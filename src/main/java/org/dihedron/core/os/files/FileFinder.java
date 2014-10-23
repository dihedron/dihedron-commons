/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.os.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.dihedron.core.regex.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Finds files by recursively browsing a directory an looking for files matching
 * a given name path.
 * 
 * @author Andrea Funto'
 */
public final class FileFinder {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(FileFinder.class);

	/**
	 * A class invokes as the visit proceeds; simply checks if the current item's
	 * name matches a regular expression and if stores it in an internal list.
	 *  
	 * @author Andrea Funto'
	 */
	private static class FileFinderVisitor extends SimpleFileVisitor<Path> {

		/**
		 * The pattern to match against file names.
		 */
		private Regex regex;
		
		/**
		 * The list of files to be populated; it will will be filled as a
		 * side effect (I know, this is not extra-clean, but this visitor 
		 * is a private class and there's no multi-threading involved).
		 */
		private List<File> files;
		
		/**
		 * Constructor.
		 *
		 * @param pattern
		 *   the pattern to match against file names.
		 * @param files
		 *   an output list of files matching the given name pattern.
		 */
		public FileFinderVisitor(String pattern, List<File> files) {
			regex = new Regex(pattern);
			this.files = files;
		}
		
		/**
		 * Performs the actual file-system visit, matching files against the given 
		 * pattern as it goes and storing matching ones in the given list.
		 * 
		 * @see 
		 *   java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
		 */
        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        	Path name = path.getFileName();
        	if(regex.matches(name.toString())) {
        		logger.trace("file found: {} -> {}", path.getParent(), name);
        		files.add(path.toFile());
        	}
        	return FileVisitResult.CONTINUE;
        }
    }
	
	/**
	 * Looks up files whose name matches the given pattern under the given set
	 * of directories (and, if specified, their sub-directories in a recursive way).
	 * 
	 * @param pattern
	 *   the name of the file, as a regular expression.
	 * @param recurse
	 *   whether the finder should recurse to sub-directories.
	 * @param roots
	 *   a set of one or more directories to scan.
	 * @return
	 *   a list of <code>File</code> object representing the found files.
	 */
	public static List<File> findFile(String pattern, boolean recurse, String ... roots) {
		List<File> files = new ArrayList<>();
		if(roots != null) {
			for(String root : roots) {
				try {
					Path path = FileSystems.getDefault().getPath(root);
					logger.trace("scanning directory {}", path.getFileName());
					Files.walkFileTree(path, new FileFinderVisitor(pattern, files)); 
				} catch(IOException e) {
					logger.trace("error walking directory tree", e);
				}
			}
		}		
		return files;
	}
	
	/**
	 * Private constructor, to prevent construction.
	 */
	private FileFinder() {
	}
}
