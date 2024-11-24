package com.sportradar.recruitmentLibrary;

import java.util.*;

import static java.util.Comparator.reverseOrder;

/**
 * Simple implementation of Scoreboard functionality for active games tracking.
 *
 * @author Robert Radzimowski
 * @version 1.0.0
 */
public class Scoreboard {
    private final Map<String, Match> ongoingMatches = new HashMap<>();

    /**
     * Starts new game and adds it to the scoreboard
     *
     * @param home team
     * @param away team
     * @return matchId handler
     */
    public String startNewMatch(final String home, final String away) {
        Match match = new Match(home, away, this.activeMatches()+1);
        ongoingMatches.put(match.getMatchId(), match);
        return match.getMatchId();
    }

    /**
     * Number of active matches.
     *
     * @return number of active matches on {@link Scoreboard}.
     */
    public int activeMatches() {
        return ongoingMatches.size();
    }

    /**
     * Updates score of outgoing match.
     * See {@link Match#updateScore(Integer homeScore, Integer awayScore)} for more details.
     *
     * @param matchId   specific key for particular match
     * @param homeScore score home team to be updated with
     * @param awayScore score away team to be updated with
     */
    public void updateMatchScore(final String matchId, final Integer homeScore, final Integer awayScore) {
        Match match = ongoingMatches.get(matchId);
        match.updateScore(homeScore, awayScore);
        ongoingMatches.put(matchId, match);
    }

    /**
     * Removes match from scoreboard.
     *
     * @param matchId specific key for particular match
     * @return finished match or null in case of wrong id
     */
    public Match finishMatch(final String matchId) {
        try {
            Match match = ongoingMatches.get(matchId);
            match.finishMatch();
            ongoingMatches.remove(matchId);
            return match;
        } catch (NullPointerException exception) {
            return null;
        }
    }

    /**
     * @return sorted list based on score (higher up) then creationDate (newer up)
     */
    public List<Match> getSummary() {
        Collection<Match> summary = ongoingMatches.values();
        return summary.stream().sorted(reverseOrder()).toList();
    }
}