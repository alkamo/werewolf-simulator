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
import ww.Actors.ProvidesStats
import ww.States.GameState
import ww.States.NightState
import ww.Statistic
import ww.StatisticCollector

class OldMan extends Player implements NightActive, ProvidesStats {

    OldMan() {
        super()
        this.weight = 0
        this.name = 'Old Man';
        this.namePlural = 'Old Men';
    }

    @Override
    void nightAction(NightState nightState) {
        if (nightState.players.findAll{it instanceof Werewolf}.size() == nightState.nightNumber) {
            nightState.addPlayerKill(this, this)
        }
    }

    @Override
    Integer getNightOrder() {
        return 12
    }

    @Override
    void updateStats(StatisticCollector stats, GameState gameState) {
        if (this.killedByPlayer == this) {
            stats.add('Old Man - Died of old age', Statistic.AggregateType.PERCENTAGE, 1)
        }
    }
}
