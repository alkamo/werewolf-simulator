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

import ww.Actors.DayActive
import ww.Identity
import ww.Parameters
import ww.Actors.Player
import ww.Actors.Team
import ww.TeamType
import ww.Utilities


class DayState extends GameState {
    Integer lynches = 1

    DayState(Integer cycleNumber, Parameters parameters, List<? extends Player> players, Map<TeamType, ? extends Team> teams) {
        super(cycleNumber, parameters, players, teams)
        this.turnType = TurnType.DAY
    }

    @Override
    NightState getNextState() {
        return new NightState(cycleNumber, parameters, players, teams)
    }

    @Override
    void execute() {
        lynch()
        List<? extends DayActive> dayActors = []
        dayActors.addAll((List<? extends DayActive>) players
                .findAll { it.alive && it instanceof DayActive })
        dayActors.addAll((List<? extends DayActive>) teams
                .values()
                .findAll { it instanceof DayActive })
        dayActors.each { player ->
            player.dayAction(this)
        }
        killSelectedPlayers()
        shareKnowledge()
    }

    Integer getDayNumber() {
        return cycleNumber
    }

    void lynch() {
        lynches.times {
            if (this.dayNumber != 1 || parameters.firstDayLynch) {
                if (getLivePlayersOnTeam(teams[TeamType.VILLAGE]).size()
                        > getLivePlayersOnTeam(teams[TeamType.WEREWOLF]).size()) {
                    lynchWerewolf()
                } else if (getLivePlayersOnTeam(teams[TeamType.WEREWOLF]).size()
                        > getLivePlayersOnTeam(teams[TeamType.VILLAGE]).size()) {
                    lynchVillager()
                }
            }
        }
    }

    void lynchWerewolf() {
        List<? extends Player> potentialKills = getLivePlayersKnownToTeam(teams[TeamType.VILLAGE]).findAll {
            Player player ->
                (player.identity == Identity.WEREWOLF
                        && !playersToBeKilled.contains(player))
        }
        if (potentialKills.size() == 0) {
            potentialKills = getLivePlayersUnknownToTeam(teams[TeamType.VILLAGE]).findAll {
                Player player ->
                    !playersToBeKilled.contains(player)
            }
        }
        addTeamKill((Player) Utilities.pickRandomElement(potentialKills),
                teams[TeamType.VILLAGE])
    }

    void lynchVillager() {
        List<? extends Player> potentialKills = getLivePlayersKnownToTeam(teams[TeamType.WEREWOLF])
        if (potentialKills.size() == 0) {
            potentialKills = players.findAll {
                Player player ->
                    (player.alive
                            && player.team != teams[TeamType.WEREWOLF]
                            && !playersToBeKilled.contains(player))
            }
        }
        addTeamKill((Player) Utilities.pickRandomElement(potentialKills),
                teams[TeamType.VILLAGE])
    }
}
