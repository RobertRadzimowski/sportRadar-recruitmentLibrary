package com.sportradar.recruitmentLibrary;

import java.time.LocalDate;

import static java.time.LocalDate.now;
import static org.apache.commons.lang3.ObjectUtils.requireNonEmpty;


/**
 * Simple representation of Football match.
 *
 * @author Robert Radzimowski
 * @version 1.0
 */
public class Match {
    private final String homeTeam;
    private final String awayTeam;

    private int homeScore;
    private int awayScore;
    private boolean isActive;
    private LocalDate startDate;

    public Match(final String home, final String away) {

        this.homeTeam = requireNonEmpty(home, "Home team name must not be null nor empty.");
        this.awayTeam = requireNonEmpty(away, "Away team name must not be null nor empty.");

        isActive = true;
        homeScore = 0;
        awayScore = 0;
        startDate = now();
    }

    /**
     * Changes match scores to given values. Can only be done on active match.
     *
     * @param homeScore overwrites HOME team score by given value.
     * @param awayScore overwrites AWAY team score by given value.
     * @throws IllegalArgumentException when trying to update with negative score.
     * @throws IllegalStateException    when trying to update closed match.
     */
    public void updateScore(final Integer homeScore, final Integer awayScore) throws IllegalArgumentException {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Score can't be negative.");
        }
        if (!isActive)
            throw new IllegalStateException("Can't update score for closed match.");

        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    /**
     * Finishes match, which prevents it from being updated.
     */
    public void finishMatch() {
        this.isActive = false;
    }

    /**
     * Finishes match.
     */
    public int getCombinedScore() {
        return homeScore + awayScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getAwayScore() {
        return awayScore;
    }
}
