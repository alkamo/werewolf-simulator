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

import ww.NightState

class ApprenticeSeer extends Seer {
    Boolean active = false

    ApprenticeSeer() {
        super()
        weight = 4
        this.name = 'Apprentice Seer';
    }

    @Override
    void nightAction(NightState nightState) {
        if (active) {
            super.nightAction(nightState)
        }
    }

    @Override
    Integer getNightOrder() {
        return 4
    }
}
