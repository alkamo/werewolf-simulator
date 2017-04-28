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

abstract class Team {
    TeamType teamType
    List<? extends Player> players
    Parameters parameters

    Team(TeamType teamType, Parameters parameters, List<? extends Player> players) {
        this.teamType = teamType;
        this.parameters = parameters;
        this.players = players;
    }

    abstract void nightAction(NightState nightState)

    abstract void onGameSetup()

    abstract Boolean checkForWin()

    public void shareKnowledge() {
        List<? extends Player> knownPlayers = players.findAll{it.alive && it.identityKnownByTeam.contains(this)}
        List<? extends Player> teamPlayers = players.findAll{it.alive && it.team == this}

        teamPlayers.each{teamPlayer ->
            knownPlayers.each{knownPlayer ->
                if (!knownPlayer.identityKnownBy.contains(teamPlayer)) {
                    knownPlayer.identityKnownBy.add(teamPlayer)
                }
            }
        }
    }
}
