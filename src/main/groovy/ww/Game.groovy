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

package ww

import java.lang.reflect.Constructor


class Game {
    private Parameters parameters = new Parameters()
    private Integer day = 0
    private List<? extends Player> players = []
    private Map<TeamType, ? extends Team> teams = [:]

    enum TurnType {
        DAY,
        NIGHT
    }

    Game(Parameters parameters) {
        this.parameters = parameters
        parameters.roles.each() { Role role, Integer quantity ->
            if (!teams.containsKey(role.teamType)){
                Constructor<? extends Team> teamConstructor = role.teamType.teamClass.getConstructor();
                teams[role.teamType] = teamConstructor.newInstance()
            }
            quantity.times {
                Constructor<? extends Player> playerConstructor = role.roleClass.getConstructor();
                players.add(playerConstructor.newInstance(parameters, players, teams[role.teamType]))
            }
        }
        Collections.shuffle(players)
    }

    void Day() {
        lynch()
    }

    void Night() {
        NightState nightState = new NightState()
        List<? extends Player> nightActors = players
                .findAll{it.alive && null != it.role.nightActionOrder}
                .sort{it.role.nightActionOrder}
        nightActors.each{player ->
            player.nightAction(nightState)
        }
        shareKnowledge()
    }

    void shareKnowledge() {
        List<? extends Player> livePlayers = players
                .findAll{it.alive && it.identityKnownBy.size() != 0}
        livePlayers.each {player ->
            player.shareKnowledge()
        }
        teams.each {teamType, team ->
            team.shareKnowledge()
        }
    }

    void lynch() {

    }
}
