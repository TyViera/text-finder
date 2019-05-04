package com.company.textfinder.web;

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
    public List<String> searchTextCaseSensitive(@RequestBody Map<String, Object> body) {
        String searchText = (String) body.get("searchText");
        List<String> collectionText = (List<String>) body.get("collectionText");
        return textFinderService.findText(searchText, collectionText);
    }

    @PostMapping("/case-insensitive")
    public List<String> searchTextCaseInsensitive(@RequestBody Map<String, Object> body) {
        String searchText = (String) body.get("searchText");
        List<String> collectionText = (List<String>) body.get("collectionText");
        return textFinderService.findText(searchText, collectionText, Boolean.FALSE);
    }

}
