

package jedu.debugger.core;

public class DebuggerException extends Exception 
{
  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public DebuggerException(String message)
  {
    super(message);
  }
  
  public DebuggerException(Throwable throwable)
  {
    super(throwable);
  }
  
  public DebuggerException(String message, Throwable throwable)
  {
    super(message, throwable);
  }
}