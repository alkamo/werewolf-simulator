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

abstract class Player {
    Boolean alive = true
    Identity identityOverride
    List<? extends Player> identityKnownBy = []
    List<? extends Team> identityKnownByTeam = []
    Parameters parameters
    List<? extends Player> players
    List<? extends Player> deathLinks = []
    Team team

    Identity identity = Identity.VILLAGER;
    Integer weight = 1;
    Boolean preventsVillageWin = false;
    TeamType teamType = TeamType.VILLAGE;

    Player(Parameters parameters, List<Player> players, TeamType teamType, Identity identity, Integer weight, Boolean preventsVillageWin) {
        this.teamType = teamType
        this.parameters = parameters;
        this.players = players;
        this.identity = identity;
        this.weight = weight;
        this.preventsVillageWin = preventsVillageWin;
    }

    Player(Parameters parameters, List<Player> players, Integer weight) {
        this.teamType = teamType
        this.parameters = parameters;
        this.players = players;
        this.identity = identity;
        this.weight = weight;
        this.preventsVillageWin = preventsVillageWin;
    }

    void kill(Game.TurnType turnType) {
        this.alive = false
        if (this instanceof DeathActive) {
            DeathActive deathPlayer = (DeathActive) this
            deathPlayer.onDeath(turnType)
        }
        if (!alive) {
            deathLinks.each { Player player ->
                player.kill(turnType)
            }
        }
    }

    Identity getIdentity() {
        return identityOverride ?: identity
    }

    void shareKnowledge() {
        identityKnownBy.each { Player player ->
            if (player.team.teamType != TeamType.SOLO && !identityKnownByTeam.contains(player.team)) {
                identityKnownByTeam.add(player.team)
            }
        }
    }
}
