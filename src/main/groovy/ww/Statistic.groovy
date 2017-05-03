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

import java.math.RoundingMode


class Statistic {
    enum AggregateType {
        SUM('sum'),
        AVERAGE('avg'),
        PERCENTAGE('%'),
        MAX('max'),
        MIN('min')

        String label

        AggregateType(String label) {
            this.label = label
        }
    }

    List<Integer> values = []
    Integer scale = 2
    RoundingMode roundingMode = RoundingMode.HALF_UP
    String statName

    List<AggregateType> aggregateTypes = []

    Statistic(AggregateType aggregateType, String statName) {
        Statistic([aggregateType], 2, RoundingMode.HALF_UP, statName)
    }

    Statistic(AggregateType aggregateType, Integer scale, RoundingMode roundingMode, String statName) {
        Statistic([aggregateType], scale, roundingMode, statName)
    }

    Statistic(List<AggregateType> aggregateTypes, Integer scale, RoundingMode roundingMode, String statName) {
        this.aggregateTypes.addAll(aggregateTypes)
        this.scale = scale
        this.roundingMode = roundingMode
        this.statName = statName
    }

    BigDecimal getFinalValue(Integer iterations, AggregateType aggregateType) {
        BigDecimal finalValue = 0
        switch (aggregateType) {
            case AggregateType.SUM:
                values.each {
                    finalValue = finalValue.add(it)
                }
                break
            case AggregateType.AVERAGE:
                values.each {
                    finalValue = finalValue.add(it)
                }
                finalValue = finalValue.divide(iterations, scale, roundingMode)
                break
            case AggregateType.PERCENTAGE:
                values.each {
                    finalValue = finalValue.add(it)
                }
                finalValue = finalValue.divide(iterations, scale + 2, roundingMode).movePointRight(2)
                break
            case AggregateType.MAX:
                finalValue = values.max()
                break
            case AggregateType.MIN:
                finalValue = values.min()
                break
        }
        return finalValue
    }

    List<String> getFormattedFinalValues(Integer iterations) {
        List<String> values = []
        aggregateTypes.each {
            values.add("$statName($it.label) - ${getFinalValue(iterations, it)}")
        }
        return values
    }
}
