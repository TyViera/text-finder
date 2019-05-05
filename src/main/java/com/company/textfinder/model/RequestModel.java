package com.company.textfinder.model;

import java.util.List;

public class RequestModel {

    private String searchText;
    List<ProductModel> collectionText;

    public RequestModel() {
    }

    public RequestModel(String searchText, List<ProductModel> collectionText) {
        this.searchText = searchText;
        this.collectionText = collectionText;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public List<ProductModel> getCollectionText() {
        return collectionText;
    }

    public void setCollectionText(List<ProductModel> collectionText) {
        this.collectionText = collectionText;
    }

}
