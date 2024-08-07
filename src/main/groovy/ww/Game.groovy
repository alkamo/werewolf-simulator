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

import ww.States.GameState
import ww.States.SetupState

import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.RunnableFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class Game implements Callable {
    private Parameters parameters
    private RoleSet roleSet
    GameState currentState
    Boolean finished = false

    Game(Parameters parameters, RoleSet roleSet) {
        this.parameters = parameters
        this.roleSet = roleSet
    }

    void play() {
        currentState = new SetupState(0, parameters, this.roleSet)
        currentState.execute()
        while (!currentState.hasSomeoneWon()) {
            currentState = currentState.getNextState()
            currentState.execute()
        }
        finished = true;
    }

    @Override
    Game call() throws Exception {
        play()
        return this
    }
}
