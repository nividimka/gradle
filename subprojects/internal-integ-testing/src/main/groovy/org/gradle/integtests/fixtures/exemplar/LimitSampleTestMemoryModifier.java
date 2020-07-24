/*
 * Copyright 2020 the original author or authors.
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

package org.gradle.integtests.fixtures.exemplar;

import org.gradle.samples.model.Command;
import org.gradle.samples.model.Sample;
import org.gradle.samples.test.runner.SampleModifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Limit exemplar test JVM Xmx to avoid oom killer.
 */
public class LimitSampleTestMemoryModifier implements SampleModifier {
    @Override
    public Sample modify(Sample sample) {
        List<Command> commands = sample.getCommands();
        List<Command> modifiedCommands = new ArrayList<Command>();
        for (Command command : commands) {
            if ("gradle".equals(command.getExecutable())) {
                List<String> args = new ArrayList<String>(command.getArgs());
                args.add("-Dorg.gradle.jvmargs=-Xmx1g");
                modifiedCommands.add(command.toBuilder().setArgs(args).build());
            } else {
                modifiedCommands.add(command);
            }
        }
        return new Sample(sample.getId(), sample.getProjectDir(), modifiedCommands);
    }
}
