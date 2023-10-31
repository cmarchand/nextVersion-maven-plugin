Feature: Calculate version numbers

  LICENSE
  Copyright 2023 Christophe MARCHAND

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.

  FEATURE
  This feature allows, given a current project version, to calculate next
  release and next snapshot versions, as to be used by release-maven-plugin.

  Next release can be a MAJOR release, a MINOR release or a PATCH release, as
  defined in SemVer.

  It applies also on versions that contains a build number.

  Scenario Template: Next Patch Release
    Given current version is <input>
    When next patch release is call
    Then <expected> is expected
    Examples:
      | input              | expected  |
      | "1.00.00"          | "1.00.01" |
      | "1.00.00-SNAPSHOT" | "1.00.00" |
      | "1.00.01-SNAPSHOT" | "1.00.01" |
      | "1.00.01"          | "1.00.02" |
      | "1.00"             | "1.00.01" |
      | "1"                | "1.00.01" |

  Scenario Template: Next Patch Snapshot
    Given current version is <input>
    When next patch snapshot is call
    Then <expected> is expected
    Examples:
      | input              | expected           |
      | "1.00.00"          | "1.00.02-SNAPSHOT" |
      | "1.00.00-SNAPSHOT" | "1.00.01-SNAPSHOT" |
      | "1.00.01-SNAPSHOT" | "1.00.02-SNAPSHOT" |
      | "1.00.01"          | "1.00.03-SNAPSHOT" |
      | "1.00"             | "1.00.02-SNAPSHOT" |
      | "1"                | "1.00.02-SNAPSHOT" |

  Scenario Template: Next Minor Release
    Given current version is <input>
    When next minor release is call
    Then <expected> is expected
    Examples:
      | input              | expected  |
      | "2.08.03-SNAPSHOT" | "2.09.00" |
      | "1.00.00"          | "1.01.00" |
      | "1.00.00-SNAPSHOT" | "1.00.00" |
      | "1.00.01-SNAPSHOT" | "1.01.00" |
      | "1.00.01"          | "1.01.00" |
      | "1.00"             | "1.01.00" |
      | "1"                | "1.01.00" |

  Scenario Template: Next Minor Snapshot
    Given current version is <input>
    When next minor snapshot is call
    Then <expected> is expected
    Examples:
      | input              | expected           |
      | "1.00.00"          | "1.01.01-SNAPSHOT" |
      | "1.00.00-SNAPSHOT" | "1.00.01-SNAPSHOT" |
      | "1.00.01-SNAPSHOT" | "1.01.01-SNAPSHOT" |
      | "1.00.01"          | "1.01.01-SNAPSHOT" |
      | "1.00"             | "1.01.01-SNAPSHOT" |
      | "1"                | "1.01.01-SNAPSHOT" |

  Scenario Template: Next Major Release
    Given current version is <input>
    When next major release is call
    Then <expected> is expected
    Examples:
      | input              | expected  |
      | "1.00.00"          | "2.00.00" |
      | "1.00.00-SNAPSHOT" | "1.00.00" |
      | "1.00.01-SNAPSHOT" | "2.00.00" |
      | "1.00.01"          | "2.00.00" |
      | "1.00"             | "2.00.00" |
      | "1"                | "2.00.00" |

  Scenario Template: Next Major Snapshot
    Given current version is <input>
    When next major snapshot is call
    Then <expected> is expected
    Examples:
      | input              | expected           |
      | "1.00.00"          | "2.00.01-SNAPSHOT" |
      | "1.00.00-SNAPSHOT" | "1.00.01-SNAPSHOT" |
      | "1.00.01-SNAPSHOT" | "2.00.01-SNAPSHOT" |
      | "1.00.01"          | "2.00.01-SNAPSHOT" |
      | "1.00"             | "2.00.01-SNAPSHOT" |
      | "1"                | "2.00.01-SNAPSHOT" |

  Scenario Template: No exception thrown
    Given current version is <input>
    When VersionCalculator is instanciated
    Then no exception is thrown
    Then suffix is <suffix>
    Then prefix is <prefix>
    Then snapshot is <snapshot>
    Examples:
      | input                     | prefix  | suffix  | snapshot |
      | "1"                       |         |         | false    |
      | "1.00"                    |         |         | false    |
      | "1.00.00"                 |         |         | false    |
      | "1-SNAPSHOT"              |         |         | true     |
      | "1.00-SNAPSHOT"           |         |         | true     |
      | "1.00.00-SNAPSHOT"        |         |         | true     |
      | "prefix-1"                | prefix- |         | false    |
      | "prefix-1.00"             | prefix- |         | false    |
      | "prefix-1.00.00"          | prefix- |         | false    |
      | "prefix-1-SNAPSHOT"       | prefix- |         | true     |
      | "prefix-1.00-SNAPSHOT"    | prefix- |         | true     |
      | "prefix-1.00.00-SNAPSHOT" | prefix- |         | true     |
      | "1-suffix"                |         | -suffix | false    |
      | "1.00-suffix"             |         | -suffix | false    |
      | "1.00.00-suffix"          |         | -suffix | false    |
      | "1-suffix-SNAPSHOT"       |         | -suffix | true     |
      | "1.00-suffix-SNAPSHOT"    |         | -suffix | true     |
      | "1.00.00-suffix-SNAPSHOT" |         | -suffix | true     |
