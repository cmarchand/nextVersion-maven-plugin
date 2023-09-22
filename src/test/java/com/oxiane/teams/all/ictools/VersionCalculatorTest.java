package com.oxiane.teams.all.ictools;

import org.assertj.core.api.Assertions;
import org.junit.Test;

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
