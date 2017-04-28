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

class Seer extends Player {

    Seer(Parameters parameters, List<? extends Player> players) {
        super(Role.SEER, parameters, players)
    }

    @Override
    void nightAction(NightState nightState) {
        switch (parameters.seerClearPattern) {
            case Parameters.SeerClearPattern.RANDOM:
                List<? extends Player> potentialLooks = players.findAll {
                    Player player ->
                        alive && !identityKnownBy.contains(this) && player != this
                }
                potentialLooks.get(new Random().nextInt(potentialLooks.size())).identityKnownBy.add(this)
                break;
            default:
                throw new Exception('Not yet implemeted')
        }
    }


    @Override
    void onDeath(Game.TurnType turnType) {
        List<? extends Player> appSeers = players.findAll {Player player ->
            (player.role == Role.APPRENTICE_SEER
            && !player.active)}
        if (appSeers.size() != 0) {
            ApprenticeSeer promotedSeer = (ApprenticeSeer) appSeers.get(new Random().nextInt(appSeers.size()))
            promotedSeer.active = true
        }
    }

    @Override
    void onGameSetup() {

    }
}
