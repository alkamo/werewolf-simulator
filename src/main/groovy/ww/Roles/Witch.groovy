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

class Witch extends NotYetImplementedPlayer implements NightActive {
    Boolean usedHeal = false
    Boolean usedKill = false

    Witch(Parameters parameters, List<? extends Player> players) {
        super(parameters, players, 4)
    }

    void useKillPotion(NightState nightState) {
        if (!this.usedKill) {
            List<? extends Player> potentialKills = players.findAll {
                Player player ->
                    (player.alive
                            && player.identityKnownByTeam.contains(this))
            }
            if (potentialKills.size() == 0) {
                potentialKills = players.findAll {
                    Player player ->
                        player.alive && player != this
                }
            }
            nightState.playersToBeKilled.add(
                    new KillChoice(
                            playerToBeKilled: (Player) Utilities.pickRandomElement(potentialKills),
                            killedByPlayer: this))

        }
    }

    void useKillPotionOptionally(NightState nightState) {
        if (new Random().nextInt(2) || null != nightState.playersToBeKilled.find {
            it.killedByTeam.teamType == TeamType.WEREWOLF && it.playerToBeKilled == this
        }) {
            this.useKillPotion(nightState)
        }
    }

    void useSavePotion(NightState nightState, Player player) {
        if (!this.usedHeal && null != player) {
            KillChoice saveChoice = nightState.playersToBeKilled.find { it.playerToBeKilled == player }
            nightState.playersToBeKilled.remove(saveChoice)
            this.usedHeal = false
        }
    }

    void useSavePotionOptionally(NightState nightState) {
        Player saveChoice
        if (null != nightState.playersToBeKilled.find {
            it.killedByTeam.teamType == TeamType.WEREWOLF && it.playerToBeKilled == this
        }) {
            saveChoice = this
        } else if (new Random().nextInt(2)) {
            List<KillChoice> savablePlayers = nightState.playersToBeKilled.findAll {
                (it.killedByTeam.teamType == TeamType.WEREWOLF)
            }
            saveChoice = (Player) Utilities.pickRandomElement(savablePlayers).playerToBeKilled
        }
        if (null != saveChoice) {
            this.useSavePotion(nightState, saveChoice)
        }
    }

    @Override
    void nightAction(NightState nightState) {
        useSavePotionOptionally(nightState)
        useKillPotionOptionally(nightState)
    }

    @Override
    Integer getNightOrder() {
        return 2
    }
}
