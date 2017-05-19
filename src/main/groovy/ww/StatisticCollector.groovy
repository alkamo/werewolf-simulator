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
import ww.Actors.ProvidesStats
import ww.Actors.Team
import ww.Actors.WinCondition
import ww.States.GameState

import java.math.RoundingMode


class StatisticCollector {
    Map<String, Statistic> stats = [:]

    void add(String statName, Statistic.AggregateType aggregateType, Integer value) {
        add(statName, [aggregateType], value)
    }

    void add(String statName, List<Statistic.AggregateType> aggregateTypes, Integer value) {
        add(statName, aggregateTypes, 2, RoundingMode.HALF_UP, value)
    }

    void add(String statName,
             List<Statistic.AggregateType> aggregateTypes,
             Integer scale,
             Integer value) {
        add(statName, aggregateTypes, scale, RoundingMode.HALF_UP, value)
    }

    void add(String statName,
             List<Statistic.AggregateType> aggregateTypes,
             Integer scale,
             RoundingMode roundingMode,
             Integer value) {
        if (!stats.keySet().contains(statName)) {
            stats[statName] = new Statistic(aggregateTypes, scale, roundingMode, statName)
        }
        stats[statName].values.add(value)
    }

    void collectStats(Game game) {
        collectStats(game.currentState)
    }

    void collectStats(GameState gameState) {
        gameState.players.findAll {
            it instanceof WinCondition && it.checkForWin(gameState)
        }.each { Player it ->
            add("! Winner - ${it.name}".toString(), [Statistic.AggregateType.PERCENTAGE], 1)
        }
        gameState.teams.values().findAll {
            it instanceof WinCondition && it.checkForWin(gameState)
        }.each { Team it ->
            add("! Winner - ${it.name}".toString(), [Statistic.AggregateType.PERCENTAGE], 1)
        }
        gameState.players.findAll {
            it instanceof ProvidesStats
        }.each { ProvidesStats it ->
            it.updateStats(this, gameState)
        }
        gameState.teams.values().findAll {
            it instanceof ProvidesStats
        }.each { ProvidesStats it ->
            it.updateStats(this, gameState)
        }
        add("! Game Weight", [Statistic.AggregateType.AVERAGE], 0, gameState.players.sum { it.weight })
        add("! Players", [Statistic.AggregateType.AVERAGE], 0, gameState.players.size())
        add("! Survivors", [Statistic.AggregateType.AVERAGE], gameState.getLivePlayers().size())
        if (gameState.whoWon().size() > 1) {
            add("! Winners - Multiple", [Statistic.AggregateType.PERCENTAGE], 1)
        }
        if (gameState.turnType == GameState.TurnType.NIGHT) {
            add("! Ended During Night", [Statistic.AggregateType.PERCENTAGE], 1)
        } else {
            add("! End During Day", [Statistic.AggregateType.PERCENTAGE], 1)
        }
        add("! Ended on Round",
                [Statistic.AggregateType.AVERAGE,
                 Statistic.AggregateType.MIN,
                 Statistic.AggregateType.MAX],
                0,
                RoundingMode.FLOOR,
                gameState.cycleNumber)
    }

    void print(Integer iterations) {
        stats.sort { e1, e2 -> e1.key <=> e2.key }.each { String key, Statistic value ->
            value.getFormattedFinalValues(iterations).each {
                System.out.println(it)
            }

        }
    }
}
