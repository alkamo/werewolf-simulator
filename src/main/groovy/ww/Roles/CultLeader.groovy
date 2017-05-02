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
import ww.Teams.Solo

class CultLeader extends Player implements NightActive, WinCondition {

    List<? extends Player> cultists = [this]

    CultLeader(Parameters parameters, List<? extends Player> players) {
        super(parameters, players, TeamType.SOLO, Identity.VILLAGER, 1, true)
    }

    @Override
    Boolean checkForWin() {
        return players.findAll { alive }.size() == cultists.findAll { alive }.size()
    }

    @Override
    void nightAction(NightState nightState) {
        List<? extends Player> potentialCultists = players.findAll {
            Player player ->
                (player.alive && !cultists.contains(player))
        }
        cultists.add((Player) Utilities.pickRandomElement(potentialCultists))
    }

    @Override
    Integer getNightOrder() {
        return 11
    }
}
