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
	public ResultItem setCacheId(String cacheId) {
		this.cacheId = cacheId;
		return this;
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
	public ResultItem setDisplayLink(String displayLink) {
		this.displayLink = displayLink;
		return this;
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
	public ResultItem setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
		return this;
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
	public ResultItem setFormattedUrl(String formattedUrl) {
		this.formattedUrl = formattedUrl;
		return this;
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
	public ResultItem setHtmlFormattedUrl(String htmlFormattedUrl) {
		this.htmlFormattedUrl = htmlFormattedUrl;
		return this;
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
	public ResultItem setHtmlSnippet(String htmlSnippet) {
		this.htmlSnippet = htmlSnippet;
		return this;
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
	public ResultItem setHtmlTitle(String htmlTitle) {
		this.htmlTitle = htmlTitle;
		return this;
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
	public ResultItem setKind(String kind) {
		this.kind = kind;
		return this;
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
	public ResultItem setLabels(List <String> labels) {
		this.labels = labels;
		return this;
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
	public ResultItem setLink(String link) {
		this.link = link;
		return this;
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
	public ResultItem setMime(String mime) {
		this.mime = mime;
		return this;
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
	public ResultItem setSnippet(String snippet) {
		this.snippet = snippet;
		return this;
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
	public ResultItem setTitle(String title) {
		this.title = title;
		return this;
	}	
	

}