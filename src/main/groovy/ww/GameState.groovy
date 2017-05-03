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


abstract class GameState {
    List<KillChoice> playersToBeKilled = []
    Integer cycleNumber
    Parameters parameters
    TurnType turnType
    protected List<? extends Player> players
    protected Map<TeamType, ? extends Team> teams

    enum TurnType {
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
        playersToBeKilled.add(new KillChoice(player, killedBy, this))
    }

    void addPlayerKill(Player player, Player killedBy) {
        playersToBeKilled.add(new KillChoice(player, killedBy, this))
    }

    void killSelectedPlayers() {
        playersToBeKilled.each{KillChoice choice ->
            choice.kill()
        }
    }

    void removeKill(Player player) {
        playersToBeKilled.remove(playersToBeKilled.find{it.playerToBeKilled == player})
    }

    abstract getNextState()

    abstract execute()

    void shareKnowledge() {
        List<? extends Player> livePlayers = players
                .findAll { it.alive && it.identityKnownBy.size() != 0 }
        livePlayers.each { player ->
            player.shareKnowledge()
        }
        teams.each { teamType, team ->
            team.shareKnowledge()
        }
    }
}
