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


class RoleSet {
    enum Predefined {
        BASIC_7(
                [(Role.VILLAGER): 6,
                 (Role.WEREWOLF): 1]),
        STANDARD_9(
                [(Role.VILLAGER): 7,
                 (Role.SEER)    : 1,
                 (Role.WEREWOLF): 2]),
        BASIC_AND_TANNER_11(
                [(Role.VILLAGER): 9,
                 (Role.TANNER)  : 1,
                 (Role.WEREWOLF): 1])

        Map<Role, Integer> roleMap

        Predefined(Map<Role, Integer> roleMap) {
            this.roleMap = roleMap
        }
    }

    Map<Role, Integer> roleMap = [:]

    RoleSet(Map<Role, Integer> roleMap) {
        this.roleMap << roleMap
    }

    RoleSet(Predefined predefinedRoleList) {
        this.roleMap << predefinedRoleList.roleMap
    }

    void setupPlayersAndTeams(Parameters parameters, List<? extends Player> players, Map<TeamType, ? extends Team> teams) {
        roleMap.each() { Role role, Integer quantity ->
            quantity.times {
                Player newPlayer = (Player) role.roleClass.newInstance()
                newPlayer.players = players
                newPlayer.parameters = parameters
                players.add(newPlayer)
                if (!teams.containsKey(newPlayer.teamType)) {
                    Team newTeam = (Team) newPlayer.teamType.teamClass.newInstance()
                    newTeam.parameters = parameters
                    newTeam.players = players
                    teams[newPlayer.teamType] = newTeam
                }
                newPlayer.team = teams[newPlayer.teamType]
            }
        }
        Collections.shuffle(players)
    }
}
