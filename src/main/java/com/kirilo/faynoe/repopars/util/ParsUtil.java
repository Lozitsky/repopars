package com.kirilo.faynoe.repopars.util;

import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ParsUtil {
    private final Logger logger = Logger.getLogger(ParsUtil.class);

    public Document getDocument(String webPage) throws IOException {
//        Document document = Jsoup.connect(webPage).userAgent("Jsoup client").timeout(5000).get();
        Document document = Jsoup.connect(webPage).userAgent("Mozilla/5.0").get();
        logger.info(document.title());
        return document;
    }

    public Element getContent(Document document) {
        return document.getElementById("citizenprofile").getElementById("container")
                .getElementById("content");
    }

    public Element getContent(int id) throws IOException {
        String webPage = "https://www.erepublik.com/en/citizen/profile/" + id;
        return getContent(getDocument(webPage));
    }

    public Elements getMilitaryBox(Element content) {
        return content.getElementsByClass("citizen_military_box");
    }

    public Elements getMilitaryBoxWide(Element content) {
        return content.getElementsByClass("citizen_military_box_wide");
    }

    public Elements getMilitaryRank(Elements militaryBoxWide) {
        return militaryBoxWide.get(0).getAllElements();
    }

    public Elements getAircraftRank(Elements militaryBoxWide) {
        return militaryBoxWide.get(1).getAllElements();
    }

    public Elements getGroundElements(Elements militaryBox) {
        return militaryBox.get(0).getAllElements();
    }

    public Elements getStrengthElements(Elements militaryBox) {
        return militaryBox.get(1).getAllElements();
    }

    public Double getStrength(Elements strengthElements) throws ParseException {
//        Element strength = strengthElements.get(3);
        NumberFormat format = NumberFormat.getInstance(Locale.UK);
//        Number number = format.parse(strength.text());
        Number number = format.parse(getValueElement(strengthElements));
//        logger.info(number instanceof Double ? number.toString() : strength.text());
        return (Double) number;
    }

    public String getDivision(Elements groundElements) {
        return groundElements.get(18).text();
    }

    public String getNameElement(Elements elements) {
        return elements.get(1).text();
    }

    public String getValueElement(Elements elements) {
        return elements.get(3).text();
    }

    public String getRankName(Elements elements) {
        return elements.get(8).text();
    }
}
