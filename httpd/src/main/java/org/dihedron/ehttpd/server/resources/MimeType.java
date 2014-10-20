/**
 * Copyright (c) 2011, 2013, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Dihedron embeddable web container ("e-httpd").
 *
 * "e-httpd" is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * "e-httpd" is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with "e-httpd". If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.ehttpd.server.resources;

import org.dihedron.core.regex.Regex;

/**
 * @author Andrea Funto'
 */
public enum MimeType {
	
	/**
	 * A binary object (executable, Java class...)
	 */
	BINARY("^[\\w-]+\\.(?:class|exe|bin)$", "application/octet-stream"),

	/**
	 * A plain text file.
	 */
	TEXT("^[\\w-]+\\.?:txt|asc)$", "text/plain"),
	
	/**
	 * An XML Document Type Definition (DTD).
	 */
	DTD("^[\\w-]+\\.dtd$", "application/xml-dtd"),
	
	/**
	 * An XML document (including XSL, XSD).
	 */
	XML("^[\\w-]+\\.(?:xml|xsl|xsd)$", "application/xml"),
	
	/**
	 * An XML transformation sheet (XSLT).
	 */
	XSLT("^[\\w-]+\\.xslt$", "application/xslt+xml"),
	
	/**
	 * An Atom feed.
	 */
	ATOM("^[\\w-]+\\.atom$", "application/atom+xml"),
	
	/**
	 * A cascaded style sheet (CSS).
	 */
	CSS("^[\\w-]+\\.css$", "text/css"),
	
	/**
	 * An HTML page.
	 */
	HTML("^[\\w-]+\\.(?htm|html)$", "text/html"),
	
	/**
	 * An XHTML page.
	 */
	XHTML("^[\\w-]+\\.xhtml$", "application/xhtml+xml"),
	
	/**
	 * A JavaScript program.
	 */
	JS("^[\\w-]+\\.js$", "application/x-javascript"),
	
	/**
	 * A Dart script.
	 */
	DART("^[\\w-]+\\.dart$", "application/dart"),

	/**
	 * A Bitmap image.
	 */
	BMP("^[\\w-]+\\.bmp$", "image/bmp"),
	
	/**
	 *  A GIF image.
	 */
	GIF("^[\\w-]+\\.gif$", "image/gif"),
	
	/**
	 * An icon.
	 */
	ICO("^[\\w-]+\\.ico$", "image/x-icon"),
	
	/**
	 * A JPEG image.
	 */
	JPEG("^[\\w-]+\\.(?:jpe|jpeg|jpg)$", "image/jpeg"),
	
	/**
	 * A Portable Network Graphics (PNG) image.
	 */
	PNG("^[\\w-]+\\.png$", "image/png"),
	
	/**
	 * An SVG image.
	 */
	SVG("^[\\w-]+\\.svg$", "image/svg+xml"),
	
	/**
	 * A TIFF image.
	 */
	TIFF("^[\\w-]+\\.(?:tif|tiff)$", "image/tiff"),
	
	/**
	 * A PDF document.
	 */
	PDF("^[\\w-]+\\.pdf$", "application/pdf"),
	
	/**
	 * A PostScript (PS) document.
	 */
	PS("^[\\w-]+\\.ps$", "application/postscript"),
	
	/**
	 * A Microsoft Word (R) document.
	 */
	MSWORD("^[\\w-]+\\.doc$", "application/msword"),
	
	/**
	 * A Microsoft Excel (R) sheet.
	 */
	MSEXCEL("^[\\w-]+\\.xls$", "application/vnd.ms-excel"),
	
	/**
	 * A microsoft PowerPoint (R) presentation.
	 */
	MSPOWERPOINT("^[\\w-]+\\.ppt$", "application/vnd.ms-powerpoint"),
	
	/**
	 * An MP3 audio file.
	 */
	MP3("^[\\w-]+\\.mp3$", "audio/mpeg"),
	
	/**
	 * A WAVE audio file.
	 */
	WAV("^[\\w-]+\\.wav$", "audio/x-wav"),
	
	/**
	 * An AVI video.
	 */
	AVI("^[\\w-]+\\.avi$", "video/x-msvideo"),

	/**
	 * An MP4 video.
	 */
	MP4("^[\\w-]+\\.mp4$", "video/mp4"),
	
	/**
	 * An MPEG video.
	 */	
	MPEG("^[\\w-]+\\.(?:mpe|mpeg|mpg)$", "video/mpeg"),
	
	/**
	 * An OGG stream.
	 */
	OGG("^[\\w-]+\\.ogg$", "application/ogg"),
	
	/**
	 * A QuikTime (R) video.
	 */	
	QUICKTIME("^[\\w-]+\\.(?:qt|mov)$", "video/quicktime"),
	
	/**
	 * A ShockWave Flash (R) video.
	 */
	SWF("^[\\w-]+\\.swf$", "application/x-shockwave-flash"),
	
	/**
	 * A ZIP archive.
	 */
	ZIP("^[\\w-]+\\.zip$", "application/zip");

	/**
	 * The regular expression matching the resource name.
	 */
	private Regex regex;
	
	/**
	 * The MIME content type.
	 */
	private String contentType;

	/**
	 * Retrieves the MimeType for the given resource, by matching by name.
	 * 
	 * @param resource
	 *   the name of the resource whose MIME type is to be detected.
	 * @return
	 *   the MIME type, or null if none matches.
	 */
	public static MimeType getMimeType(String resource) {
		for(MimeType mime : MimeType.values()) {
			if(mime.regex.matches(resource)){
				return mime;			
			}
		}
		return null;
	}
	
	/**
	 * Returns the content type, as a string. 
	 * 
	 * @return
	 *   the content type, as a string.
	 */
	public String getContentType() {
		return contentType;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param regex
	 *   the regular expression used to identify the given resource.
	 * @param contentType
	 *   the HTML content type.
	 */
	private MimeType(String regex, String contentType) {
		this.regex = new Regex(regex, false);
		this.contentType = contentType;
	}
}
