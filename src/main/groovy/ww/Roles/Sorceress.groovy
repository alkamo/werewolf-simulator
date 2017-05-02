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

class Sorceress extends Player implements NightActive {

    Sorceress(Parameters parameters, List<? extends Player> players) {
        super(parameters, players, TeamType.WEREWOLF, Identity.VILLAGER, -3, false)
    }

    @Override
    void nightAction(NightState nightState) {
        List<? extends Player> potentialLooks = players.findAll {
            Player player ->
                (alive
                        && !identityKnownBy.contains(this)
                        && player != this
                        && team != this.team)
        }
        (Player) Utilities.pickRandomElement(potentialLooks).identityKnownBy.add(this)
    }

    @Override
    Integer getNightOrder() {
        return 14
    }
}
