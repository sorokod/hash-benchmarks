package org.benchmark;

import com.google.common.hash.HashFunction;
import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.benchmark.BenchmarkSetup.HASHERS;
import static org.benchmark.BenchmarkSetup.generateCorpus;

public class CollisionDetector {

    private final Set<String> corpus;

    public CollisionDetector(int corpusSize, int stringLength) {
        corpus = generateCorpus(corpusSize, stringLength);
    }

    public void detect() {
        for (String hasherName : HASHERS.keySet()) {

            Object hasher = HASHERS.get(hasherName);
            int diff = 0;

            if (hasher instanceof MessageDigest) {
                diff = doDetect((MessageDigest) hasher);
            } else {
                diff = doDetect((HashFunction) hasher);
            }
            System.out.println("# Collisions for " + hasherName + " :\t" + diff);
        }
    }

    private int doDetect(MessageDigest hasher) {
        Set<String> target = new HashSet<>();
        for (String str : corpus) {
            byte[] bytes = hasher.digest(str.getBytes(US_ASCII));
            target.add(Base64.encodeBase64String(bytes));
        }
        return corpus.size() - target.size();
    }

    private int doDetect(HashFunction hasher) {
        Set<String> target = new HashSet<>();
        for (String str : corpus) {
            byte[] bytes = hasher.hashString(str, US_ASCII).asBytes();
            target.add(Base64.encodeBase64String(bytes));
        }
        return corpus.size() - target.size();
    }

    public int detectStringHashcode() {
        Set<String> target = new HashSet<>();
        for (String str : corpus) {
            target.add(String.valueOf(str.hashCode()));
        }
        return corpus.size() - target.size();
    }

    public static void main(String[] args) {
        CollisionDetector detector = new CollisionDetector(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        detector.detect();
        System.out.println("# Collisions for String.hashCode :\t" + detector.detectStringHashcode());
    }
}
