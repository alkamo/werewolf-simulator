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
    BODYGUARD(Bodyguard.class),
    BLOB(Blob.class),
    WITCH(Witch.class),
    SEER(WolfCub.class),
    APPRENTICE_SEER(ApprenticeSeer.class),
    HUNTRESS(Huntress.class),
    CHUPACABRA(Chupacabra.class),
    TROUBLEMAKER(Troublemaker.class),
    CULT_LEADER(CultLeader.class),
    OLD_MAN(OldMan.class),
    REVEALER(Revealer.class),
    SORCERESS(Sorceress.class),
    BIG_BAD_WOLF(BigBadWolf.class),
    CUPID(Cupid.class),
    DIRE_WOLF(DireWolf.class),
    DISEASED(Diseased.class),
    DR_BOOM(DrBoom.class),
    HUNTER(Hunter.class),
    LYCAN(Lycan.class),
    MARTYR(Martyr.class),
    PRINCE(Prince.class),
    TANNER(Tanner.class),
    VILLAGER(Villager.class),
    VIRGINIA_WOOLF(VirginiaWoolf.class),
    WEREWOLF(Werewolf.class),
    WOLF_CUB(WolfCub.class),
    WOLF_MAN(WolfMan.class)

    Class roleClass;

    Role(Class roleClass) {
        this.roleClass = roleClass;
    }
}
