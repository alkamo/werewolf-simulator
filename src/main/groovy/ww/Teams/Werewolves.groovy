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

import ww.Actors.NightActive
import ww.Actors.Player
import ww.Actors.Team
import ww.Actors.WinCondition
import ww.Roles.ApprenticeSeer
import ww.Roles.Seer
import ww.Roles.Werewolf
import ww.States.GameState
import ww.States.NightState
import ww.TeamType
import ww.Utilities

class Werewolves extends Team implements WinCondition, NightActive {
    Werewolves() {
        super()
        this.teamType = TeamType.WEREWOLF;
    }

    @Override
    void nightAction(NightState nightState) {
        if (nightState.getLivePlayers().findAll {
            it instanceof Werewolf
        }.size() > 0 && nightState.getNightNumber() != 1) {
            if (nightState.nightNumber > 0) {
                List<? extends Player> potentialKills = nightState.getLivePlayersKnownToTeam(this).findAll {
                    Player player ->
                        (player instanceof Seer || (player instanceof ApprenticeSeer && player.active))
                }
                if (potentialKills.size() == 0) {
                    potentialKills = nightState.getLivePlayersNotOnTeam(this)
                }
                if (potentialKills.size() != 0) {
                    nightState.addTeamKill((Player) Utilities.pickRandomElement(potentialKills), this)
                }
            }
        }
    }

    @Override
    Integer getNightOrder() {
        return 1
    }

    @Override
    Boolean checkForWin(GameState gameState) {
        Boolean won = (parameters.endGameAtParity
                && gameState.getLivePlayersOnTeam(this).size() >= gameState.getLivePlayersNotOnTeam(this).size())
        won = won || gameState.getLivePlayersNotOnTeam(this).size() == 0
        return won
    }
}
