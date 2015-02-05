/**
 * Created Jun 9, 2009
 */
package com.jettmarks.base;

import junit.framework.TestCase;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Parser;
import org.apache.commons.cli.PosixParser;

import com.jettmarks.clsld.ClassLoadUtil;

/**
 * @author Jett Marks
 * 
 */
public class ArgParsingEntryPointTest extends TestCase
{

  /**
   * Test method for
   * {@link com.jettmarks.base.ArgParsingEntryPoint#printUsage()}.
   */
  public void testPrintUsage()
  {
    ArgParsingEntryPoint apep = null;

    apep = ClassLoadUtil.loadEntryPoint();
    String usageResult = apep.printUsage();
    assertNull(usageResult);

    String[] args =
    { "m", "v" };
    try
    {
      apep.parseArgs(args);
    } catch (ParseException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    usageResult = apep.printUsage();
    assertNull(usageResult);

    ArgParsingEntryPoint apepWithOptions = new ArgParsingEntryPoint()
    {

      @Override
      public int execute()
      {
        // TODO Auto-generated method stub
        return 0;
      }

      @Override
      public int parseArgs(String[] args) throws ParseException
      {
        Parser parser = new PosixParser();
        options = new Options();
        options.addOption("h", false, "print this message");
        commandLine = parser.parse(options, args);
        return commandLine.getArgs().length;
      }
    };

    int argCount = 0;
    try
    {
      argCount = apepWithOptions.parseArgs(args);
    } catch (ParseException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertEquals(2, argCount);
    usageResult = apepWithOptions.printUsage();
    assertNotNull(usageResult);
  }

}
