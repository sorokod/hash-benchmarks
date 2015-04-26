package org.benchmark;

import com.google.common.hash.HashFunction;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.security.MessageDigest;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.benchmark.BenchmarkSetup.HASHERS;
import static org.benchmark.BenchmarkSetup.generateCorpus;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class HashingBenchmark {

    private Set<String> corpus = null;

    @Param({})
    public int corpusSize;

    @Param({})
    public int stringLength;

    @Param({"MD5", "SHA-1", "SHA-256", "SHA-512", "MURMUR3_128", "MURMUR3_32", "SIP_HASH24", "CRC32", "CRC32C"})
    public String hasherName;

    @org.openjdk.jmh.annotations.Setup
    public void setup() {
        corpus = generateCorpus(corpusSize, stringLength);
    }

    @Benchmark
    public void hash(Blackhole blackHole) {
        Object hasher = HASHERS.get(hasherName);
        if (hasher instanceof MessageDigest) {
            doHash((MessageDigest) hasher, blackHole);
        } else {
            doHash((HashFunction) hasher, blackHole);
        }
    }

    private final void doHash(HashFunction hasher, Blackhole blackHole) {
        for (String str : corpus) {
            blackHole.consume(hasher.hashString(str, US_ASCII).asBytes());
        }
    }

    private final void doHash(MessageDigest hasher, Blackhole blackHole) {
        for (String str : corpus) {
            blackHole.consume(hasher.digest(str.getBytes(US_ASCII)));
        }
    }
}
