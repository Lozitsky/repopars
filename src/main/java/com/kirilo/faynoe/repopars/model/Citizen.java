package com.kirilo.faynoe.repopars.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;

@Entity
@Table(name = "citizen", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_game"}, name = "citizens_unique_id_game_idx")})
public class Citizen extends AbstractEntity {
    @Column(name = "id_game")
    private int idGame;
    @Column(name = "name")
    private String name;
    @Column(name = "birthday")
    private LocalDate birthday;
    @Column(name = "national_rank")
    private int nationalRank;
    @Column(name = "level")
    private int level;
    @Column(name = "experience_points")
    private int experiencePoints;
    @Column(name = "ground_division")
    private int groundDivision;
    @Column(name = "strength")
    private int strength;
    @Column(name = "military_rank_points")
    private long militaryRankPoints;
    @Column(name = "military_rank")
    private String militaryRank;
    @Column(name = "aircraft_rank_points")
    private long aircraftRankPoints;
    @Column(name = "aircraft_rank")
    private String aircraftRank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getNationalRank() {
        return nationalRank;
    }

    public void setNationalRank(int nationalRank) {
        this.nationalRank = nationalRank;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public int getGroundDivision() {
        return groundDivision;
    }

    public void setGroundDivision(int groundDivision) {
        this.groundDivision = groundDivision;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public long getMilitaryRankPoints() {
        return militaryRankPoints;
    }

    public void setMilitaryRankPoints(long militaryRankPoints) {
        this.militaryRankPoints = militaryRankPoints;
    }

    public String getMilitaryRank() {
        return militaryRank;
    }

    public void setMilitaryRank(String militaryRank) {
        this.militaryRank = militaryRank;
    }

    public long getAircraftRankPoints() {
        return aircraftRankPoints;
    }

    public void setAircraftRankPoints(long aircraftRankPoints) {
        this.aircraftRankPoints = aircraftRankPoints;
    }

    public String getAircraftRank() {
        return aircraftRank;
    }

    public void setAircraftRank(String aircraftRank) {
        this.aircraftRank = aircraftRank;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "idGame=" + idGame +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", nationalRank=" + nationalRank +
                ", level=" + level +
                ", experiencePoints=" + experiencePoints +
                ", groundDivision=" + groundDivision +
                ", strength=" + strength +
                ", militaryRankPoints=" + militaryRankPoints +
                ", militaryRank='" + militaryRank + '\'' +
                ", aircraftRankPoints=" + aircraftRankPoints +
                ", aircraftRank='" + aircraftRank + '\'' +
                ", id=" + id +
                "} " + super.toString();
    }
}