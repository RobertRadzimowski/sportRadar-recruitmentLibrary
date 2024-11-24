package com.sportradar.recruitmentLibrary;

import java.util.*;

import static java.util.Comparator.reverseOrder;

/**
 * You are working in a sports data company, and we would like you to develop a new Live Football
 * World Cup Scoreboard library that shows all the ongoing matches and their scores.
 * The scoreboard supports the following operations:
 * 1. Start a new match, assuming initial score 0 – 0 and adding it the scoreboard.
 * This should capture following parameters:
 * a. Home team
 * b. Away team
 * 2. Update score. This should receive a pair of absolute scores: home team score and away
 * team score.
 * 3. Finish match currently in progress. This removes a match from the scoreboard.
 * 4. Get a summary of matches in progress ordered by their total score. The matches with the
 * same total score will be returned ordered by the most recently started match in the
 * scoreboard.
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
        Match match = new Match(home, away);
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
     * @return sorted list based on score (higher up) then creationDate (earlier up)
     */
    public List<Match> getSummary() {
        Collection<Match> summary = ongoingMatches.values();
        return summary.stream().sorted(reverseOrder()).toList();
    }
}