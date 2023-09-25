Feature: Calculate version numbers

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
      | input              | expected  |
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
      | input              | expected  |
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
      | input              | expected  |
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
