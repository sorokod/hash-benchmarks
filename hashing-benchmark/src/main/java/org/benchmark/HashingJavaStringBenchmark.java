package org.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class HashingJavaStringBenchmark {

    private List<String> data = new ArrayList<>();

    @Setup(Level.Iteration)
    public void setup() {
        try (Scanner scanner = new Scanner(new File(dataFile))) {
            while (scanner.hasNext()) {
                data.add(scanner.next());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Param({})
    public String dataFile;


    @Benchmark
    public void javaStringHash(Blackhole blackHole) {
        for (String s : data) {
            blackHole.consume(s.hashCode());
        }
    }
}
