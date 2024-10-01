package com.ffa.back.dto;

import java.util.List;

public class TmdbResponseDTO {

    private int page;
    private List<TmdbItemDTO> results;
    private int totalResults;
    private int totalPages;

    // Getters y Setters

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<TmdbItemDTO> getResults() {
        return results;
    }

    public void setResults(List<TmdbItemDTO> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
