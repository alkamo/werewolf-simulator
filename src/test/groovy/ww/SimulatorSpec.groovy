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

import org.junit.Rule
import org.junit.rules.TestName
import spock.lang.Specification
import spock.lang.Unroll


class SimulatorSpec extends Specification {
    @Rule TestName name = new TestName()

    @Unroll
    def "Main - #roleSet.name() - 200"() {
        setup:
        System.out.println(name.methodName)
        String[] args = ['-i 200', "-r ${roleSet.name()}"]
        expect:
        Simulator.main(args)
        where:
        roleSet << RoleSet.Predefined.values()
    }
}
