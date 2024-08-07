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

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class Simulator {

    public static void main(String[] args) {
        def cli = new CliBuilder(usage: 'Simulator.groovy -[irfptvh]')
        cli.with {
            i longOpt: 'iteration', 'Number of Iterations', args: 1, required: false, type: Integer
            r longOpt: 'roleset', 'Predefined Role Set', args: 1, required: false, type: String
            f longOpt: 'rolesetfile', 'Role Set File', args: 1, required: false, type: String
            p longOpt: 'properties', 'Properties File', args: 1, required: false, type: String
            v longOpt: 'verbose', 'Enumerate each action', args: 0, required: false, type: Boolean
            t longOpt: 'threads', 'Number of threads', args: 1, required: false, type: Integer
            h longOpt: 'help', 'Usage', required: false
        }
        def options = cli.parse(args)
        if (options.h) {
            cli.usage()
            return
        }
        Integer iterations = 1
        if (options.i) {
            iterations = options.i.trim().toInteger()
        }

        RoleSet.Predefined predefinedRoleSet = RoleSet.Predefined.BASIC_7
        if (options.r) {
            predefinedRoleSet = RoleSet.Predefined.valueOf(options.r.trim().toUpperCase())
        }
        RoleSet roleSet = new RoleSet(predefinedRoleSet)

        Boolean verbose = false
        if (options.v) {
            verbose = true
        }

        if (options.f) {
            throw new Exception('Role set files are not yet supported')
        }

        Properties properties = new Properties()
        if (options.p) {
            throw new Exception('Property files are not yet supported')
        }

        Integer threads = 1
        if (options.t) {
            threads = options.t.trim().toInteger()
        }

        ExecutorService executor = Executors.newFixedThreadPool(threads)
        try {
            List<Callable<Game>> games = []
            iterations.times { idx ->
                Parameters parameters = new Parameters()
                if (verbose) {
                    parameters.verbose = true
                }
                Game game = new Game(parameters, roleSet)
                games.add(game)
            }
            List<Future<Game>> finalGames = executor.invokeAll(games)

            StatisticCollector stats = new StatisticCollector(finalGames)

            System.out.println(roleSet.name)
            System.out.println('------------------------')
            stats.print(iterations)
            System.out.println('------------------------')
        } finally {
            executor.shutdown()
        }
    }
}
