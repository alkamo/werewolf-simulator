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

import ww.Actors.Player
import ww.Actors.Team


class RoleSet {
    enum Predefined {
        BASIC_7(
                [(Role.VILLAGER): 6,
                 (Role.WEREWOLF): 1]),
        STANDARD_6(
                [(Role.VILLAGER): 4,
                 (Role.SEER)    : 1,
                 (Role.WEREWOLF): 1]),
        STANDARD_7(
                [(Role.VILLAGER): 5,
                 (Role.SEER)    : 1,
                 (Role.WEREWOLF): 1]),
        STANDARD_8(
                [(Role.VILLAGER): 6,
                 (Role.SEER)    : 1,
                 (Role.WEREWOLF): 1]),
        STANDARD_9(
                [(Role.VILLAGER): 6,
                 (Role.SEER)    : 1,
                 (Role.WEREWOLF): 2]),
        STANDARD_10(
                [(Role.VILLAGER): 7,
                 (Role.SEER)    : 1,
                 (Role.WEREWOLF): 2]),
        STANDARD_11(
                [(Role.VILLAGER): 8,
                 (Role.SEER)    : 1,
                 (Role.WEREWOLF): 2]),
        STANDARD_12(
                [(Role.VILLAGER): 8,
                 (Role.SEER)    : 1,
                 (Role.WEREWOLF): 3]),
        STANDARD_13(
                [(Role.VILLAGER): 9,
                 (Role.SEER)    : 1,
                 (Role.WEREWOLF): 3]),
        STANDARD_14(
                [(Role.VILLAGER): 10,
                 (Role.SEER)    : 1,
                 (Role.WEREWOLF): 3]),
        STANDARD_15(
                [(Role.VILLAGER): 11,
                 (Role.SEER)    : 1,
                 (Role.WEREWOLF): 3]),
        BASIC_AND_TANNER_10(
                [(Role.VILLAGER): 8,
                 (Role.TANNER)  : 1,
                 (Role.WEREWOLF): 1]),
        BASIC_AND_SEER_8(
                [(Role.VILLAGER): 5,
                 (Role.SEER)    : 1,
                 (Role.WEREWOLF): 2]),
        BASIC_AND_SEER_AND_WOLF_MAN_11(
                [(Role.VILLAGER): 8,
                 (Role.SEER)    : 1,
                 (Role.WEREWOLF): 1,
                 (Role.WOLF_MAN): 1]),
        BASIC_AND_SEER_AND_APPRENTICE_12(
                [(Role.VILLAGER)       : 7,
                 (Role.SEER)           : 1,
                 (Role.APPRENTICE_SEER): 1,
                 (Role.WEREWOLF)       : 3]),
        BASIC_AND_SEER_AND_LYCAN_10(
                [(Role.VILLAGER): 6,
                 (Role.SEER)    : 1,
                 (Role.LYCAN)   : 1,
                 (Role.WEREWOLF): 2]),
        BASIC_AND_CHUPACABRA_11(
                [(Role.VILLAGER)  : 8,
                 (Role.CHUPACABRA): 1,
                 (Role.WEREWOLF)  : 2]),
        BASIC_AND_CUPID_11(
                [(Role.VILLAGER): 9,
                 (Role.CUPID)   : 1,
                 (Role.WEREWOLF): 1]),
        BASIC_AND_DIRE_WOLF_12(
                [(Role.VILLAGER) : 10,
                 (Role.DIRE_WOLF): 1,
                 (Role.WEREWOLF) : 1]),
        BASIC_AND_HUNTER_12(
                [(Role.VILLAGER): 9,
                 (Role.HUNTER)  : 1,
                 (Role.WEREWOLF): 2]),
        BASIC_AND_HUNTRESS_12(
                [(Role.VILLAGER): 9,
                 (Role.HUNTRESS): 1,
                 (Role.WEREWOLF): 2]),
        UNCERTAINTY_10(
                [(Role.VILLAGER) : 5,
                 (Role.WEREWOLF) : 1,
                 (Role.SEER)     : 1,
                 (Role.SORCERESS): 1,
                 (Role.LYCAN)    : 1,
                 (Role.HUNTER)   : 1]),
        BASIC_AND_MAD_BOMBER_10(
                [(Role.VILLAGER)  : 8,
                 (Role.MAD_BOMBER): 1,
                 (Role.WEREWOLF)  : 1]),
        BASIC_AND_OLD_MAN_8(
                [(Role.VILLAGER): 6,
                 (Role.OLD_MAN) : 1,
                 (Role.WEREWOLF): 1]),
        BASIC_AND_VIRGINIA_WOOLF_10(
                [(Role.VILLAGER)      : 8,
                 (Role.VIRGINIA_WOOLF): 1,
                 (Role.WEREWOLF)      : 1]),
        BASIC_AND_WITCH_11(
                [(Role.VILLAGER): 8,
                 (Role.WITCH)   : 1,
                 (Role.WEREWOLF): 2]),
        BASIC_AND_BODYGUARD_12(
                [(Role.VILLAGER) : 9,
                 (Role.BODYGUARD): 1,
                 (Role.WEREWOLF) : 2]),
        EVERYTHING(
                [(Role.VILLAGER)       : 8,
                 (Role.WEREWOLF)       : 1,
                 (Role.SEER)           : 1,
                 (Role.APPRENTICE_SEER): 1,
                 (Role.SORCERESS)      : 1,
                 (Role.LYCAN)          : 1,
                 (Role.HUNTER)         : 1,
                 (Role.TANNER)         : 1,
                 (Role.CHUPACABRA)     : 1,
                 (Role.DIRE_WOLF)      : 1,
                 (Role.CUPID)          : 1,
                 (Role.HUNTRESS)       : 1,
                 (Role.WOLF_MAN)       : 1,
                 (Role.LYCAN)          : 1,
                 (Role.MAD_BOMBER)     : 1,
                 (Role.OLD_MAN)        : 1,
                 (Role.VIRGINIA_WOOLF) : 1,
                 (Role.WITCH)          : 1,
                 (Role.BODYGUARD)      : 1])

        Map<Role, Integer> roleMap

        Predefined(Map<Role, Integer> roleMap) {
            this.roleMap = roleMap
        }
    }

    String name
    Map<Role, Integer> roleMap = [:]

    RoleSet(String name, Map<Role, Integer> roleMap) {
        this.name = name
        this.roleMap << roleMap
    }

    RoleSet(Predefined predefinedRoleSet) {
        this.name = predefinedRoleSet.name()
        this.roleMap << predefinedRoleSet.roleMap
    }

    void setupPlayersAndTeams(Parameters parameters, List<? extends Player> players, Map<TeamType, ? extends Team> teams) {
        roleMap.each() { Role role, Integer quantity ->
            quantity.times {
                Player newPlayer = (Player) role.roleClass.newInstance()
                newPlayer.parameters = parameters
                players.add(newPlayer)
                if (!teams.containsKey(newPlayer.teamType)) {
                    Team newTeam = (Team) newPlayer.teamType.teamClass.newInstance()
                    newTeam.parameters = parameters
                    teams[newPlayer.teamType] = newTeam
                }
                newPlayer.team = teams[newPlayer.teamType]
            }
        }
        Collections.shuffle(players)
    }
}
