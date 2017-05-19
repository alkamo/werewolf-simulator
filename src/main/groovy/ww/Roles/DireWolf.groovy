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
import ww.Actors.Player
import ww.Actors.ProvidesStats
import ww.Actors.SetupActive
import ww.States.GameState
import ww.States.SetupState

class DireWolf extends Werewolf implements SetupActive, ProvidesStats {
    Player companion

    DireWolf() {
        super()
        this.weight = -4
        this.name = 'Dire Wolf';
        this.namePlural = 'Dire Wolves';
    }

    @Override
    void onGameSetup(SetupState setupState) {
        List<? extends Player> potentialCompanion = setupState.players.findAll {
            Player player ->
                (player != this
                        && player.identity != Identity.WEREWOLF)
        }
        companion = (Player) Utilities.pickRandomElement(potentialCompanion)
        setupState.addOneWayDeathLink(companion, this, this)
    }

    @Override
    void updateStats(StatisticCollector stats, GameState gameState) {
        if (this.killedByPlayer == this) {
            stats.add('Dire Wolf - Companion Killed', [Statistic.AggregateType.PERCENTAGE], 1)
        }
    }
}
