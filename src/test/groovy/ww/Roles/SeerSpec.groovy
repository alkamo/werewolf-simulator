package ww.Roles

import spock.lang.Specification
import ww.States.NightState
import ww.Parameters
import ww.Actors.Player
import ww.Role
import ww.RoleSet
import ww.Actors.Team
import ww.TeamType

class SeerSpec extends Specification {
    List<? extends Player> players
    Map<TeamType, ? extends Team> teams
    Parameters parameters
    def setup() {
        players = []
        teams = [:]
        parameters = new Parameters()
    }

    def "NightAction"() {
        setup:
        parameters.seerClearPattern = Parameters.SeerClearPattern.RANDOM
        RoleSet roleSet = new RoleSet('Seer NightAction Test', [(Role.SEER):1, (Role.WEREWOLF):1])
        roleSet.setupPlayersAndTeams(parameters, players, teams)
        Seer seer = (Seer) players.find{it instanceof Seer}
        NightState nightState = new NightState(5, parameters, players, teams)
        when:
        seer.nightAction(nightState)
        then:
        players[1].identityKnownBy.contains(seer)

    }

    def "OnDeath"() {
        setup:
        Parameters parameters = new Parameters()
        List<Player> players = []
        RoleSet roleSet = new RoleSet('Seer OnDeath Test', [(Role.SEER):1, (Role.APPRENTICE_SEER):1])
        roleSet.setupPlayersAndTeams(parameters, players, teams)
        Seer seer = (Seer) players.find{it instanceof Seer}
        ApprenticeSeer appSeer = (ApprenticeSeer) players.find{it instanceof ApprenticeSeer}
        NightState nightState = new NightState(5, parameters, players, teams)
        when:
        seer.onDeath(nightState)
        then:
        appSeer.active
    }
}
