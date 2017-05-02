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

import java.lang.reflect.Constructor


class Game {
    private Parameters parameters = new Parameters()
    private Integer day = 0
    private List<? extends Player> players = []
    private Map<TeamType, ? extends Team> teams = [:]

    enum TurnType {
        DAY,
        NIGHT
    }

    Game(Parameters parameters) {
        this.parameters = parameters
        setupPlayers()
        setupActions()
    }

    void setupPlayers(){
        parameters.roles.each() { Role role, Integer quantity ->
            quantity.times {
                Constructor<? extends Player> playerConstructor = role.roleClass.getConstructor();
                Player newPlayer = playerConstructor.newInstance(parameters, players)
                players.add(newPlayer)
                if (!teams.containsKey(newPlayer.teamType)) {
                    Constructor<? extends Team> teamConstructor = newPlayer.teamType.teamClass.getConstructor();
                    teams[newPlayer.teamType] = teamConstructor.newInstance()
                }
                newPlayer.team = teams[newPlayer.teamType]
            }
        }
        Collections.shuffle(players)
    }

    void setupActions() {
        List<? extends SetupActive> setupActors = []
        setupActors.addAll((List<? extends SetupActive>) players
                .findAll { it.alive && it instanceof SetupActive })
        setupActors.addAll((List<? extends SetupActive>) teams
                .findAll { it instanceof SetupActive })
        setupActors.each { player ->
            player.onGameSetup()
        }
    }

    void play() {
        Integer cycleNumber = 1
        while (!hasSomeoneWon()) {
            day(cycleNumber)
            if (!hasSomeoneWon()) {
                night(cycleNumber)
            }
            cycleNumber += 1
        }
    }

    void day(Integer dayNumber) {
        DayState dayState = new DayState(dayNumber)
        lynch(dayState)
        List<? extends DayActive> dayActors = []
        dayActors.addAll((List<? extends DayActive>) players
                .findAll { it.alive && it instanceof NightActive })
        dayActors.addAll((List<? extends DayActive>) teams
                .findAll { it instanceof DayActive })
        dayActors.each { player ->
            player.dayAction(dayState)
        }
        shareKnowledge()
    }

    void night(Integer nightNumber) {
        NightState nightState = new NightState(nightNumber)
        List<? extends NightActive> nightActors = []
        nightActors.addAll((List<? extends NightActive>) players
                .findAll { it.alive && it instanceof NightActive })
        nightActors.addAll((List<? extends NightActive>) teams
                .findAll { it instanceof NightActive })
        nightActors.each { player ->
            player.nightAction(nightState)
        }
        shareKnowledge()
    }

    void shareKnowledge() {
        List<? extends Player> livePlayers = players
                .findAll { it.alive && it.identityKnownBy.size() != 0 }
        livePlayers.each { player ->
            player.shareKnowledge()
        }
        teams.each { teamType, team ->
            team.shareKnowledge()
        }
    }

    Boolean hasSomeoneWon() {
        Boolean teamWon = teams.findAll { it instanceof WinCondition && it.checkForWinCondition } > 0
        Boolean playerWon = teams.findAll { it instanceof WinCondition && it.checkForWinCondition } > 0
        return teamWon || playerWon
    }
}
