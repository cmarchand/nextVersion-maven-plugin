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

public class VersionCalculatorException extends Exception {
  public VersionCalculatorException() {
  }

  public VersionCalculatorException(String message) {
    super(message);
  }

  public VersionCalculatorException(String message, Throwable cause) {
    super(message, cause);
  }

  public VersionCalculatorException(Throwable cause) {
    super(cause);
  }

  public VersionCalculatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
