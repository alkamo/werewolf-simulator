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

import ww.Game
import ww.NightState
import ww.Parameters
import ww.Player
import ww.Role

class Huntress extends Player {

    Boolean shotUsed = false

    Huntress(Parameters parameters, List<? extends Player> players) {
        super(Role.HUNTRESS, parameters, players)
    }

    @Override
    void nightAction(NightState nightState) {
        if (!shotUsed && alive) {
            List<? extends Player> potentialKills = players.findAll {
                Player player ->
                    alive && identityKnownBy.contains(this) && getIdentity() == Role.Identity.WEREWOLF
            }
            if (potentialKills.size() == 0 && new Random().nextInt(2)) {
                potentialKills = players.findAll {
                    Player player ->
                        alive && !identityKnownBy.contains(this) && player != this
                }
            }
            potentialKills.get(new Random().nextInt(potentialKills.size())).kill(Game.TurnType.NIGHT)
        }
    }


    @Override
    void onDeath(Game.TurnType turnType) {

    }

    @Override
    void onGameSetup() {

    }
}
