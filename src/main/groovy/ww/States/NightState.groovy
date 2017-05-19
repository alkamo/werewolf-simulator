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

import ww.Actors.NightActive
import ww.Parameters
import ww.Actors.Player
import ww.Actors.Team
import ww.TeamType


class NightState extends GameState {
    Integer werewolfKills = 1

    NightState(GameState gameState) {
        super(gameState)
        this.turnType = TurnType.NIGHT
    }

    NightState(Integer cycleNumber, Parameters parameters, List<? extends Player> players, Map<TeamType, ? extends Team> teams) {
        super(cycleNumber, parameters, players, teams)
        this.turnType = TurnType.NIGHT
    }

    @Override
    DayState getNextState() {
        return new DayState(this)
    }

    @Override
    void execute() {
        List<? extends NightActive> nightActors = []
        nightActors.addAll((List<? extends NightActive>) players
                .findAll { it.alive && it instanceof NightActive })
        nightActors.addAll((List<? extends NightActive>) teams
                .values()
                .findAll { it instanceof NightActive })
        nightActors.sort{it.getNightOrder()}.each { player ->
            player.nightAction(this)
        }
        killSelectedPlayers()
        shareKnowledge()
    }

    Integer getNightNumber() {
        return cycleNumber
    }
}
