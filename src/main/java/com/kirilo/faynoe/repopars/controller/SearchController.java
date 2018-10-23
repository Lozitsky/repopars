package com.kirilo.faynoe.repopars.controller;

import com.kirilo.faynoe.repopars.service.CitizenService;
import com.kirilo.faynoe.repopars.util.ParsUtil;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SearchController.REST_EREP)
public class SearchController {
    static final String REST_EREP = "/search";

    private final CitizenService service;
    private final Logger logger = Logger.getLogger(SearchController.class);
    private final ParsUtil util;

    @Autowired
    public SearchController(CitizenService service) {
        this.service = service;
        this.util = new ParsUtil();
    }

    @GetMapping(value = "/{id}")
    public void getCitizens(@PathVariable int id) {
/*        List<Integer> strings = new ArrayList<>();
        for (int i = id; i < 1000; i++) {
            String webPage = "https://www.erepublik.com/en/citizen/profile/" + i;
//            Document document = util.getDocument(webPage);
            if (util.getDocument(webPage) != null) {
                strings.add(i);
            }
        }*/

        service.search(6332, id);

//        return strings;
    }
}
