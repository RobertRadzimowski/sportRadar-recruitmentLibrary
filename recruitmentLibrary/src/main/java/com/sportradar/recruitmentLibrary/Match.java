package com.sportradar.recruitmentLibrary;

import lombok.Getter;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.nanoTime;
import static org.apache.commons.lang3.ObjectUtils.requireNonEmpty;


/**
 * Simple representation of Football match.
 *
 * @author Robert Radzimowski
 * @version 1.0
 */
@Getter
public class Match implements Comparable<Match> {
    private final String matchId;
    private final String homeTeam;
    private final String awayTeam;
    private final Long startTime;

    private int homeScore;
    private int awayScore;
    private boolean isActive;

    public Match(final String home, final String away) {

        this.homeTeam = requireNonEmpty(home, "Home team name must not be null nor empty.");
        this.awayTeam = requireNonEmpty(away, "Away team name must not be null nor empty.");

        isActive = true;
        homeScore = 0;
        awayScore = 0;
        startTime = nanoTime();
        matchId = homeTeam + "_" + awayTeam + "_" + startTime; // Todo: Better Id generation method
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
     * Order is based on total (home + away) score. In case of draw, newer takes priority.
     *
     *
     * @param other Match is compared against
     * @return better value match
     */
    @Override
    public int compareTo(Match other) {
        int scoreComparison = Integer.compare((this.getHomeScore() + this.getAwayScore()),
                (other.getHomeScore() + other.getAwayScore()));
        return switch (scoreComparison) {
            case -1, 1 -> scoreComparison;
            default -> Long.compare(this.startTime, other.startTime);
        };
    }
}
