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
import ww.Actors.NightActive
import ww.Actors.Player
import ww.States.NightState

class Sorceress extends Player implements NightActive {

    Sorceress() {
        super()
        this.teamType = TeamType.WEREWOLF
        this.identity = Identity.VILLAGER
        this.weight = -3
        this.preventsVillageWin = true
    }

    @Override
    void nightAction(NightState nightState) {
        List<? extends Player> potentialLooks = nightState.getLivePlayersNotOnTeam(this.team).findAll {
            Player player ->
                (!identityKnownBy.contains(this))
        }
        Player pickedPlayer = Utilities.pickRandomElement(potentialLooks)
        if (pickedPlayer != null) {
            pickedPlayer.identityKnownBy.add(this)
        }
    }

    @Override
    Integer getNightOrder() {
        return 14
    }
}
