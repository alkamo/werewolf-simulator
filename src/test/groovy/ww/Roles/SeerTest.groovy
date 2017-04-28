package ww.Roles

import spock.lang.Specification
import ww.Game
import ww.Parameters
import ww.Player


class SeerTest extends Specification {
    def "NightAction"() {
        setup:
        Parameters parameters = new Parameters()
        parameters.seerClearPattern = Parameters.SeerClearPattern.RANDOM
        List<Player> players = []
        players.add(new Seer(parameters, players))
        players.add(new Werewolf(parameters, players))
        Seer seer = (Seer) players[0]
        when:
        seer.nightAction(blarg)
        then:
        players[1].identityKnownBy.contains(seer)

    }

    def "OnDeath"() {
        setup:
        Parameters parameters = new Parameters()
        List<Player> players = []
        players.add(new Seer(parameters, players))
        players.add(new ApprenticeSeer(parameters, players))
        Seer seer = (Seer) players[0]
        ApprenticeSeer appSeer = (ApprenticeSeer) players[1]
        when:
        seer.onDeath(Game.TurnType.DAY)
        then:
        appSeer.active
    }
}
