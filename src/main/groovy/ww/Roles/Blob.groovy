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
import ww.Actors.WinCondition
import ww.States.GameState
import ww.States.NightState
import ww.Statistic
import ww.StatisticCollector
import ww.TeamType

class Blob extends Player implements NightActive, WinCondition, ProvidesStats {

    List<? extends Player> blob = [this]

    Blob() {
        super()
        this.teamType = TeamType.SOLO
        this.weight = -1
        this.preventsVillageWin = true
    }

    @Override
    void nightAction(NightState nightState) {
        blob.add(nightState.getLeftNeighbor(blob.findAll { it.alive }.last()))
    }

    @Override
    Integer getNightOrder() {
        return 1
    }

    @Override
    Boolean checkForWin(GameState gameState) {
        return gameState.getLivePlayers().size() == blob.findAll { it.alive }.size()
    }

    @Override
    void updateStats(StatisticCollector stats, GameState gameState) {
        stats.add('Blob - Size',
                [Statistic.AggregateType.MAX, Statistic.AggregateType.MIN, Statistic.AggregateType.AVERAGE],
                blob.size())
        stats.add('Blob - Live Size',
                [Statistic.AggregateType.MAX, Statistic.AggregateType.MIN, Statistic.AggregateType.AVERAGE],
                blob.findAll { it.alive }.size())
    }
}
