# nextVersion-maven-plugin

A plugin to calculate next release and next snapshot versions, as to be used by
[maven-release-plugin](https://maven.apache.org/maven-release/maven-release-plugin/).

This plugin requires a project, so you must run it in a project.

The way to get your values ?
```shell
$ mvn top.marchand.maven.plugins:nextVersion-maven-plugin:1.01.00:calculate -DreleaseType=MAJOR
```

Legal values for `releaseType` are
- `PATCH`
- `MINOR`
- `MAJOR`
- `NONE`

`NONE` is the default value, and if it is selected, no output
is produced.

Else, it produces an output as this :
```
[INFO] --- nextVersion:1.00.01:calculate (default-cli) @ nextVersion-maven-plugin ---
{"release":"1.00.00","nextSnapshot":"1.00.01-SNAPSHOT"}
[INFO] release: 1.00.00
[INFO] snapshot: 1.00.01-SNAPSHOT
```

You may use the JSON like this :
```shell
JSON=$(mvn top.marchand.maven.plugins:nextVersion-maven-plugin:1.01.00:calculate -DreleaseType=MAJOR | grep nextSnapshot)
NEXT_RELEASE=$(echo "$JSON" | jq -r '.release')
NEXT_SNAPSHOT=$(echo "$JSON" | jq -r '.nextSnapshot')
```

It also define project properties `releaseVersion` and `developmentVersion` that can
be directly used by `maven-release-plugin`. So you can chain both plugins when you want do a release :

```shell
$ mvn -B -DreleaseType=MINOR top.marchand.maven.plugins:nextVersion-maven-plugin:1.01.00:calculate release:prepare
```
