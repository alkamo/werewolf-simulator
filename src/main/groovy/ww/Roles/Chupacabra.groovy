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

class Chupacabra extends NotYetImplementedPlayer implements SoloPlayer {

    Chupacabra(Parameters parameters, List<? extends Player> players) {
        super(Role.CHUPACABRA, parameters, players)
    }

    @Override
    void nightAction(NightState nightState) {

    }


    @Override
    void onDeath(Game.TurnType turnType) {

    }

    @Override
    void onGameSetup() {

    }

    @Override
    Boolean checkForWin() {
        return (alive && players.findAll{alive}.size() == 1)
    }
}