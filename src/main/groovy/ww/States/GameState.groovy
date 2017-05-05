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

package ww.States

import ww.KillChoice
import ww.Parameters
import ww.Actors.Player
import ww.Actors.Team
import ww.ProvidesStats
import ww.Statistic
import ww.TeamType
import ww.Utilities
import ww.WinCondition

import java.math.RoundingMode


abstract class GameState {
    LinkedList<KillChoice> playersToBeKilled = new LinkedList()
    Integer cycleNumber
    Parameters parameters
    TurnType turnType
    List<? extends Player> players
    Map<TeamType, ? extends Team> teams

    enum TurnType {
        SETUP,
        DAY,
        NIGHT
    }

    GameState(Integer cycleNumber, Parameters parameters, List<? extends Player> players, Map<TeamType, ? extends Team> teams) {
        this.cycleNumber = cycleNumber
        this.parameters = parameters
        this.players = players
        this.teams = teams
    }

    void addTeamKill(Player player, Team killedBy) {
        if (playersToBeKilled.findAll { it.playerToBeKilled = player }.size() == 0) {
            playersToBeKilled.add(new KillChoice(player, killedBy, this))
        }
    }

    void addPlayerKill(Player player, Player killedBy) {
        if (playersToBeKilled.findAll { it.playerToBeKilled = player }.size() == 0) {
            playersToBeKilled.add(new KillChoice(player, killedBy, this))
        }
    }

    void killSelectedPlayers() {
        while (!playersToBeKilled.isEmpty()) {
            playersToBeKilled.remove().kill(this)
        }
    }

    void removeKill(Player player) {
        playersToBeKilled.remove(playersToBeKilled.find { it.playerToBeKilled == player })
    }

    abstract GameState getNextState()

    abstract void execute()

    void shareKnowledge() {
        List<? extends Player> livePlayers = players
                .findAll { it.alive && it.identityKnownBy.size() != 0 }
        livePlayers.each { player ->
            player.shareKnowledge(this)
        }
        teams.each { teamType, team ->
            team.shareKnowledge(this)
        }
    }

    List<? extends Player> getOtherLivePlayers(Player player) {
        return players.findAll { it.alive && it != player }
    }

    List<? extends Player> getLivePlayersOnTeam(Team team) {
        return players.findAll { it.teamType == team.teamType && it.alive }
    }

    List<? extends Player> getLivePlayersNotOnTeam(Team team) {
        return players.findAll { it.teamType != team.teamType && it.alive }
    }

    List<? extends Player> getLivePlayersKnownToTeam(Team team) {
        return players.findAll { it.alive && it.identityKnownByTeam.contains(team) }
    }

    List<? extends Player> getLivePlayersUnknownToTeam(Team team) {
        return players.findAll { it.alive && !it.identityKnownByTeam.contains(team) }
    }

    Boolean hasSomeoneWon() {
        Boolean teamWon = teams.values().findAll { (it instanceof WinCondition && it.checkForWin(this)) }.size() > 0
        Boolean playerWon = players.findAll { it instanceof WinCondition && it.checkForWin(this) }.size() > 0
        return teamWon || playerWon
    }

    List<String> whoWon() {
        List<String> winners = []
        teams.values().findAll { (it instanceof WinCondition && it.checkForWin(this)) }.each {
            Team team ->
                winners.add(team.name)
        }
        players.findAll { it instanceof WinCondition && it.checkForWin(this) }.each {
            Player player ->
                winners.add(player.name)
        }
        return winners
    }

    void updateStats(Map<String, Statistic> stats) {
        this.players.findAll{
            it instanceof ProvidesStats
        }.each{ProvidesStats it ->
            it.updateStats(stats, this)
        }
        this.teams.values().findAll{
            it instanceof ProvidesStats
        }.each{ProvidesStats it ->
            it.updateStats(stats, this)
        }
        if (whoWon().size() > 1) {
            Utilities.addStatIfMissing(stats, "Multiple Winners", [Statistic.AggregateType.PERCENTAGE], 1)
        }
        if (this.turnType == GameState.TurnType.NIGHT) {
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
                this.cycleNumber)
    }
}
