[![Build Status](https://travis-ci.org/JoachimVandersmissen/ioutil.svg?branch=master)](https://travis-ci.org/JoachimVandersmissen/ioutil)
[![Jitpack](https://jitpack.io/v/JoachimVandersmissen/ioutil.svg)](https://jitpack.io/#JoachimVandersmissen/ioutil)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
# ioutil
This library provides various input and output utilities:
* 8, 16, 32, 64 bit integers, signed and unsigned, big and little endian
* 32 and 64 bit floating points
* Arbitrary byte arrays
* UTF-8, CESU-8 and Modified UTF-8 strings
* Little Endian Base 128, signed and unsigned

Suggestions are always welcome!

## Installation
### Gradle
Step 1. Add the JitPack repository to your build file  
Add it in your root build.gradle at the end of repositories:  
```
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```  
Step 2. Add the dependency  
```
    dependencies {
            compile 'com.github.JoachimVandersmissen:ioutil:[version]'
    }
```  

### Maven
Step 1. Add the JitPack repository to your build file  
```
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
```  
Step 2. Add the dependency  
```
    <dependency>
        <groupId>com.github.JoachimVandersmissen</groupId>
        <artifactId>ioutil</artifactId>
        <version>[version]</version>
    </dependency>
```  

## Documentation
The latest javadoc documentation can be found here: https://jitpack.io/com/github/JoachimVandersmissen/ioutil/master-SNAPSHOT/javadoc/  
Documentation for a specific version can be found at https://jitpack.io/com/github/JoachimVandersmissen/ioutil/[version]/javadoc/

## Usage
Reading from a big or little endian input:
```
InputStream inputStream = ...
Input bigEndian = new BigEndianInput(inputStream);
Input littleEndian = new LittleEndianInput(inputStream);
```

Writing to a big or little endian output:
```
OutputStream outputStream = ...
Output bigEndian = new BigEndianOutput(outputStream);
Output littleEndian = new LittleEndianOutput(outputStream);
```

Encoding or decoding UTF-8, CESU-8 and Modified UTF-8 strings:
```
String toEncode = ...
byte[] toDecode = ...
byte[] encodedUtf8 = StringEncoding.UTF_8.encode(toEncode);
String decodedUtf8 = StringEncoding.UTF_8.decode(toDecode);
byte[] encodedCesu8 = StringEncoding.CESU_8.encode(toEncode);
String decodedCesu8 = StringEncoding.CESU_8.decode(toDecode);
byte[] encodedMutf8 = StringEncoding.MUTF_8.encode(toEncode);
String decodedMutf8 = StringEncoding.MUTF_8.decode(toDecode);
```

## Acknowledgements

![YourKit](https://www.yourkit.com/images/yklogo.png)

YourKit supports open source projects with its full-featured Java Profiler.  
YourKit, LLC is the creator of [YourKit Java Profiler](https://www.yourkit.com/java/profiler/)
and [YourKit .NET Profiler](https://www.yourkit.com/.net/profiler/), innovative and intelligent tools for profiling Java and .NET applications.
