/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.model

import org.gradle.integtests.fixtures.AbstractIntegrationSpec
import org.gradle.integtests.fixtures.Sample
import org.gradle.integtests.fixtures.UnsupportedWithInstantExecution
import org.gradle.integtests.fixtures.UsesSample
import org.junit.Rule

@UnsupportedWithInstantExecution(because = "software model")
class ModelRuleSamplesIntegrationTest extends AbstractIntegrationSpec {

    @Rule Sample sample = new Sample(testDirectoryProvider)

    @UsesSample("modelRules/modelDsl")
    def "dsl creation example works"() {
        when:
        inDirectory(sample.dir.file('groovy'))

        then:
        succeeds "hello"
        output.contains("Hello John Smith!")

        when:
        inDirectory(sample.dir.file('groovy'))

        then:
        succeeds "listPeople"
        output.contains("configuring Person 'people.john'")
        output.contains("configuring Person 'people.barry'")
        output.contains("Hello John Smith!")
        output.contains("Hello Barry Barry!")
    }
}
