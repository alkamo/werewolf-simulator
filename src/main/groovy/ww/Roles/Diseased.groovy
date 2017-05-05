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

import ww.Actors.DeathActive
import ww.States.GameState
import ww.Actors.NotYetImplementedPlayer

class Diseased extends NotYetImplementedPlayer implements DeathActive {

    Diseased() {
        super()
        this.weight = 3
    }

    @Override
    void onDeath(GameState gameState) {

    }
}
