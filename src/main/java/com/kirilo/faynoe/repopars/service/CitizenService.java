package com.kirilo.faynoe.repopars.service;

import com.kirilo.faynoe.repopars.model.Citizen;
import org.jsoup.nodes.Document;

import java.text.ParseException;

public interface CitizenService {
    Citizen create(Citizen citizen);
    void update(Citizen citizen);
    Citizen getCitizen(int citizenId);

    void search(int fromCitizenId, int toCitizenId);
    Citizen parse(Document document, int inGameId) throws ParseException;
}
