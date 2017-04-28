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

//Complete
class Cupid extends Player {

    Cupid(Parameters parameters, List<? extends Player> players) {
        super(Role.CUPID, parameters, players)
    }

    @Override
    void nightAction(NightState nightState) {

    }


    @Override
    void onDeath(Game.TurnType turnType) {

    }

    @Override
    void onGameSetup() {
        List<? extends Player> potentialLovers = players.findAll {
            Player player ->
                player != this
        }
        Player lover1 = potentialLovers.get(new Random().nextInt(potentialLovers.size()))
        potentialLovers.remove(lover1)
        Player lover2 = potentialLovers.get(new Random().nextInt(potentialLovers.size()))
        lover1.deathLinks.add(lover2)
        lover2.deathLinks.add(lover1)
    }
}
