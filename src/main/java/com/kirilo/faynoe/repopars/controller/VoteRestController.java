package com.kirilo.faynoe.repopars.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@RestController
@RequestMapping(VoteRestController.REST_EREP)
public class VoteRestController {
    static final String REST_EREP = "/erep";

    @GetMapping(value = "/{id}")
    public String getErep(@PathVariable int id) throws IOException, ParseException {

        String webPage = "https://www.erepublik.com/en/citizen/profile/" + id;

//        Document document = Jsoup.connect(webPage).userAgent("Jsoup client").timeout(5000).get();
        Document document = Jsoup.connect(webPage).userAgent("Mozilla/5.0").get();

        System.out.println(document.title());
        Elements elements = document.getElementById("citizenprofile").getElementById("container")
                .getElementById("content").getElementsByClass("citizen_military_box")
                .tagName("citizen_military_box");
        Element element = elements.get(1);
        Elements allElements = element.getAllElements();
        Element element3 = allElements.get(3);

        NumberFormat format = NumberFormat.getInstance(Locale.UK);
        Number number = format.parse(element3.text());

        return number instanceof Double ? number.toString() : element3.text();
    }
}
