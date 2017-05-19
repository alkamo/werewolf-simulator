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
import ww.States.GameState


class DeathLink {
    Player drivingPlayer
    Player affectedPlayer
    Player linkedBy

    DeathLink(Player drivingPlayer, Player affectedPlayer, Player linkedBy) {
        this.drivingPlayer = drivingPlayer
        this.affectedPlayer = affectedPlayer
        this.linkedBy = linkedBy
    }

    void evaluateLink(GameState gameState) {
        if (!drivingPlayer.alive && affectedPlayer.alive) {
            affectedPlayer.kill(gameState, linkedBy)
        }
    }
}
