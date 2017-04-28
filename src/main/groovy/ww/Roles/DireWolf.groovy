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
import ww.Identity
import ww.NightState
import ww.NotYetImplementedPlayer
import ww.Parameters
import ww.Player
import ww.Role

class DireWolf extends Player {

    DireWolf(Parameters parameters, List<? extends Player> players) {
        super(Role.DIRE_WOLF, parameters, players)
    }

    @Override
    void nightAction(NightState nightState) {

    }

    @Override
    void onDeath(Game.TurnType turnType) {

    }

    @Override
    void onGameSetup() {
        List<? extends Player> potentialCompanion = players.findAll {
            Player player ->
                (player != this
                && player.role.identity != Identity.WEREWOLF)
        }
        Player companion = potentialCompanion.get(new Random().nextInt(potentialCompanion.size()))
        companion.deathLinks.add(this)
    }
}
