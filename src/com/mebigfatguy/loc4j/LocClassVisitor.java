/*
 * loc4j - a source code line counting ant task
 * Copyright 2016 MeBigFatGuy.com
 * Copyright 2016 Dave Brosius
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

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LocClassVisitor extends ClassVisitor {

    private static final LocMethodVisitor mv = new LocMethodVisitor();

    private Counts counts;

    public LocClassVisitor(Counts cnts) {
        super(Opcodes.ASM5);
        counts = cnts;
        mv.setCounts(cnts);

        counts.addClassCounts(1);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        counts.addMethodCounts(1);
        return mv;
    }

}
