# hash-benchmarks

Benchmark for digesting "words" using the hashing algorithms available with the JDK plus some that are part of [Guava](https://code.google.com/p/guava-libraries/wiki/HashingExplained):

* MD5 (JDK)
* SHA-1 (JDK)
* SHA-256 (JDK)
* SHA-512 (JDK)
* Murmur3 128 (Guava)
* Murmur3 32 (Guava) 
* CRC-32 (Guava)
* CRC-32C (Guava)
* SipHash (Guava)

The code uses [JMH](http://http://openjdk.java.net/projects/code-tools/jmh/), is available as a Maven project and contains a sample word file.  

## How to run
```
> mvn clean install
> java -jar target/benchmarks.jar HashingBenchmark -wi 7 -i 7  -f 1 -p dataFile=data\words.txt
> java -jar target/benchmarks.jar HashingJavaStringBenchmark -wi 7 -i 7  -f 1 -p dataFile=data\words.txt
```
## Results
The words.txt file contains 109583 distinct words, in the following JMH output, each result (*Score* in jmh-speak) is the average time in milliseconds it took to process **all of the words in the file**. 

```
Benchmark                         (dataFile)  Mode  Cnt   Score   Error  Units
HashingBenchmark.MD5          data\words.txt  avgt    7  19.254 ± 0.318  ms/op
HashingBenchmark.Murmur3_128  data\words.txt  avgt    7   7.682 ± 0.107  ms/op
HashingBenchmark.Murmur3_32   data\words.txt  avgt    7   7.709 ± 0.090  ms/op
HashingBenchmark.SHA1         data\words.txt  avgt    7  27.362 ± 0.138  ms/op
HashingBenchmark.SHA256       data\words.txt  avgt    7  36.453 ± 0.140  ms/op
HashingBenchmark.SHA512       data\words.txt  avgt    7  60.504 ± 0.202  ms/op
HashingBenchmark.crc32        data\words.txt  avgt    7   5.319 ± 0.073  ms/op
HashingBenchmark.crc32c       data\words.txt  avgt    7   2.893 ± 0.111  ms/op
HashingBenchmark.sipHash24    data\words.txt  avgt    7   8.734 ± 0.109  ms/op

HashingJavaStringBenchmark.javaStringHash  data\words.txt  avgt    7  7.346 ± 2.504  ms/op  (cached hash values are discarded)
```

On the provided corpus, CRC-32C generated one collision, the rest where collision free.