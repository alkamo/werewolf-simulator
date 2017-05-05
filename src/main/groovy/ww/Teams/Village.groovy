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

package ww.Teams

import ww.*
import ww.Actors.Team
import ww.States.GameState

class Village extends Team implements WinCondition {
    Village() {
        super()
        this.teamType = TeamType.VILLAGE;
    }

    @Override
    Boolean checkForWin(GameState gameState) {
        return (gameState.players.findAll { it.preventsVillageWin && it.alive }.size() == 0
                && gameState.getLivePlayersOnTeam(this).size() > 0)
    }

    @Override
    void updateStats(Map<String, Statistic> stats, GameState gameState) {
        Utilities.updateWinnerStats(this.name,stats,checkForWin(gameState))
    }
}
