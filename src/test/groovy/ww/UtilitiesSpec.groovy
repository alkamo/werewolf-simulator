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


class UtilitiesSpec extends Specification {
    def "GetListOffsetWrapping"() {
        setup:
        List<String> sourceList = ['A', 'B', 'C', 'D', 'E', 'F']
        expect:
        Utilities.getListOffsetWrapping(sourceList, startingPoint, offset) == result
        where:
        startingPoint | offset | result
        'A'           | 5      | 'F'
        'A'           | 4      | 'E'
        'A'           | 3      | 'D'
        'A'           | 2      | 'C'
        'A'           | 1      | 'B'
        'A'           | 0      | 'A'
        'A'           | -1      | 'F'
        'A'           | -2      | 'E'
        'A'           | -3      | 'D'
        'A'           | -4      | 'C'
        'A'           | -5      | 'B'
        'F'           | 5      | 'E'
        'F'           | 4      | 'D'
        'F'           | 3      | 'C'
        'F'           | 2      | 'B'
        'F'           | 1      | 'A'
        'F'           | 0      | 'F'
        'F'           | -1      | 'E'
        'F'           | -2      | 'D'
        'F'           | -3      | 'C'
        'F'           | -4      | 'B'
        'F'           | -5      | 'A'
    }
}
