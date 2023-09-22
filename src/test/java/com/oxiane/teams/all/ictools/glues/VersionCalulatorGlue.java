package com.oxiane.teams.all.ictools.glues;

import com.oxiane.teams.all.ictools.VersionCalculator;
import com.oxiane.teams.all.ictools.VersionCalculatorException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

public class VersionCalulatorGlue {
  private String inputVersion;
  private VersionCalculator versionCalculator = null;
  private Exception exceptionThrown;
  private String actual;

  @Given("current version is {string}")
  public void current_version_is(String inputVersion) {
    this.inputVersion = inputVersion;
    version_calculator_is_instanciated();
  }
  @When("next patch release is call")
  public void next_patch_release_is_call() {
    actual = versionCalculator.nextPatchRelease();
  }
  @When("next minor release is call")
  public void next_minor_release_is_call() {
    actual = versionCalculator.nextMinorRelease();
  }
  @When("next major release is call")
  public void next_major_release_is_call() {
    actual = versionCalculator.nextMajorRelease();
  }
  @When("next patch snapshot is call")
  public void next_patch_snapshot_is_call() {
    try {
      actual = versionCalculator.nextPatchSnapshot();
    } catch (RuntimeException e) {
      exceptionThrown = (VersionCalculatorException)e.getCause();
    }
  }
  @When("next minor snapshot is call")
  public void next_minor_snapshot_is_call() {
    try {
      actual = versionCalculator.nextMinorSnapshot();
    } catch (RuntimeException e) {
      exceptionThrown = (VersionCalculatorException)e.getCause();
    }
  }
  @When("next major snapshot is call")
  public void next_major_snapshot_is_call() {
    try {
      actual = versionCalculator.nextMajorSnapshot();
    } catch (RuntimeException e) {
      exceptionThrown = (VersionCalculatorException)e.getCause();
    }
  }
  @Then("{string} is expected")
  public void is_expected(String expected) {
    Assertions.assertThat(actual).isEqualTo(expected);
  }
  @When("VersionCalculator is instanciated")
  public void version_calculator_is_instanciated() {
    try {
      versionCalculator = new VersionCalculator(inputVersion);
    } catch (VersionCalculatorException e) {
      exceptionThrown = e;
    }
  }
  @Then("no exception is thrown")
  public void no_exception_is_thrown() {
    Assertions.assertThat(exceptionThrown).isNull();
  }
  @Then("suffix is {word}")
  public void suffix_is(String suffix) {
    String expected = suffix==null ? "" : suffix;
    Assertions.assertThat(versionCalculator.suffix()).isEqualTo(expected);
  }
  @Then("suffix is ")
  public void suffix_is_empty() {
    suffix_is("");
  }
  @Then("prefix is {word}")
  public void prefix_is(String prefix) {
    Assertions.assertThat(versionCalculator.prefix()).isEqualTo(prefix);
  }
  @Then("prefix is ")
  public void prefix_is_empty() {
    prefix_is("");
  }
  @Then("snapshot is {word}")
  public void snapshot_is(String expected) {
    boolean snapshot = Boolean.parseBoolean(expected);
    Assertions.assertThat(versionCalculator.snapshot()).isEqualTo(snapshot);
  }
}
