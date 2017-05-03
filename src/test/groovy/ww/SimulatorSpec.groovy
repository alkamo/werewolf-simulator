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

import spock.lang.Specification


class SimulatorSpec extends Specification {
    def "Main - Basic(1000)"() {
        setup:
        String[] args = ['-i 200']
        expect:
        Simulator.main(args)
    }
    def "Main - Basic_Tanner(1000)"() {
        setup:
        String[] args = ['-i 200','-r BASIC_AND_TANNER_11']
        expect:
        Simulator.main(args)
    }
    def "Main - Standard(1000)"() {
        setup:
        String[] args = ['-i 200','-r STANDARD_9']
        expect:
        Simulator.main(args)
    }
}
