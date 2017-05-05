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

package ww.States

import ww.Parameters
import ww.RoleSet
import ww.Actors.SetupActive


class SetupState extends GameState {
    Integer lynches = 1
    RoleSet roleSet

    SetupState(Integer cycleNumber, Parameters parameters, RoleSet roleSet) {
        super(cycleNumber, parameters, [], [:])
        this.turnType = TurnType.SETUP
        this.roleSet = roleSet
        this.cycleNumber = 0
    }

    @Override
    NightState getNextState() {
        return new NightState(0, parameters, players, teams)
    }

    @Override
    void execute() {
        this.roleSet.setupPlayersAndTeams(parameters, players, teams)
        List<? extends SetupActive> setupActors = []
        setupActors.addAll((List<? extends SetupActive>) players
                .findAll { it.alive && it instanceof SetupActive })
        setupActors.addAll((List<? extends SetupActive>) teams
                .values()
                .findAll { it instanceof SetupActive })
        setupActors.each { player ->
            player.onGameSetup(this)
        }
    }
}
