package org.benchmark;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class HashingBenchmark {

    private final static MessageDigest digestMD5;
    private final static MessageDigest digestSHA1;
    private final static MessageDigest digestSHA256;
    private final static MessageDigest digestSHA512;

    static {
        try {
            digestMD5 = MessageDigest.getInstance("MD5");
            digestSHA1 = MessageDigest.getInstance("SHA-1");
            digestSHA256 = MessageDigest.getInstance("SHA-256");
            digestSHA512 = MessageDigest.getInstance("SHA-512");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final static HashFunction digestMurmur3_128 = Hashing.murmur3_128();
    private final static HashFunction digestMurmur3_32 = Hashing.murmur3_32();
    private final static HashFunction digestsipHash24 = Hashing.sipHash24();
    private final static HashFunction digestCRC32 = Hashing.crc32();
    private final static HashFunction digestCRC32C = Hashing.crc32c();


    private List<byte[]> data = new ArrayList<>();

    @Setup
    public void setup() {
        try (Scanner scanner = new Scanner(new File(dataFile))) {
            while (scanner.hasNext()) {
                data.add(scanner.next().getBytes());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Param({})
    public String dataFile;


    @Benchmark
    public void MD5(Blackhole blackHole) {
        doHash(digestMD5, blackHole);
    }

    @Benchmark
    public void SHA1(Blackhole blackHole) {
        doHash(digestSHA1, blackHole);
    }

    @Benchmark
    public void SHA256(Blackhole blackHole) {
        doHash(digestSHA256, blackHole);
    }

    @Benchmark
    public void SHA512(Blackhole blackHole) {
        doHash(digestSHA512, blackHole);
    }

    @Benchmark
    public void Murmur3_128(Blackhole blackHole) {
        doHash(digestMurmur3_128, blackHole);
    }

    @Benchmark
    public void Murmur3_32(Blackhole blackHole) {
        doHash(digestMurmur3_32, blackHole);
    }

    @Benchmark
    public void sipHash24(Blackhole blackHole) {
        doHash(digestsipHash24, blackHole);
    }

    @Benchmark
    public void crc32(Blackhole blackHole) {
        doHash(digestCRC32, blackHole);
    }

    @Benchmark
    public void crc32c(Blackhole blackHole) {
        doHash(digestCRC32C, blackHole);
    }

    private final void doHash(HashFunction hasher, Blackhole blackHole) {
        for (byte[] bytes : data) {
            blackHole.consume(hasher.hashBytes(bytes).asBytes());
        }
    }

    private final void doHash(MessageDigest hasher, Blackhole blackHole) {
        for (byte[] bytes : data) {
            blackHole.consume(hasher.digest(bytes));
        }
    }
}
