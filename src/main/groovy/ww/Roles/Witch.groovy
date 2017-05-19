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
import ww.Actors.NightActive
import ww.Actors.Player
import ww.Actors.ProvidesStats
import ww.States.GameState
import ww.States.NightState

class Witch extends Player implements NightActive, ProvidesStats {
    Boolean usedHeal = false
    Boolean usedKill = false
    Player healedPlayer
    Player killedPlayer

    Witch() {
        super()
        this.weight = 4
        this.namePlural = 'Witches';
    }

    void useKillPotionOptionally(NightState nightState) {
        if (!this.usedKill) {
            if (new Random().nextInt(2)
                    || nightState.playersToBeKilled.find {
                (it.killedByTeam != null
                        && it.killedByTeam.teamType == TeamType.WEREWOLF
                        && it.playerToBeKilled == this) != null
            }) {
                List<? extends Player> potentialKills = nightState.getLivePlayersKnownToTeam(this.team).findAll {
                    Player player ->
                        player.identity == Identity.WEREWOLF
                }
                if (potentialKills.size() == 0) {
                    potentialKills = nightState.getOtherLivePlayers(this)
                }
                killedPlayer = (Player) Utilities.pickRandomElement(potentialKills)
                nightState.addPlayerKill(killedPlayer, this)
                this.usedKill = true
            }
        }
    }

    void useSavePotionOptionally(NightState nightState) {
        if (!this.usedHeal) {
            KillChoice saveChoice = nightState.playersToBeKilled.find {
                it.killedByTeam != null && it.killedByTeam.teamType == TeamType.WEREWOLF && it.playerToBeKilled == this
            }
            if (saveChoice == null && new Random().nextInt(2)) {
                List<KillChoice> savablePlayers = nightState.playersToBeKilled.findAll {
                    (it.killedByTeam != null && it.killedByTeam.teamType == TeamType.WEREWOLF)
                }
                saveChoice = (KillChoice) Utilities.pickRandomElement(savablePlayers)
            }
            if (saveChoice != null) {
                healedPlayer = saveChoice.playerToBeKilled
                nightState.removeKill(healedPlayer)
                this.usedHeal = true
            }
        }
    }

    @Override
    void nightAction(NightState nightState) {
        useSavePotionOptionally(nightState)
        useKillPotionOptionally(nightState)
    }

    @Override
    Integer getNightOrder() {
        return 3
    }

    @Override
    void updateStats(StatisticCollector stats, GameState gameState) {
        if (healedPlayer != null) {
            stats.add("Witch - Healed ${healedPlayer.name}", Statistic.AggregateType.PERCENTAGE, 1)
        }
        if (killedPlayer != null) {
            stats.add("Witch - Killed ${killedPlayer.name}", Statistic.AggregateType.PERCENTAGE, 1)
        }

    }
}
