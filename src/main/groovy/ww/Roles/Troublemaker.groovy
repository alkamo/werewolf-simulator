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

import ww.NightActive
import ww.NightState
import ww.NotYetImplementedPlayer
import ww.Parameters
import ww.Player

class Troublemaker extends NotYetImplementedPlayer implements NightActive {

    Troublemaker() {
        super()
        this.weight = -3
    }

    @Override
    void nightAction(NightState nightState) {

    }

    @Override
    Integer getNightOrder() {
        return 10
    }
}
