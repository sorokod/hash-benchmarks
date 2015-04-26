package org.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class HashingJavaStringBenchmark {

    private Set<String> corpus = null;

    @Param({})
    public int corpusSize;

    @Param({})
    public int stringLength;

    @org.openjdk.jmh.annotations.Setup(Level.Iteration)
    public void setup() {
        corpus = BenchmarkSetup.generateCorpus(corpusSize, stringLength);
    }

    @Benchmark
    public void javaStringHash(Blackhole blackHole) {
        for (String s : corpus) {
            blackHole.consume(s.hashCode());
        }
    }
}
