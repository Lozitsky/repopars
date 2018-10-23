package com.kirilo.faynoe.repopars.controller;

import com.kirilo.faynoe.repopars.util.ParsUtil;
import com.kirilo.faynoe.repopars.util.exception.NotFoundException;
import org.jboss.logging.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping(ErepRestController.REST_EREP)
public class ErepRestController {
    static final String REST_EREP = "/erep";

    private final Logger logger = Logger.getLogger(ErepRestController.class);
    private final ParsUtil util;

    public ErepRestController() {
        util = new ParsUtil();
    }

    @GetMapping(value = "/{id}")
    public String getErep(@PathVariable int id) throws ParseException {
        String webPage = "https://www.erepublik.com/en/citizen/profile/" + id;
        Document document = util.getDocument(webPage);
        Optional.ofNullable(document).orElseThrow(() -> new NotFoundException("Not Found Citizen with address: " + webPage));

        Element content = util.getContent(document);

        //-------------------------------------------------------------------------------
        Elements militaryBox = util.getMilitaryBox(content);
        Elements militaryBoxWide = util.getMilitaryBoxWide(content);

        Elements militaryRank = util.getMilitaryRank(militaryBoxWide);
        Elements aircraftRank = util.getAircraftRank(militaryBoxWide);

        //Pars strength
        Elements groundElements = util.getGroundElements(militaryBox);
        Elements strengthElements = util.getStrengthElements(militaryBox);
        //-------------------------------------------------------------------------------
//birthday and rank
        Elements citizenSecond = util.getCitizenSecond(content);
        logger.info(util.getNameElement(citizenSecond) + " " + util.getBirthday(citizenSecond) + " "
                + util.getNationalRank(citizenSecond));

        //  citizen level
        int level = util.getCitizenLevel(content);
        logger.info("Level: " + level);
        logger.info(util.getOriginalTitle(content));

        String[] experiencePoints = util.getExperiencePoints(content);
        logger.info("Experience points: " + util.getFirst(experiencePoints));
        logger.info("Next Level at: " + util.getSecond(experiencePoints));
//----------------------------------------------------------------------------------
        String name = util.getCitizenName(content);

        logger.info("Name: " + name);

//----------------------------------print----------------------------------------------------------
        int strength = util.getStrength(strengthElements);

//Ground
        logger.info(util.getNameElement(groundElements) + " " + util.getDivision(groundElements));
        logger.info(util.getNameElement(strengthElements) + " " + strength);

//        String[] valueMilitary = util.getValueElements(militaryRank);

/*        Elements allElements = militaryRank.get(7).getAllElements();
//        logger.info(allElements);
        logger.info(allElements.get(0).text());*/

//        logger.info(militaryRank.get(7).text());


/*        int i = 0;
        for (Element element: allElements) {
            logger.info(i + " militaryRank: " + element);
            ++i;
        }*/

        String[] valueMilitary = util.getTankRankNumber(militaryRank);
        logger.info(util.getNameElement(militaryRank)
                + "\n Rank points: " + util.getLongFirst(valueMilitary)
                + "\n Next Rank at: " + util.getLongSecond(valueMilitary)
                + "\n Rank: " + util.getRankName(militaryRank));

        String[] valueAirCraft = util.getValueElements(aircraftRank);
        logger.info(util.getNameElement(aircraftRank)
                + "\n Rank points: " + util.getLongFirst(valueAirCraft)
                + "\n Next Rank at: " + util.getLongSecond(valueAirCraft)
                + "\n Rank: " + util.getRankName(aircraftRank));

        return "" + strength;
    }
}
