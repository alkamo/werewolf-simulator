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


class Parameters {
    public enum SeerClearPattern {
        RANDOM,
        ALTERNATE_SIDES,
        ONE_SIDE,
        SHORT_SIDE
    }

    Boolean seerClearsIfUnbrokenPath = true
    Boolean firstDayLynch = true
    SeerClearPattern seerClearPattern = SeerClearPattern.ALTERNATE_SIDES
    Integer lynchThreshold = 0
    Boolean werewolvesChooseLycan = false
    Boolean werewolvesChooseMinion = false
    Boolean witchSeesVictimAfterSave = false
    Boolean endGameAtParity = false

    Map<Role, Integer> roles = [(Role.VILLAGER): 6,
                                (Role.WEREWOLF): 1]
}
