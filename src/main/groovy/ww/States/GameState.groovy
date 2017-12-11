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

import ww.*
import ww.Actors.Player
import ww.Actors.Team
import ww.Actors.WinCondition

abstract class GameState {
    LinkedList<KillChoice> playersToBeKilled = new LinkedList()
    Integer cycleNumber
    Parameters parameters
    List<? extends Player> players
    Map<TeamType, ? extends Team> teams
    List<DeathLink> deathLinks = []

    GameState(Integer cycleNumber, Parameters parameters, List<? extends Player> players, Map<TeamType, ? extends Team> teams) {
        this.cycleNumber = cycleNumber
        this.parameters = parameters
        this.players = players
        this.teams = teams
    }

    GameState(GameState gameState) {
        this.cycleNumber = gameState.cycleNumber
        this.parameters = gameState.parameters
        this.players = gameState.players
        this.teams = gameState.teams
        this.deathLinks = gameState.deathLinks
    }

    void addTeamKill(Player player, Team killedBy) {
        if (playersToBeKilled.findAll { it.playerToBeKilled = player }.size() == 0) {
            parameters.logAction("$player chosen to be killed by $killedBy")
            playersToBeKilled.add(new KillChoice(player, killedBy, this))
        }
    }

    void addPlayerKill(Player player, Player killedBy) {
        if (playersToBeKilled.findAll { it.playerToBeKilled = player }.size() == 0) {
            parameters.logAction("$player chosen to be killed by $killedBy")
            playersToBeKilled.add(new KillChoice(player, killedBy, this))
        }
    }

    void killSelectedPlayers() {
        while (!playersToBeKilled.isEmpty()) {
            playersToBeKilled.remove().kill(this)
        }
        deathLinks.each { it.evaluateLink(this) }
    }

    Boolean removeKill(Player player) {
        return playersToBeKilled.remove(playersToBeKilled.find { it.playerToBeKilled == player })
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

    List<? extends Player> getLivePlayers() {
        return players.findAll { it.alive }
    }

    List<? extends Player> getOtherLivePlayers(Player player) {
        return players.findAll { it.alive && it != player }
    }

    List<? extends Player> getLivePlayersAndYou(Player player) {
        return players.findAll { it.alive || it == player }
    }

    List<? extends Player> getLivePlayersOnTeam(TeamType teamType) {
        return players.findAll { it.teamType == teamType && it.alive }
    }

    List<? extends Player> getLivePlayersNotOnTeam(TeamType teamType) {
        return players.findAll { it.teamType != teamType && it.alive }
    }

    List<? extends Player> getLivePlayersKnownToTeam(TeamType teamType) {
        return getLivePlayersKnownToTeam(teams[teamType])
    }

    List<? extends Player> getLivePlayersUnknownToTeam(TeamType teamType) {
        return getLivePlayersUnknownToTeam(teams[teamType])
    }

    List<? extends Player> getLivePlayersOnTeam(Team team) {
        return getLivePlayersOnTeam(team.teamType)
    }

    List<? extends Player> getLivePlayersNotOnTeam(Team team) {
        return getLivePlayersNotOnTeam(team.teamType)
    }

    List<? extends Player> getLivePlayersKnownToTeam(Team team) {
        return players.findAll { it.alive && it.identityKnownByTeam.contains(team) }
    }

    List<? extends Player> getLivePlayersUnknownToTeam(Team team) {
        return players.findAll { it.alive && !it.identityKnownByTeam.contains(team) }
    }

    List<? extends Player> getLivePlayersWithIdentity(Identity identity) {
        return players.findAll { it.identity == identity && it.alive }
    }

    Player getRightNeighbor(Player currentPlayer) {
        return (Player) Utilities.getListOffsetWrapping(getLivePlayersAndYou(currentPlayer), currentPlayer, 1)
    }

    Player getLeftNeighbor(Player currentPlayer) {
        return (Player) Utilities.getListOffsetWrapping(getLivePlayersAndYou(currentPlayer), currentPlayer, -1)
    }

    Player getOffsetLivePlayer(Player currentPlayer, Integer offset) {
        Player nextPlayer
        List<? extends Player> livePlayers = getLivePlayers()
        Integer currentIndex = livePlayers.findIndexOf { it == currentPlayer }
        if (currentIndex + offset >= livePlayers.size()) {
            nextPlayer = livePlayers[offset + currentIndex - livePlayers.size()]
        } else if (currentPlayer + offset < 0) {
            nextPlayer = livePlayers[livePlayers.size() - currentIndex - offset - 1]
        } else {
            nextPlayer = livePlayers[currentIndex + offset]
        }
        return nextPlayer
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

    void addTwoWayDeathLink(Player player1, Player player2, Player linkedBy) {
        addOneWayDeathLink(player1, player2, linkedBy)
        addOneWayDeathLink(player2, player1, linkedBy)
    }

    void addOneWayDeathLink(Player drivingPlayer, Player affectedPlayer, Player linkedBy) {
        deathLinks.add(new DeathLink(drivingPlayer, affectedPlayer, linkedBy))
    }
}
