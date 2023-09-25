package top.marchand.maven.plugins;

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
