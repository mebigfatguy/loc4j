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

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

public class LocAnnotationVisitor extends AnnotationVisitor {

    private Counts counts;

    public LocAnnotationVisitor() {
        super(Opcodes.ASM5);
    }

    public void setCounts(Counts cnts) {
        counts = cnts;
    }

    @Override
    public AnnotationVisitor visitArray(String name) {
        return super.visitArray(name);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }

}
