package com.example.utils;

import java.util.LinkedHashSet;
import java.util.Set;

public class Page<T> {
    private final Set<T> elements;
    private final int pagesAmount;
    private final int currentPage;
    private final int pageSize;

    public Page(Set<T> elements, int pagesAmount, int currentPage, int pageSize) {
        this.elements = new LinkedHashSet<>(elements);
        this.pagesAmount = pagesAmount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Set<T> getElements() {
        return elements;
    }

    public int getPagesAmount() {
        return pagesAmount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }
}
