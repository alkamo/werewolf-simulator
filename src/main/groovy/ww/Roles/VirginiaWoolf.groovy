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

class VirginiaWoolf extends Player implements SetupActive, ProvidesStats {

    Player afraidPlayer

    VirginiaWoolf() {
        super()
        this.weight = -2
        this.name = 'Virginia Woolf';
    }

    @Override
    void onGameSetup(SetupState setupState) {
        List<? extends Player> potentialAfraid = setupState.getOtherLivePlayers(this)
        afraidPlayer = (Player) Utilities.pickRandomElement(potentialAfraid)
        setupState.addOneWayDeathLink(this, afraidPlayer, this)
    }

    @Override
    void updateStats(StatisticCollector stats, GameState gameState) {
        if (this.afraidPlayer.killedByPlayer == this) {
            stats.add("Virginia Woolf - Killed", [Statistic.AggregateType.PERCENTAGE], 1)
            stats.add("Virginia Woolf - Killed ${afraidPlayer.name}", [Statistic.AggregateType.PERCENTAGE], 1)
        }
    }
}
