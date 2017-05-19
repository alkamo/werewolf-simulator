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
import ww.States.GameState
import ww.States.NightState

class Huntress extends Player implements NightActive, ProvidesStats {
    Player killedPlayer

    Boolean shotUsed = false

    Huntress() {
        super()
        this.weight = 3
        this.namePlural = 'Huntresses';
    }

    @Override
    void nightAction(NightState nightState) {
        if (!shotUsed && alive) {
            List<? extends Player> potentialKills = nightState.getLivePlayersKnownToTeam(this.team).findAll {
                Player player -> player.getIdentity() == Identity.WEREWOLF && player != this
            }
            if (potentialKills.size() == 0 && new Random().nextInt(2)) {
                potentialKills = nightState.getLivePlayersUnknownToTeam(this.team).findAll {
                    Player player -> player != this
                }
            }
            if (potentialKills.size() > 0) {
                killedPlayer = (Player) Utilities.pickRandomElement(potentialKills)
                nightState.addPlayerKill((Player) killedPlayer, this)
                shotUsed = true
            }
        }
    }

    @Override
    Integer getNightOrder() {
        return 5
    }

    @Override
    void updateStats(StatisticCollector stats, GameState gameState) {
        if (killedPlayer != null) {
            stats.add("Huntress - Killed ${killedPlayer.name}", Statistic.AggregateType.PERCENTAGE, 1)
        }
    }
}
