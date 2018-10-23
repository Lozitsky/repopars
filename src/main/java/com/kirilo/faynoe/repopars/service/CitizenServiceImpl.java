package com.kirilo.faynoe.repopars.service;

import com.kirilo.faynoe.repopars.controller.ErepRestController;
import com.kirilo.faynoe.repopars.model.Citizen;
import com.kirilo.faynoe.repopars.repository.CitizenRepository;
import com.kirilo.faynoe.repopars.util.ParsUtil;
import com.kirilo.faynoe.repopars.util.exception.NotFoundException;
import jdk.nashorn.internal.runtime.options.Option;
import org.jboss.logging.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class CitizenServiceImpl implements CitizenService {
    private final Logger logger = Logger.getLogger(ErepRestController.class);

    private CitizenRepository repository;
    private final ParsUtil util;

    @Autowired
    public CitizenServiceImpl(CitizenRepository repository) {
        this.repository = repository;
        util = new ParsUtil();
    }

    @Override
    public Citizen create(Citizen citizen) {
        return repository.save(citizen);
    }

    @Override
    public void update(Citizen citizen) {
        repository.save(citizen);
    }

    @Override
    public Citizen getCitizen(int gameId) {
        return repository.findByGameId(gameId);
    }

//    https://stackoverflow.com/questions/25747499/java-8-calculate-difference-between-two-localdatetime
    @Override
    public void search(int fromCitizenId, int toCitizenId) {
        LocalDateTime start = LocalDateTime.now();
        logger.info("Start with " + fromCitizenId + ": " + start);
        for (int id = fromCitizenId; id <= toCitizenId; id++) {
            String webPage = "https://www.erepublik.com/en/citizen/profile/" + id;

            Document document = util.getDocument(webPage);
            if (document == null) {
                continue;
            }

            Citizen citizen;
            if ((citizen = getCitizen(id)) == null) {
                try {
                    create(parse(document, id));
                } catch (Exception e) {
//                    throw new NotFoundException("Error parsing document for: " + webPage);
                    logger.info("Error parsing document for: " + webPage);
                    continue;
                }
            } else {
                update(citizen);
            }
        }
        LocalDateTime end = LocalDateTime.now();
        logger.info("End with " + toCitizenId + ": " + end);

        LocalDateTime tempDateTime = LocalDateTime.from(start);
        long hours = tempDateTime.until(end, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours(hours);

        long minutes = tempDateTime.until(end, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes(minutes);

        long seconds = tempDateTime.until(end, ChronoUnit.SECONDS);

        logger.info("Total: " +
                hours + " hours " +
                minutes + " minutes " +
                seconds + " seconds.");
    }

    @Override
    public Citizen parse(Document document, int inGameId) throws ParseException, NullPointerException {
        Element content = util.getContent(document);

        Elements militaryBox = util.getMilitaryBox(content);
        Elements militaryBoxWide = util.getMilitaryBoxWide(content);

        Elements groundElements = util.getGroundElements(militaryBox);
        Elements strengthElements = util.getStrengthElements(militaryBox);

        Elements militaryRank = util.getMilitaryRank(militaryBoxWide);
        Elements aircraftRank = util.getAircraftRank(militaryBoxWide);

        Elements citizenSecond = util.getCitizenSecond(content);
        String[] experiencePoints = util.getExperiencePoints(content);
        String[] valueMilitary = util.getTankRankNumber(militaryRank);
        String[] valueAirCraft = util.getValueElements(aircraftRank);

        Citizen citizen = new Citizen();

        citizen.setIdGame(inGameId);
        citizen.setBirthday(util.getBirthday(citizenSecond));
        citizen.setNationalRank(util.getNationalRank(citizenSecond));
        citizen.setLevel(util.getCitizenLevel(content));
        citizen.setExperiencePoints(util.getFirst(experiencePoints));
        citizen.setName(util.getCitizenName(content));
        citizen.setStrength(util.getStrength(strengthElements));
        citizen.setGroundDivision(util.getDivision(groundElements));
        citizen.setMilitaryRankPoints(util.getLongFirst(valueMilitary));
        citizen.setMilitaryRank(util.getRankName(militaryRank));
        citizen.setAircraftRankPoints(util.getLongFirst(valueAirCraft));
        citizen.setAircraftRank(util.getRankName(aircraftRank));

        return citizen;
    }
}
