/*
 * Copyright 2017 Allan Morstein
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ww

import java.math.RoundingMode

class Game {
    private Parameters parameters
    private RoleSet roleSet
    private List<? extends Player> players = []
    private Map<TeamType, ? extends Team> teams = [:]
    GameState currentState

    Game(Parameters parameters, RoleSet roleSet) {
        this.parameters = parameters
        this.roleSet = roleSet
    }

    void setupActions() {
        List<? extends SetupActive> setupActors = []
        setupActors.addAll((List<? extends SetupActive>) players
                .findAll { it.alive && it instanceof SetupActive })
        setupActors.addAll((List<? extends SetupActive>) teams
                .values()
                .findAll { it instanceof SetupActive })
        setupActors.each { player ->
            player.onGameSetup()
        }
    }

    void play() {
        this.roleSet.setupPlayersAndTeams(parameters, players, teams)
        setupActions()
        currentState = new NightState(0, parameters, players, teams)
        currentState.execute()
        while (!hasSomeoneWon()) {
            currentState = currentState.getNextState()
            currentState.execute()
        }
    }

    Boolean hasSomeoneWon() {
        Boolean teamWon = teams.values().findAll { (it instanceof WinCondition && it.checkForWin()) }.size() > 0
        Boolean playerWon = players.findAll { it instanceof WinCondition && it.checkForWin() }.size() > 0
        return teamWon || playerWon
    }

    List<String> whoWon() {
        List<String> winners = []
        teams.values().findAll { (it instanceof WinCondition && it.checkForWin()) }.each {
            Team team ->
                winners.add(team.name)
        }
        players.findAll { it instanceof WinCondition && it.checkForWin() }.each {
            Player player ->
                winners.add(player.name)
        }
        return winners
    }

    void updateStats(Map<String, Statistic> stats) {
        players.findAll{
            it instanceof ProvidesStats
        }.each{ProvidesStats it ->
            it.updateStats(stats)
        }
        teams.values().findAll{
            it instanceof ProvidesStats
        }.each{ProvidesStats it ->
            it.updateStats(stats)
        }
        if (whoWon().size() > 1) {
            Utilities.addStatIfMissing(stats, "Multiple Winners", [Statistic.AggregateType.PERCENTAGE], 1)
        }
        if (currentState.turnType == GameState.TurnType.NIGHT) {
            Utilities.addStatIfMissing(stats, "Night End", [Statistic.AggregateType.PERCENTAGE], 1)
        } else {
            Utilities.addStatIfMissing(stats, "Day End", [Statistic.AggregateType.PERCENTAGE], 1)
        }
        Utilities.addStatIfMissing(stats,
                "Ended on Round",
                [Statistic.AggregateType.AVERAGE,
                 Statistic.AggregateType.MIN,
                 Statistic.AggregateType.MAX],
                0,
                RoundingMode.FLOOR,
                currentState.cycleNumber)
    }


}
