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

class DireWolf extends Player implements SetupActive {

    DireWolf() {
        super()
        this.teamType = TeamType.WEREWOLF
        this.identity = Identity.WEREWOLF
        this.weight = -4
        this.preventsVillageWin = true
        this.playerName = 'Dire Wolf';
    }

    @Override
    void onGameSetup() {
        List<? extends Player> potentialCompanion = players.findAll {
            Player player ->
                (player != this
                        && player.identity != Identity.WEREWOLF)
        }
        Player companion = (Player) Utilities.pickRandomElement(potentialCompanion)
        companion.deathLinks.add(this)
    }
}
