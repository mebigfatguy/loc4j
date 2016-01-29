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
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LocMethodVisitor extends MethodVisitor {

    private static final LocAnnotationVisitor av = new LocAnnotationVisitor();
    private Counts counts;

    public LocMethodVisitor() {
        super(Opcodes.ASM5);
    }

    public void setCounts(Counts cnts) {
        counts = cnts;
        av.setCounts(cnts);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if ("".equals(desc)) {
            return av;
        }

        return super.visitAnnotation(desc, visible);
    }

}
