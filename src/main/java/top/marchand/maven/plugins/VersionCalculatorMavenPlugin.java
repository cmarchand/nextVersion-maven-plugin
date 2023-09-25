package top.marchand.maven.plugins;
/*
 * Copyright 2023 Christophe MARCHAND
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Arrays;
import java.util.function.Function;

@Mojo(name = "calculate")
public class VersionCalculatorMavenPlugin extends AbstractMojo {

  @Parameter(defaultValue = "${project.version}")
  private String currentVersion;

  /**
   * The type of release to calculate.
   * Legal values are PATCH, MINOR, MAJOR
   */
  @Parameter(defaultValue = "PATCH")
  private String releaseType;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    try {
      VersionCalculator versionCalculator = new VersionCalculator(currentVersion);
      ReleaseTypeStrategy wrapper = ReleaseTypeStrategy.of(releaseType);
      String release = wrapper.nextRelease(versionCalculator);
      String snapshot = wrapper.nextSnapshot(versionCalculator);
      String result = "{\"release\":\""+ release +"\",\"nextSnapshot\":\""+ snapshot +"\"}";
      System.out.println(result);
      getLog().info("release: "+release);
      getLog().info("snapshot: "+snapshot);
    } catch(VersionCalculatorException ex) {
      getLog().error("The version you try to release do not match SemVer specification : "+currentVersion);
      getLog().error("See https://semver.org/ and adapt your version");
      throw new MojoExecutionException(ex);
    }
  }

  private enum ReleaseTypeStrategy {
    PATCH(
        "PATCH",
        VersionCalculator::nextPatchRelease,
        VersionCalculator::nextPatchSnapshot),
    MINOR(
        "MINOR",
        VersionCalculator::nextMinorRelease,
        VersionCalculator::nextMinorSnapshot
    ),
    MAJOR(
        "MAJOR",
        VersionCalculator::nextMajorRelease,
        VersionCalculator::nextMajorSnapshot
    )
    ;

    private final String code;
    private final Function<VersionCalculator, String> nextReleaser;
    private final Function<VersionCalculator, String> nextSnapshoter;

    ReleaseTypeStrategy(String code, Function<VersionCalculator, String> nextReleaser, Function<VersionCalculator, String> nextSnapshoter) {
      this.code = code;
      this.nextReleaser = nextReleaser;
      this.nextSnapshoter = nextSnapshoter;
    }

    public static ReleaseTypeStrategy of(String releaseType) {
      return Arrays.stream(values())
          .filter(v -> v.code().equals(releaseType))
          .findFirst()
          .orElseThrow();
    }

    private String code() {
      return this.code;
    }

    public String nextRelease(VersionCalculator versionCalculator) {
      return nextReleaser.apply(versionCalculator);
    }
    public String nextSnapshot(VersionCalculator versionCalculator) {
      return nextSnapshoter.apply(versionCalculator);
    }
  }
}
