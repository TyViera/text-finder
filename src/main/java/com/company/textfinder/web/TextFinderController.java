package com.company.textfinder.web;

import com.company.textfinder.model.ProductModel;
import com.company.textfinder.model.RequestModel;
import com.company.textfinder.service.TextFinderService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class TextFinderController {

    @Autowired
    private TextFinderService textFinderService;

    @PostMapping("/case-sensitive")
    public List<ProductModel> searchTextCaseSensitive(@RequestBody RequestModel requestModel) {
        return textFinderService.findText(requestModel.getSearchText(), requestModel.getCollectionText());
    }

    @PostMapping("/case-insensitive")
    public List<ProductModel> searchTextCaseInsensitive(@RequestBody RequestModel requestModel) {
        return textFinderService.findText(requestModel.getSearchText(), requestModel.getCollectionText(), Boolean.FALSE);
    }

}
