/**
 * Created Jun 9, 2009
 */
package com.jettmarks.base;

import junit.framework.TestCase;

import org.apache.commons.cli.ParseException;

import com.jettmarks.clsld.ClassLoadUtil;

/**
 * @author Jett Marks
 * 
 */
public class EntryPointFactoryTest extends TestCase
{

  /**
   * Test method for
   * {@link com.jettmarks.base.mock.EntryPointFactory#getInstance()}.
   */
  public void testGetInstance()
  {
    ArgParsingEntryPoint apep = null;
    try
    {
      apep = ClassLoadUtil.loadEntryPoint();
      assertEquals(0, apep.parseArgs(null));
    } catch (ParseException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertEquals(0, apep.execute());
  }

}
