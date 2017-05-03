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

class Blob extends NotYetImplementedPlayer implements NightActive, WinCondition {

    List<? extends Player> blob

    Blob() {
        super()
        this.teamType = TeamType.SOLO
        this.weight = -1
        this.preventsVillageWin = true
    }

    @Override
    void nightAction(NightState nightState) {

    }

    @Override
    Integer getNightOrder() {
        return 1
    }

    @Override
    Boolean checkForWin() {
        return players.findAll { alive }.size() == blob.findAll { alive }.size()
    }

    @Override
    void updateStats(Map<String, Statistic> stats) {
        Utilities.updateWinnerStats(this.name,stats,checkForWin())
    }
}
