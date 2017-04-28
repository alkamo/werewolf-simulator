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

class Werewolves extends Team {
    Werewolves(Parameters parameters, List<? extends Player> players) {
        super(TeamType.WEREWOLF, parameters, players)
    }

    @Override
    void nightAction(NightState nightState) {
        List<? extends Player> potentialKills = players.findAll {
            Player player ->
                (player.alive
                        && (player.role == Role.SEER || (player.role == Role.APPRENTICE_SEER && player.active))
                        && player.identityKnownByTeam.contains(this))
        }
        if (potentialKills.size() == 0) {
            potentialKills = players.findAll {
                Player player ->
                    player.alive && player.role.teamType != this.teamType
            }
        }
        nightState.playersToBeKilled.add(
                new KillChoice(
                        playerToBeKilled: potentialKills.get(new Random().nextInt(potentialKills.size())),
                        killedByTeam: this))
    }

    @Override
    void onGameSetup() {
    }

    @Override
    Boolean checkForWin() {
        Boolean won = false
        if (parameters.endGameAtParity) {
            won = (players.findAll { it.role.teamType != this.teamType && it.alive }.size() ==
                    players.findAll { it.role.teamType == this.teamType && it.alive }.size())
        } else {
            won = (players.findAll { it.role.teamType != this.teamType && it.alive }.size() == 0)
        }

        return won
    }
}
