package com.sportradar.recruitmentLibrary;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sportradar.recruitmentLibrary.MatchTest.AWAY_TEAM;
import static com.sportradar.recruitmentLibrary.MatchTest.HOME_TEAM;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

public class ScoreboardTest {

    @ParameterizedTest(name = "{0} scenario")
    @MethodSource("com.sportradar.recruitmentLibrary.MatchTest#constructorTestScenarios")
    public void startNewMatchTest(final String caseDescription,
                                  final String home,
                                  final String away,
                                  final Exception expectedException) {
        Scoreboard scoreboard = new Scoreboard();
        try {
            assertEquals(scoreboard.activeMatches(), 0);
            scoreboard.startNewMatch(home, away);
            assertEquals(scoreboard.activeMatches(), 1);
        } catch (Exception catchedException) {
            assertEquals(expectedException.getClass(), catchedException.getClass());
            assertEquals(expectedException.getMessage(), catchedException.getMessage());
        }
    }

    @ParameterizedTest(name = "{0} scenario")
    @MethodSource("com.sportradar.recruitmentLibrary.MatchTest#updateScoreTestScenarios")
    public void updateMatchScoreTest(final String caseDescription,
                                     final Integer home,
                                     final Integer away,
                                     final Exception expectedException) {
        Scoreboard scoreboard = new Scoreboard();
        String matchId = scoreboard.startNewMatch(HOME_TEAM, AWAY_TEAM);
        try {
            scoreboard.updateMatchScore(matchId, home, away);
        } catch (Exception catchedException) {
            assertEquals(expectedException.getClass(), catchedException.getClass());
            assertEquals(expectedException.getMessage(), catchedException.getMessage());
        }
    }

    private static Pair<Scoreboard, String> generateScoreboardWithSingleMatch() {
        Scoreboard scoreboard = new Scoreboard();
        String matchId = scoreboard.startNewMatch(HOME_TEAM, AWAY_TEAM);
        return new ImmutablePair<>(scoreboard, matchId);
    }


    public static Stream<Arguments> finishMatchTestScenarios() {
        Pair<Scoreboard, String> okScenarioPair = generateScoreboardWithSingleMatch();
        return Stream.of(
                of("OK", okScenarioPair.getLeft(), okScenarioPair.getRight(), 0, false),
                of("NonExisting Id", generateScoreboardWithSingleMatch().getLeft(), HOME_TEAM, 1, true),
                of("Null argument", generateScoreboardWithSingleMatch().getLeft(), null, 1, true)
        );
    }

    @ParameterizedTest(name = "{0} scenario")
    @MethodSource("finishMatchTestScenarios")
    public void finishMatchTest(final String caseDescription,
                                final Scoreboard scoreboard,
                                final String matchId,
                                final Integer expectedActiveMatches,
                                final boolean expectNull) {
        assertEquals(1, scoreboard.activeMatches());
        Match testMatch = scoreboard.finishMatch(matchId);
        assertEquals(expectedActiveMatches, scoreboard.activeMatches());
        assertEquals(expectNull, isNull(testMatch));
    }

    @Test
    public void getSummaryTest() {
        Scoreboard scoreboard = new Scoreboard();
        addTestScore(scoreboard, "Mexico", 0, "Canada", 5);
        addTestScore(scoreboard, "Spain", 10, "Brazil", 2);
        addTestScore(scoreboard, "Germany", 2, "France", 2);
        addTestScore(scoreboard, "Uruguay", 6, "Italy", 6);
        addTestScore(scoreboard, "Argentina", 3, "Australia", 1);
        List<String> expected = List.of(
                "Uruguay_Italy_4",
                "Spain_Brazil_2",
                "Mexico_Canada_1",
                "Argentina_Australia_5",
                "Germany_France_3");


        List<String> summary = scoreboard.getSummary().stream().map(Match::getMatchId).collect(toList());
        assertEquals(5, summary.size());
        assertEquals(expected,summary);

    }

    private void addTestScore(final Scoreboard scoreboard,
                              final String team_1,
                              final int score_1,
                              final String team_2,
                              final int score_2) {
        String key = scoreboard.startNewMatch(team_1, team_2);
        scoreboard.updateMatchScore(key, score_1, score_2);
    }
}
