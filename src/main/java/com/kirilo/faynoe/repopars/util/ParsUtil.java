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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ParsUtil {
    private final Logger logger = Logger.getLogger(ParsUtil.class);
    private final NumberFormat format = NumberFormat.getInstance(Locale.UK);

    public ParsUtil() {
    }

    public Document getDocument(String webPage) {
//        Document document = Jsoup.connect(webPage).userAgent("Jsoup client").timeout(5000).get();
        Document document;
        try {
            document = Jsoup.connect(webPage).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
                    .timeout(5000).get();
        } catch (Exception e) {
//            throw new NotFoundException("Not Found Citizen with address: " + webPage);
            return null;
        }
//        logger.info(document.title());
/*        if(!document.title().endsWith(" - Citizen of The New World")) {
            getDocument(webPage);
        }*/
        return document;
    }

    public Element getContent(Document document) {
        Element content = null;
        try {
            Element citizenprofile = document.getElementById("citizenprofile");
            Element container = citizenprofile.getElementById("container");
            content = container.getElementById("content");
        } catch (NullPointerException e) {
            logger.info(document.title());
            logger.info(document.location());
            return null;
        }

        return content;
    }

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
        return content.getElementsByClass("citizen_level").tagName("strong").text();

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

    public int getStrength(Elements strengthElements) throws ParseException {
        Number number = format.parse(getValueElement(strengthElements));
        float value = number.floatValue() * 100;
        return (int) value;
    }

    //https://www.baeldung.com/java-string-to-date
    public LocalDate getBirthday(Elements citizenSecond) {
        String textData = citizenSecond.get(4).text();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
        LocalDate localDate = LocalDate.parse(textData, formatter);
        return localDate;
    }

    public int getNationalRank(Elements citizenSecond) {
        return Integer.parseInt(citizenSecond.get(7).text());
    }


    public int getDivision(Elements groundElements) {
        String[] split = (groundElements.get(18).text()).split(" ");
        return Integer.parseInt(split[1]);
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

    public String getCitizenName(Element content) {
        return content.getElementsByClass("citizen_avatar").attr("alt");
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


    public String[] getTankRankNumber(Elements militaryRank) {
        return militaryRank.get(7).text().split(" / ");
    }
}
