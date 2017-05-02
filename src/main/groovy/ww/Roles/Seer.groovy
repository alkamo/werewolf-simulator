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

class Seer extends Player implements NightActive, DeathActive {

    Seer(Parameters parameters, List<? extends Player> players) {
        super(parameters, players, 7)
        identityKnownBy.add(this)
    }

    @Override
    void nightAction(NightState nightState) {
        switch (parameters.seerClearPattern) {
            case Parameters.SeerClearPattern.RANDOM:
                List<? extends Player> potentialLooks = players.findAll {
                    Player player ->
                        alive && !identityKnownBy.contains(this) && player != this
                }
                Utilities.pickRandomElement(potentialLooks).identityKnownBy.add(this)
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
    void onDeath(Game.TurnType turnType) {
        List<? extends Player> appSeers = players.findAll { Player player ->
            (player.role instanceof ApprenticeSeer
                    && !player.active)
        }
        if (appSeers.size() != 0) {
            ApprenticeSeer promotedSeer = (ApprenticeSeer) Utilities.pickRandomElement(appSeers)
            promotedSeer.active = true
        }
    }
}
