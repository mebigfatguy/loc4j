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

/**
 * a simple beans for holding counts of lines, methods and classes
 */
public class Counts {

    private long lineCounts;
    private long methodCounts;
    private long classCounts;

    public long getLineCounts() {
        return lineCounts;
    }

    public void addLineCounts(long lc) {
        lineCounts += lc;
    }

    public long getMethodCounts() {
        return methodCounts;
    }

    public void addMethodCounts(long mc) {
        methodCounts += mc;
    }

    public long getClassCounts() {
        return classCounts;
    }

    public void addClassCounts(long cc) {
        classCounts += cc;
    }

    @Override
    public String toString() {
        return "Lines: " + lineCounts + " Methods: " + methodCounts + " Classes: " + classCounts;
    }
}
