/*
 * loc4j - a source code line counting ant task
 * Copyright 2016-2019 MeBigFatGuy.com
 * Copyright 2016-2019 Dave Brosius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package com.mebigfatguy.loc4j;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LocMethodVisitor extends MethodVisitor {

    private Counts counts;
    private Set<Integer> lines;

    public LocMethodVisitor() {
        super(Opcodes.ASM5);
        lines = new HashSet<>();
    }

    public void setCounts(Counts cnts) {
        counts = cnts;
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        lines.add(Integer.valueOf(line));
    }

    @Override
    public void visitEnd() {
        counts.addLineCounts(lines.size());
        lines.clear();
    }
}
