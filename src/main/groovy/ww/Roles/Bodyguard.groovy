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

import ww.Actors.NightActive
import ww.Actors.Player
import ww.Identity
import ww.Actors.ProvidesStats
import ww.States.GameState
import ww.States.NightState
import ww.Statistic
import ww.StatisticCollector
import ww.Utilities

class Bodyguard extends Player implements NightActive, ProvidesStats {
    List<? extends Player> savedPlayers = []

    Bodyguard() {
        super()
        this.weight = 3
    }

    @Override
    void nightAction(NightState nightState) {
        List<? extends Player> protectionCandidates = nightState.getLivePlayersKnownToTeam(this.team).findAll{
            it != this && it.identity == Identity.VILLAGER
        }
        Player protectedPlayer = Utilities.pickRandomElement(protectionCandidates)
        if (nightState.removeKill(protectedPlayer)) {
            savedPlayers.add(protectedPlayer)
        }
    }

    @Override
    Integer getNightOrder() {
        return 15
    }

    @Override
    void updateStats(StatisticCollector stats, GameState gameState) {
        if (savedPlayers.size() > 0) {
            stats.add("Bodyguard - Players Saved", Statistic.AggregateType.AVERAGE, savedPlayers.size())
            savedPlayers.each { Player player ->
                stats.add("Bodyguard - Saved ${player.name}", Statistic.AggregateType.PERCENTAGE, 1)
            }
        }
    }
}
