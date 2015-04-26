# hash-benchmarks

Benchmark for hashing "words" using the algorithms available with the JDK plus some that are part of [Guava](https://code.google.com/p/guava-libraries/wiki/HashingExplained):

* MD5 (JDK)
* SHA-1 (JDK)
* SHA-256 (JDK)
* SHA-512 (JDK)
* Murmur3 128 (Guava)
* Murmur3 32 (Guava) 
* CRC-32 (Guava)
* CRC-32C (Guava)
* SipHash (Guava)

The "words" are part of a corpus that is dynamically generated during the benchmark, the command line parameters set the size of the corpus and the length of the "words" it contains. The code uses [JMH](http://http://openjdk.java.net/projects/code-tools/jmh/), and is a Maven project.  

## How to run
```
> mvn clean install
> java -jar target/benchmarks.jar HashingBenchmark -wi 10 -i 10  -f 1 -p corpusSize=500000 -p stringLength=12
> java -jar target/benchmarks.jar HashingJavaStringBenchmark -wi 10 -i 10  -f 1 -p corpusSize=500000 -p stringLength=12
```
## Results
The results here refer to corpus created with ```-p corpusSize=500000 -p stringLength=12```i.e 500000 words of length 12, in the following JMH output, each result (*Score* in jmh-speak) is the average time in milliseconds it took to process **all of the words in the corpus**. 

```
Benchmark              (corpusSize)  (hasherName)  (stringLength)  Mode  Cnt    Score   Error  Units
HashingBenchmark.hash        500000           MD5              12  avgt   10  108.614 ± 0.851  ms/op
HashingBenchmark.hash        500000         SHA-1              12  avgt   10  147.499 ± 3.498  ms/op
HashingBenchmark.hash        500000       SHA-256              12  avgt   10  186.499 ± 0.709  ms/op
HashingBenchmark.hash        500000       SHA-512              12  avgt   10  287.876 ± 3.372  ms/op
HashingBenchmark.hash        500000   MURMUR3_128              12  avgt   10   70.198 ± 0.988  ms/op
HashingBenchmark.hash        500000    MURMUR3_32              12  avgt   10   71.443 ± 1.115  ms/op
HashingBenchmark.hash        500000    SIP_HASH24              12  avgt   10   78.706 ± 0.609  ms/op
HashingBenchmark.hash        500000         CRC32              12  avgt   10   63.512 ± 1.642  ms/op
HashingBenchmark.hash        500000        CRC32C              12  avgt   10   43.908 ± 0.743  ms/op

HashingJavaStringBenchmark.javaStringHash        500000        12  avgt   10   20.907 ± 6.922  ms/op
```
## Collisions
Collisions do occur in some of the cases, running: ```mvn exec:java -Dexec.mainClass="org.benchmark.CollisionDetector" -Dexec.args="500000 12" -Dexec.classpathScope=runtime``` results in 
```
Generated corpus of size 500000 with strings of length 12
# Collisions for SHA-512 :      0
# Collisions for SHA-256 :      0
# Collisions for CRC32C :       43
# Collisions for MURMUR3_128 :  0
# Collisions for MD5 :  0
# Collisions for CRC32 :        31
# Collisions for MURMUR3_32 :   21
# Collisions for SIP_HASH24 :   0
# Collisions for SHA-1 :        0
# Collisions for String.hashCode :      19
```