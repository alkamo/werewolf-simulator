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

import ww.Actors.Player
import ww.Actors.SetupActive
import ww.Actors.ProvidesStats
import ww.States.GameState
import ww.States.SetupState
import ww.Statistic
import ww.StatisticCollector
import ww.Utilities

class Cupid extends Player implements SetupActive, ProvidesStats {
    Player lover1
    Player lover2

    Cupid() {
        super()
        this.weight = -3
    }

    @Override
    void onGameSetup(SetupState setupState) {
        List<? extends Player> potentialLovers = setupState.getOtherLivePlayers(this)
        lover1 = (Player) Utilities.pickRandomElement(potentialLovers)
        potentialLovers.remove(lover1)
        lover2 = (Player) Utilities.pickRandomElement(potentialLovers)
        setupState.addTwoWayDeathLink(lover1, lover2, this)
    }

    @Override
    void updateStats(StatisticCollector stats, GameState gameState) {
        if (lover1.class == lover2.class) {
            stats.add( "Cupid - Both lovers were ${lover1.namePlural}", Statistic.AggregateType.PERCENTAGE, 1)
        } else {
            stats.add("Cupid - ${lover1.name} was a lover", Statistic.AggregateType.PERCENTAGE, 1)
            stats.add("Cupid - ${lover2.name} was a lover", Statistic.AggregateType.PERCENTAGE, 1)
        }
        if (lover1.team != lover2.team) {
            stats.add("Cupid - Star-crossed lovers", Statistic.AggregateType.PERCENTAGE, 1)
        }
    }
}
