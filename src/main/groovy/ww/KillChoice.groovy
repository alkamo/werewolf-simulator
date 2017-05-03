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


class KillChoice {
    Player playerToBeKilled
    Player killedByPlayer
    Team killedByTeam
    GameState gameState

    KillChoice(Player player, Team killedBy, GameState gameState) {
        this.playerToBeKilled = player
        this.killedByTeam = killedBy
        this.gameState = gameState
    }

    KillChoice(Player player, Player killedBy, GameState gameState) {
        this.playerToBeKilled = player
        this.killedByPlayer = killedBy
        this.gameState = gameState
    }

    void kill() {
        playerToBeKilled.kill(gameState.turnType)
    }
}
