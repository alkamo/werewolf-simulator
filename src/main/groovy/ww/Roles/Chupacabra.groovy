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

package ww.Roles

import ww.*
import ww.Actors.NightActive
import ww.Actors.Player
import ww.Actors.ProvidesStats
import ww.Actors.WinCondition
import ww.States.GameState
import ww.States.NightState

class Chupacabra extends Player implements WinCondition, NightActive, ProvidesStats {

    List<? extends Player> attemptedKills = []
    Boolean noWerewolves = false
    Integer playersKilled = 0

    Chupacabra() {
        super()
        this.teamType = TeamType.SOLO
        this.weight = 4
        this.preventsVillageWin = true
    }

    @Override
    void nightAction(NightState nightState) {
        if (nightState.getLivePlayersWithIdentity(Identity.WEREWOLF).size() == 0) {
            noWerewolves = true
            attemptedKills.clear()
        }

        List<? extends Player> potentialKills = nightState.getLivePlayersKnownToTeam(TeamType.VILLAGE).findAll {
            Player player ->
                (player.identity == Identity.WEREWOLF
                        && !nightState.playersToBeKilled.contains(player))
        }
        if (potentialKills.size() == 0) {
            potentialKills = nightState.getLivePlayersUnknownToTeam(TeamType.VILLAGE).findAll {
                Player player ->
                    (!nightState.playersToBeKilled.contains(player)
                            && player != this
                            && !attemptedKills.contains(player))
            }
        }
        if (potentialKills.size() == 0) {
            potentialKills = nightState.getLivePlayers().findAll {
                Player player -> player != this && !attemptedKills.contains(player)
            }
        }
        if (potentialKills.size() != 0) {
            Player killPick = Utilities.pickRandomElement(potentialKills)
            if (killPick.identity == Identity.WEREWOLF
                    || noWerewolves) {
                nightState.addPlayerKill(killPick, this)
                playersKilled += 1
            }
            attemptedKills.add(killPick)
        }

    }

    @Override
    Integer getNightOrder() {
        return 6
    }

    @Override
    Boolean checkForWin(GameState gameState) {
        return (alive && gameState.getLivePlayers().size() == 1)
    }

    @Override
    void updateStats(StatisticCollector stats, GameState gameState) {
        stats.add('Chupacabra - Kills', [Statistic.AggregateType.AVERAGE,
                                         Statistic.AggregateType.MAX,
                                         Statistic.AggregateType.MIN], playersKilled)
    }
}
