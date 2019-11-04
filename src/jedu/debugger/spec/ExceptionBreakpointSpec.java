/*
A plugin for jEdit which implements java debugger functionality.
Copyright (C) 2003  Krishna Prakash Duggaraju

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

package jedu.debugger.spec;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.request.EventRequestManager;

public class ExceptionBreakpointSpec extends BreakpointSpec
{

  boolean caught = true;
  boolean uncaught = true;
  
  public ExceptionBreakpointSpec(String name, boolean caught, boolean uncaught)
  {
    super(name);
    this.caught = caught;
    this.uncaught = uncaught;
  }

  public boolean matches(ReferenceType rt)
  {
    return rt.name().equals(klass);
  }

  public void createRequest(ReferenceType rt) throws Exception
  {
    EventRequestManager evmgr = rt.virtualMachine().eventRequestManager();
    request = evmgr.createExceptionRequest(rt, caught, uncaught);
  }

  public boolean equals(Object obj)
  {
    if (obj instanceof ExceptionBreakpointSpec)
    {
      ExceptionBreakpointSpec ebp = (ExceptionBreakpointSpec) obj;
      return ( klass.equals(ebp.klass) );
    }
    return false;
  }

  public String toString()
  {
    return "Breakpoint in exception " + klass;
  }
}
