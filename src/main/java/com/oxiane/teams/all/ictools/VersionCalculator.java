package com.oxiane.teams.all.ictools;

import java.text.NumberFormat;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionCalculator {
  private static final NumberFormat FORMATTER = NumberFormat.getIntegerInstance();
  public static final String SNAPSHOT = "-SNAPSHOT";

  private final String inputVersion;
  private String build;
  private String patch;
  private String minor;
  private String major;
  private String prefix;
  private String suffix;
  private boolean isSnapshot = false;

  public VersionCalculator(String inputVersion) throws VersionCalculatorException {
    this.inputVersion = inputVersion;
    extractVersionNumbers();
  }

  private void extractVersionNumbers() throws VersionCalculatorException {
    Matcher matcher = Pattern.compile("[0-9]+").matcher(this.inputVersion);
    int groupIndex = 0;
    while(matcher.find()) {
      switch(groupIndex) {
        case 0: major = matcher.group(); break;
        case 1: minor = matcher.group(); break;
        case 2: patch = matcher.group(); break;
        default: build = matcher.group(); break;
      }
      groupIndex++;
    }
    if(groupIndex==0) {
      throw new VersionCalculatorException("A version should at least contain one number");
    }
    matcher.find(0);
    prefix=inputVersion.substring(0,matcher.start());
    do {
      suffix = inputVersion.substring(matcher.end());
    }
    while(matcher.find());
    if(!suffix.isEmpty()) {
      if (suffix.endsWith(SNAPSHOT)) {
        isSnapshot = true;
        suffix = suffix.substring(0, suffix.length() - SNAPSHOT.length());
      }
    }
  }

  public String nextPatchRelease() {
    StringJoiner joiner = new StringJoiner(".");
    joiner.add(major);
    if(minor!=null) joiner.add(minor);
    else joiner.add("00");
    if(patch!=null) {
      if(isSnapshot)
        joiner.add(formatNumber(patch,2));
      else
        joiner.add(formatNumber(increment(patch),2));
    } else {
      joiner.add("01");
    }
    if(build!=null) joiner.add("00");
    return prefix+joiner.toString()+suffix;
  }

  private int increment(String number) {
    return Integer.parseInt(number) + 1;
  }

  private CharSequence formatNumber(int value, int size) {
    FORMATTER.setMinimumIntegerDigits(size);
    return FORMATTER.format(value);
  }
  private CharSequence formatNumber(String value, int size) {
    return formatNumber(Integer.parseInt(value), size);
  }

  public String suffix() {
    return suffix;
  }
  public String prefix() {
    return prefix;
  }
  public boolean snapshot() {
    return isSnapshot;
  }

  public String nextMinorRelease() {
    StringJoiner joiner = new StringJoiner(".");
    joiner.add(major);
    if(minor!=null) {
      if(snapshot()) {
        if("00".equals(patch)) {
          joiner.add(minor);
        } else {
          joiner.add(incrementAndFormatMinor());
        }
      } else {
        joiner.add(incrementAndFormatMinor());
      }
    } else joiner.add("01");
    joiner.add("00");
    if(build!=null) joiner.add("00");
    return prefix+joiner.toString()+suffix;
  }

  private CharSequence incrementAndFormatMinor() {
    return formatNumber(increment(minor), 2);
  }

  public String nextMajorRelease() {
    StringJoiner joiner = new StringJoiner(".");
    if(snapshot()) {
      if(minor.isEmpty() || "00".equals(minor)) {
        if(patch.isEmpty() || "00".equals(patch)) {
          joiner.add(major);
        } else {
          joiner.add(incrementAndFormatMajor());
        }
      } else {
        joiner.add(incrementAndFormatMajor());
      }
    } else {
      joiner.add(incrementAndFormatMajor());
    }
    joiner.add("00");
    joiner.add("00");
    if(build!=null) joiner.add("00");
    return prefix+joiner.toString()+suffix;
  }

  private CharSequence incrementAndFormatMajor() {
    return formatNumber(increment(major), 1);
  }

  public String nextPatchSnapshot() {
    try {
      return new VersionCalculator(nextPatchRelease()).nextPatchRelease()+SNAPSHOT;
    } catch (VersionCalculatorException e) {
      throw new RuntimeException(e);
    }
  }

  public String nextMinorSnapshot() {
    try {
      return new VersionCalculator(nextMinorRelease()).nextPatchRelease()+SNAPSHOT;
    } catch (VersionCalculatorException e) {
      throw new RuntimeException(e);
    }
  }
  public String nextMajorSnapshot() {
    try {
      return new VersionCalculator(nextMajorRelease()).nextPatchRelease()+SNAPSHOT;
    } catch (VersionCalculatorException e) {
      throw new RuntimeException(e);
    }
  }
}
