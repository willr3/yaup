This document summarizes all the steps needed to release next version of yaup.

It is assumed that you have all the privileges on Sonatype, GitHub push rights and set up GPG keys.

Start with building and releasing artefacts:

```
$ mvn release:prepare -Prelease
What is the release version for "yaup"? ... <- Use semantic version (X.Y.Z), default guessed by Maven works.
...
What is SCM release tag or label for "yaup"? <- Use tag 'yaup-X.Y.Z'. N.B. a tag opf `X.Y.Z` appears to perform a snapshot release
What is the new development version for "yaup"? ... <- Use semantic version (X.Y.Z) with -SNAPSHOT suffix
...
$ mvn release:perform -Prelease
```

This creates the tag and pushes everything in the Hyperfoil/yaup repo.
