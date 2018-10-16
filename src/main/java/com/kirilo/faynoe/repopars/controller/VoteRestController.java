package com.kirilo.faynoe.repopars.controller;

import com.kirilo.faynoe.repopars.util.ParsUtil;
import org.jboss.logging.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping(VoteRestController.REST_EREP)
public class VoteRestController {
    static final String REST_EREP = "/erep";

    private final Logger logger = Logger.getLogger(VoteRestController.class);
    private final ParsUtil util;

    public VoteRestController() {
        util = new ParsUtil();
    }

    @GetMapping(value = "/{id}")
    public String getErep(@PathVariable int id) throws IOException, ParseException {
        Element content = util.getContent(id);

        Elements militaryBox = util.getMilitaryBox(content);
        Elements militaryBoxWide = util.getMilitaryBoxWide(content);

        Elements militaryRank = util.getMilitaryRank(militaryBoxWide);
        Elements aircraftRank = util.getAircraftRank(militaryBoxWide);

        //Pars strength
        Elements groundElements = util.getGroundElements(militaryBox);
        Elements strengthElements = util.getStrengthElements(militaryBox);

/*        int i = 0;
        for (Element elem : allElements1) {
            System.out.print(i + "------->   ");
            System.out.println(elem.text());
            ++i;
        }*/

        Double strength = util.getStrength(strengthElements);

//Ground
        logger.info(util.getNameElement(groundElements) + " " + util.getDivision(groundElements));
        logger.info(util.getNameElement(strengthElements) + " " + strength);
        logger.info(util.getNameElement(militaryRank) + " " + util.getValueElement(militaryRank)
                + " " + util.getRankName(militaryRank));
        logger.info(util.getNameElement(aircraftRank) + " " + util.getValueElement(aircraftRank)
                + " " + util.getRankName(aircraftRank));

        return "" + strength;
    }
}
