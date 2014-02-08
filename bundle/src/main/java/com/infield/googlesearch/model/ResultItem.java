/*************************************************************************
 * Copyright 2014 Infield Design
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Gaetan Marmasse <gaetan@infielddesign.com>
 * 
 *************************************************************************/

package com.infield.googlesearch.model;

import java.util.List;

public class ResultItem {
	
	private String cacheId;
	private String displayLink;
	private String fileFormat;
	private String formattedUrl;
	private String htmlFormattedUrl;
	private String htmlSnippet;
	private String htmlTitle;
	//private String image;
	private String kind;
	private List <String> labels;
	private String link;
	private String mime;
	//pageMap
	private String snippet;
	private String title;
	
	/**
	 * @return the cacheId
	 */
	public String getCacheId() {
		return cacheId;
	}
	/**
	 * @param cacheId the cacheId to set
	 */
	public void setCacheId(String cacheId) {
		this.cacheId = cacheId;
	}
	/**
	 * @return the displayLink
	 */
	public String getDisplayLink() {
		return displayLink;
	}
	/**
	 * @param displayLink the displayLink to set
	 */
	public void setDisplayLink(String displayLink) {
		this.displayLink = displayLink;
	}
	/**
	 * @return the fileFormat
	 */
	public String getFileFormat() {
		return fileFormat;
	}
	/**
	 * @param fileFormat the fileFormat to set
	 */
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	/**
	 * @return the formattedUrl
	 */
	public String getFormattedUrl() {
		return formattedUrl;
	}
	/**
	 * @param formattedUrl the formattedUrl to set
	 */
	public void setFormattedUrl(String formattedUrl) {
		this.formattedUrl = formattedUrl;
	}
	/**
	 * @return the htmlFormattedUrl
	 */
	public String getHtmlFormattedUrl() {
		return htmlFormattedUrl;
	}
	/**
	 * @param htmlFormattedUrl the htmlFormattedUrl to set
	 */
	public void setHtmlFormattedUrl(String htmlFormattedUrl) {
		this.htmlFormattedUrl = htmlFormattedUrl;
	}
	/**
	 * @return the htmlSnippet
	 */
	public String getHtmlSnippet() {
		return htmlSnippet;
	}
	/**
	 * @param htmlSnippet the htmlSnippet to set
	 */
	public void setHtmlSnippet(String htmlSnippet) {
		this.htmlSnippet = htmlSnippet;
	}
	/**
	 * @return the htmlTitle
	 */
	public String getHtmlTitle() {
		return htmlTitle;
	}
	/**
	 * @param htmlTitle the htmlTitle to set
	 */
	public void setHtmlTitle(String htmlTitle) {
		this.htmlTitle = htmlTitle;
	}
	/**
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}
	/**
	 * @param kind the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}
	/**
	 * @return the labels
	 */
	public List <String> getLabels() {
		return labels;
	}
	/**
	 * @param labels the labels to set
	 */
	public void setLabels(List <String> labels) {
		this.labels = labels;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the mime
	 */
	public String getMime() {
		return mime;
	}
	/**
	 * @param mime the mime to set
	 */
	public void setMime(String mime) {
		this.mime = mime;
	}
	/**
	 * @return the snippet
	 */
	public String getSnippet() {
		return snippet;
	}
	/**
	 * @param snippet the snippet to set
	 */
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}	
	

}
