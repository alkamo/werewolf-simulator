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

import ww.Actors.DeathActive
import ww.Actors.Player
import ww.Actors.ProvidesStats
import ww.States.GameState
import ww.Statistic
import ww.StatisticCollector

class MadBomber extends Player implements DeathActive, ProvidesStats {
    Player killedPlayer1
    Player killedPlayer2

    MadBomber() {
        super()
        this.weight = -2
        this.name = 'Mad Bomber';
    }

    @Override
    void onDeath(GameState gameState) {
        List<? extends Player> livePlayers = gameState.getLivePlayers()
        Integer bomberIndex = livePlayers.findIndexOf { it == this }
        if (this != livePlayers.first()) {
            killedPlayer1 = livePlayers[bomberIndex - 1]
        } else {
            killedPlayer1 = livePlayers.last()
        }
        gameState.addPlayerKill(killedPlayer1, this)
        if (this != livePlayers.last()) {
            killedPlayer2 = livePlayers[bomberIndex + 1]
        } else {
            killedPlayer2 = livePlayers.first()
        }
        gameState.addPlayerKill(killedPlayer2, this)
    }

    @Override
    void updateStats(StatisticCollector stats, GameState gameState) {
        if (!alive) {
            if (killedPlayer1.class == killedPlayer2.class) {
                stats.add("Mad Bomber - Blew up 2 ${killedPlayer1.namePlural}", Statistic.AggregateType.PERCENTAGE, 1)
            } else {
                stats.add("Mad Bomber - Blew up ${killedPlayer1.name}", Statistic.AggregateType.PERCENTAGE, 1)
                stats.add("Mad Bomber - Blew up ${killedPlayer2.name}", Statistic.AggregateType.PERCENTAGE, 1)
            }
        }
    }
}
