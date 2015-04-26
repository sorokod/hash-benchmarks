package org.benchmark;

import com.google.common.hash.Hashing;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.apache.commons.lang3.RandomStringUtils.randomAscii;


public class BenchmarkSetup {

    public static Map<String, Object> HASHERS = new HashMap<>();

    static {
        HASHERS.put("MURMUR3_128", Hashing.murmur3_128());
        HASHERS.put("MURMUR3_32", Hashing.murmur3_32());
        HASHERS.put("SIP_HASH24", Hashing.sipHash24());
        HASHERS.put("CRC32", Hashing.crc32());
        HASHERS.put("CRC32C", Hashing.crc32c());

        try {
            HASHERS.put("MD5", MessageDigest.getInstance("MD5"));
            HASHERS.put("SHA-1", MessageDigest.getInstance("SHA-1"));
            HASHERS.put("SHA-256", MessageDigest.getInstance("SHA-256"));
            HASHERS.put("SHA-512", MessageDigest.getInstance("SHA-512"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param size
     * @param stringLength
     * @return A set of ASCII strings of size size where each member string is of length stringLength
     */
    public static Set<String> generateCorpus(int size, int stringLength) {
        Set<String> corpus = new HashSet<>(size);
        while (corpus.size() < size) {
            corpus.add(randomAscii(stringLength));
        }
        System.out.printf("Generated corpus of size %d with strings of length %d\n", size, stringLength);
        return corpus;
    }
}
