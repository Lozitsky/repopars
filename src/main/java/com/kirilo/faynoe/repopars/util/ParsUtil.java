package com.kirilo.faynoe.repopars.util;

import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ParsUtil {
    private final Logger logger = Logger.getLogger(ParsUtil.class);
    private final NumberFormat format = NumberFormat.getInstance(Locale.UK);

    public ParsUtil() {
    }

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


/*    public Element getContent(int id) throws IOException {
        String webPage = "https://www.erepublik.com/en/citizen/profile/" + id;
        return getContent(getDocument(webPage));
    }*/
    public Elements getMilitaryBox(Element content) {
        return content.getElementsByClass("citizen_military_box");
    }

    public Elements getMilitaryBoxWide(Element content) {
        return content.getElementsByClass("citizen_military_box_wide");
    }

    public Elements getCitizenSecond(Element content) {
        return content.getElementsByClass("citizen_second").get(0).getAllElements();
    }

    public int getCitizenLevel(Element content) {
        String citizen_level = content.getElementsByClass("citizen_level").get(0).text();
        return Integer.parseInt(citizen_level);
    }

    public String getOriginalTitle(Element content) {
        String text = content.getElementsByClass("citizen_level").tagName("strong").text();
        return text;
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
//        NumberFormat format = NumberFormat.getInstance(Locale.UK);
//        Number number = format.parse(strength.text());
        Number number = format.parse(getValueElement(strengthElements));
//        logger.info(number instanceof Double ? number.toString() : strength.text());
        return number.doubleValue();
    }

    public String getBirthday(Elements citizenSecond) {
        return citizenSecond.get(4).text();
    }

    public int getNationalRank(Elements citizenSecond) {
        String text = citizenSecond.get(7).text();
        logger.info("get national rank: " + text);
        return Integer.parseInt(text);
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

    public String[] getValueElements(Elements elements) {
        return getValueElement(elements).split(" / ");
    }

    public String getRankName(Elements elements) {
        return elements.get(8).text();
    }

    public String[] getExperiencePoints(Element content) {
        Attributes citizen_level = content.getElementsByClass("citizen_level").get(0).attributes();
        String[] titles = citizen_level.get("title").split("<br />");
        String[] strings = titles[1].split(" / ");
        return strings;
    }

    public Elements getCitizenName(Element content) {
        Elements name = content.getElementsByClass("citizen_profile_header").get(0).getElementsByClass("");
//                .get(0).attributes();
        return name;
    }

    public int getFirst(String[] experiencePoints) throws ParseException {
        Number number = format.parse(experiencePoints[0]);
        return number.intValue();
    }

    public long getLongFirst(String[] experiencePoints) throws ParseException {
        Number number = format.parse(experiencePoints[0]);
        return number.longValue();
    }

    public int getSecond(String[] experiencePoints) throws ParseException {
        Number number = format.parse(experiencePoints[1]);
        return number.intValue();
    }

    public long getLongSecond(String[] experiencePoints) throws ParseException {
        Number number = format.parse(experiencePoints[1]);
        return number.longValue();
    }


}
