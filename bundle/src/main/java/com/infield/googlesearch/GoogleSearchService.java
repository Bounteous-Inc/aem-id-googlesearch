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

package com.infield.googlesearch;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.CustomsearchRequestInitializer;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import com.google.api.services.customsearch.model.Search.SearchInformation;
import com.infield.googlesearch.model.ResultItem;
import com.infield.googlesearch.model.ResultList;

public class GoogleSearchService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private String apikey = "";
    private String applicationname = "";
    private String cx = "";
    private long num = 7;
    private long pagesToShow = 10;

    private Long pagesRight;
    private Long pagesLeft;
    private Customsearch customsearch;
    private LinkedList<ResultItem> resultItems;

    static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1;

    public GoogleSearchService(String apikey, String applicationname, String cx) {
        this.apikey = apikey;
        this.applicationname = applicationname;
        this.cx = cx;
    }

    public ResultList getResults(String q, String currentTab, long num, long pagesToShow) {

        this.num = num;
        this.pagesToShow = pagesToShow;

        ResultList resultList = new ResultList().setResultItems(new LinkedList<ResultItem>());

        this.pagesLeft = (this.pagesToShow - (pagesToShow % 2)) / 2;
        this.pagesRight = this.pagesToShow - this.pagesLeft;

        // currentPage starts from 1
        Long currentPage = Long.valueOf(currentTab);

        Customsearch.Cse.List list;
        Search results = null;

        try {
            customsearch = new Customsearch.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, null)
                    .setApplicationName(applicationname)
                    .setGoogleClientRequestInitializer(new CustomsearchRequestInitializer(apikey))
                    .build();

            list = customsearch.cse().list(q)
                    .setNum(this.num)
                    .setStart((this.num * (currentPage - 1)) + 1)
                    .setCx(cx);

            results = list.execute();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (GeneralSecurityException e) {
            log.error(e.getMessage(), e);
        } finally {

            SearchInformation searchInformation = (results != null) ? results.getSearchInformation() : null;

            this.resultItems = new LinkedList<ResultItem>();


            if (searchInformation != null) {
                Long totalResults = searchInformation.getTotalResults();

                Long totalPages = (totalResults - (totalResults % this.num)) / this.num + 1;

                this.pagesToShow = (totalPages > this.pagesToShow) ? this.pagesToShow : totalPages;

                resultList.setTotalPages(totalPages)
                        .setPagesToShow(this.pagesToShow)
                        .setTotalResults(searchInformation.getTotalResults())
                        .setFormattedSearchTime(searchInformation.getFormattedSearchTime())
                        .setFormattedTotalResults(searchInformation.getFormattedTotalResults())
                        .setSearchTime(searchInformation.getSearchTime())
                        .setStartPage(getStartPage(currentPage))
                        .setEndPage(getEndPage(currentPage))
                        .setCurrentTab(currentPage);

                //TODO: Labels and Images
                List<Result> searchResults = results.getItems() != null ? results.getItems() : new LinkedList<Result>();

                for (Result searchResult : searchResults) {
                    ResultItem resultItem = new ResultItem();
                    resultItem.setCacheId(searchResult.getCacheId());
                    resultItem.setDisplayLink(searchResult.getDisplayLink());
                    resultItem.setFileFormat(searchResult.getFileFormat());
                    resultItem.setFormattedUrl(searchResult.getFormattedUrl());
                    resultItem.setHtmlFormattedUrl(searchResult.getHtmlFormattedUrl());
                    resultItem.setHtmlSnippet(searchResult.getHtmlSnippet());
                    resultItem.setHtmlTitle(searchResult.getHtmlTitle());
                    resultItem.setKind(searchResult.getKind());
                    //resultItem.setLabels(searchResult.getLabels());
                    resultItem.setLink(searchResult.getLink());
                    resultItem.setMime(searchResult.getMime());
                    resultItem.setSnippet(searchResult.getSnippet());
                    resultItem.setTitle(searchResult.getTitle());
                    resultItems.add(resultItem);
                }

                resultList.setResultItems(resultItems);

            }
        }

        return resultList;
    }

    private long getEndPage(long currentPage) {

        this.pagesLeft = (pagesToShow - (pagesToShow % 2)) / 2;
        this.pagesRight = pagesToShow - this.pagesLeft;

        long cp = currentPage - 1;
        long startPage = cp - this.pagesLeft;
        long endPage = cp + this.pagesRight;

        startPage = (startPage < 0) ? 0 : startPage;
        endPage = (endPage > pagesToShow - 1) ? pagesToShow - 1 : endPage;

        while ((endPage - startPage) < pagesToShow
                && (startPage == 0)
                && endPage < pagesToShow - 1) {
            endPage++;
        }

        return endPage + 1;
    }

    private long getStartPage(long currentPage) {

        this.pagesLeft = (pagesToShow - (pagesToShow % 2)) / 2;
        this.pagesRight = pagesToShow - this.pagesLeft;

        long cp = currentPage - 1;
        long startPage = cp - this.pagesLeft;
        long endPage = cp + this.pagesRight;

        startPage = (startPage < 0) ? 0 : startPage;
        endPage = (endPage > pagesToShow - 1) ? pagesToShow - 1 : endPage;

        while ((endPage - startPage) < pagesToShow
                && (endPage == pagesToShow - 1)
                && startPage > 0) {
            startPage--;
        }

        return startPage + 1;
    }
}
