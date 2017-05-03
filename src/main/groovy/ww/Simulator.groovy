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


class Simulator {

    public static void main(String[] args){
        Integer iterations = args[0].toInteger()
        Properties properties = new Properties()
        if (args.size() > 1) {
            File propertiesFile = new File(args[1])
            propertiesFile.withInputStream {
                properties.load(it)
            }

        }
        iterations.times {
            Parameters parameters = new Parameters()
            RoleSet roleSet = new RoleSet(RoleSet.Predefined.BASIC7)
            Game game = new Game(parameters, roleSet)
            game.play()
        }
    }
}