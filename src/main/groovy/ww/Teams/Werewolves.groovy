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

package ww.Teams

import ww.*
import ww.Roles.ApprenticeSeer
import ww.Roles.Seer

class Werewolves extends Team implements WinCondition, NightActive{
    Werewolves() {
        super()
        this.teamType = TeamType.WEREWOLF;
    }

    @Override
    void nightAction(NightState nightState) {
        if (nightState.nightNumber > 0) {
            List<? extends Player> potentialKills = players.findAll {
                Player player ->
                    (player.alive
                            && (player instanceof Seer || (player instanceof ApprenticeSeer && player.active))
                            && player.identityKnownByTeam.contains(this))
            }
            if (potentialKills.size() == 0) {
                potentialKills = players.findAll {
                    Player player ->
                        player.alive && player.teamType != this.teamType
                }
            }
            nightState.addTeamKill((Player) Utilities.pickRandomElement(potentialKills), this)
        }
    }

    @Override
    Integer getNightOrder() {
        return 1
    }

    @Override
    Boolean checkForWin() {
        Boolean won = false
        if (parameters.endGameAtParity) {
            won = (getLivePlayersOnTeam().size() == getLivePlayersNotOnTeam().size())
        } else {
            won = (getLivePlayersNotOnTeam().size() == 0)
        }

        return won
    }

    @Override
    void updateStats(Map<String, Statistic> stats) {
        Utilities.updateWinnerStats(this.name,stats,checkForWin())
    }
}
