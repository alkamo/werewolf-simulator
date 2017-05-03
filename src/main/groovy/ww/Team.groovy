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

abstract class Team {
    TeamType teamType
    List<? extends Player> players
    Parameters parameters
    String teamName

    Team() {
        this.teamName = this.getClass().getSimpleName()
    }

    public void shareKnowledge() {
        getLivePlayersOnTeam().each{teamPlayer ->
            getLivePlayersKnownToTeam().each{ knownPlayer ->
                if (!knownPlayer.identityKnownBy.contains(teamPlayer)) {
                    knownPlayer.identityKnownBy.add(teamPlayer)
                }
            }
        }
    }

    List<? extends Player> getLivePlayersOnTeam() {
        return players.findAll { it.teamType == this.teamType && it.alive }
    }

    List<? extends Player> getLivePlayersNotOnTeam() {
        return players.findAll { it.teamType != this.teamType && it.alive }
    }

    List<? extends Player> getLivePlayersKnownToTeam() {
        return players.findAll { it.alive && it.identityKnownByTeam.contains(this) }
    }

    List<? extends Player> getLivePlayersUnknownToTeam() {
        return players.findAll { it.alive && !it.identityKnownByTeam.contains(this) }
    }
}
