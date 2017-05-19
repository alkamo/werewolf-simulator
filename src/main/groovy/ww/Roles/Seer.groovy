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
import ww.Actors.DeathActive
import ww.Actors.NightActive
import ww.Actors.Player
import ww.States.GameState
import ww.States.NightState

class Seer extends Player implements NightActive, DeathActive {

    Seer() {
        super()
        this.weight = 7
        identityKnownBy.add(this)
    }

    @Override
    void nightAction(NightState nightState) {
        switch (parameters.seerClearPattern) {
            case Parameters.SeerClearPattern.RANDOM:
                List<? extends Player> potentialLooks = nightState.getOtherLivePlayers(this).findAll {
                    Player player ->
                        !player.identityKnownBy.contains(this)
                }
                if (potentialLooks.size() > 0) {
                    Utilities.pickRandomElement(potentialLooks).identityKnownBy.add(this)
                }
                break;
            default:
                throw new Exception('Not yet implemeted')
        }
    }

    @Override
    Integer getNightOrder() {
        return 3
    }

    @Override
    void onDeath(GameState gameState) {
        List<? extends Player> appSeers = gameState.players.findAll { Player player ->
            (player instanceof ApprenticeSeer
                    && !player.active)
        }
        if (appSeers.size() != 0) {
            ApprenticeSeer promotedSeer = (ApprenticeSeer) Utilities.pickRandomElement(appSeers)
            promotedSeer.active = true
        }
    }
}
