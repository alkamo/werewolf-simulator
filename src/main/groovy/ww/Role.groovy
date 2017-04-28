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

import ww.Roles.*

enum Role {
    BODYGUARD(TeamType.VILLAGE, Identity.VILLAGER, Bodyguard.class, 3, false, 0),
    BLOB(TeamType.SOLO, Identity.VILLAGER, Blob.class, -1, true, 1),
    WITCH(TeamType.VILLAGE, Identity.VILLAGER, Witch.class, 4, false,2),
    SEER(TeamType.VILLAGE, Identity.VILLAGER, WolfCub.class, 7, false, 3),
    APPRENTICE_SEER(TeamType.VILLAGE, Identity.VILLAGER, ApprenticeSeer.class, 4, false, 4),
    HUNTRESS(TeamType.VILLAGE, Identity.VILLAGER, Huntress.class, 3, false,5),
    CHUPACABRA(TeamType.SOLO, Identity.VILLAGER, Chupacabra.class, 4, true, 6),
    TROUBLEMAKER(TeamType.VILLAGE, Identity.VILLAGER, Troublemaker.class, -3, false,10),
    CULT_LEADER(TeamType.CULT, Identity.VILLAGER, CultLeader.class, 1, true, 11),
    OLD_MAN(TeamType.VILLAGE, Identity.VILLAGER, OldMan.class, 0, false, 12),
    REVEALER(TeamType.VILLAGE, Identity.VILLAGER, Revealer.class, 4, false, 13),
    SORCERESS(TeamType.WEREWOLF, Identity.VILLAGER, Sorceress.class, -3, false, 14),
    BIG_BAD_WOLF(TeamType.WEREWOLF, Identity.WEREWOLF, BigBadWolf.class, -9, true, null),
    CUPID(TeamType.VILLAGE, Identity.VILLAGER, Cupid.class, -3, false, null),
    DIRE_WOLF(TeamType.WEREWOLF, Identity.WEREWOLF, DireWolf.class, -4, true, null),
    DISEASED(TeamType.VILLAGE, Identity.VILLAGER, Diseased.class, 3, false, null),
    DR_BOOM(TeamType.VILLAGE, Identity.VILLAGER, DrBoom.class, -2, false, null),
    HUNTER(TeamType.VILLAGE, Identity.VILLAGER, Hunter.class, 3, false, null),
    LYCAN(TeamType.VILLAGE, Identity.WEREWOLF, Lycan.class, -1, false, null),
    MARTYR(TeamType.VILLAGE, Identity.VILLAGER, Martyr.class, 3, false, null),
    PRINCE(TeamType.VILLAGE, Identity.VILLAGER, Prince.class, 3, false, null),
    TANNER(TeamType.SOLO, Identity.VILLAGER, Tanner.class, -2, false, null),
    VILLAGER(TeamType.VILLAGE, Identity.VILLAGER, Villager.class, 1, false, null),
    VIRGINIA_WOOLF(TeamType.VILLAGE, Identity.VILLAGER, VirginiaWoolf.class, -2, false, null),
    WEREWOLF(TeamType.WEREWOLF, Identity.WEREWOLF, Werewolf.class, -6, true, null),
    WOLF_CUB(TeamType.WEREWOLF, Identity.WEREWOLF, WolfCub.class, -8, true, null),
    WOLF_MAN(TeamType.WEREWOLF, Identity.VILLAGER, WolfMan.class, -9, true, null)

    TeamType teamType;
    Identity identity;
    Class roleClass;
    Integer weight;
    Boolean preventsVillageWin;
    Integer nightActionOrder;

    Role(TeamType teamType, Identity identity, Class roleClass, Integer weight, Boolean preventsVillageWin, Integer nightActionOrder) {
        this.teamType = teamType;
        this.identity = identity;
        this.roleClass = roleClass;
        this.weight = weight
        this.preventsVillageWin = preventsVillageWin
        this.nightActionOrder = nightActionOrder
    }
}
