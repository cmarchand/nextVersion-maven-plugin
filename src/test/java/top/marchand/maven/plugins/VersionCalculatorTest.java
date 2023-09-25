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

import org.assertj.core.api.Assertions;
import org.junit.Test;

// Tests in this class are all moved to VersionCalculator.feature
// This class is kept here only to be able to run some code in debug mode
public class VersionCalculatorTest {

  @Test
  public void given_1_SNAPSHOT_snapshot_should_be_true() throws Exception {
    // given
    String inputVersion = "1-SNAPSHOT";
    // When
    VersionCalculator versionCalculator = new VersionCalculator(inputVersion);
    Assertions.assertThat(versionCalculator.snapshot()).isTrue();
  }
}
