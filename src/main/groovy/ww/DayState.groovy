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


class DayState extends GameState {
    Integer lynches = 1

    DayState(Integer dayNumber) {
        super(dayNumber)
    }

    Integer getDayNumber() {
        return cycleNumber
    }

    void lynch(Map<TeamType, ? extends Team> teams, List<? extends Player> players) {
        if (this.dayNumber != 1 || parameters.firstDayLynch) {
            if (teams[TeamType.VILLAGE].getLivePlayersOnTeam().size()
                    > teams[TeamType.WEREWOLF].getLivePlayersOnTeam().size()) {
                lynchWerewolf(teams, players)
            } else if (teams[TeamType.WEREWOLF].getLivePlayersOnTeam().size()
                    > teams[TeamType.VILLAGE].getLivePlayersOnTeam().size()) {
                lynchVillager(teams, players)
            }
        }
    }

    void lynchWerewolf(Map<TeamType, ? extends Team> teams, List<? extends Player> players) {
        List<? extends Player> potentialKills = teams[TeamType.VILLAGE].getLivePlayersKnownByTeam().findAll {
            Player player ->
                player.identity = Identity.WEREWOLF
        }
        if (potentialKills.size() == 0) {
            potentialKills = teams[TeamType.VILLAGE].getLivePlayersUnknownByTeam()
        }
        this.playersToBeKilled.add(
                new KillChoice(
                        playerToBeKilled: (Player) Utilities.pickRandomElement(potentialKills),
                        killedByTeam: teams[TeamType.VILLAGE]))
    }

    void lynchVillager(Map<TeamType, ? extends Team> teams, List<? extends Player> players) {
        List<? extends Player> potentialKills = teams[TeamType.WEREWOLF].getLivePlayersKnownByTeam()
        if (potentialKills.size() == 0) {
            potentialKills = players.findAll {
                Player player ->
                    (player.alive
                            && player.team != teams[TeamType.WEREWOLF])
            }
        }
        this.playersToBeKilled.add(
                new KillChoice(
                        playerToBeKilled: (Player) Utilities.pickRandomElement(potentialKills),
                        killedByTeam: teams[TeamType.VILLAGE]))
    }
}
