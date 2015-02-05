/*
 * Created on Jun 03, 2009
 */
package com.jettmarks.base;

import org.apache.commons.cli.ParseException;

import com.jettmarks.clsld.ClassLoadUtil;

/**
 * Defines a main() method that locates EntryPointFactory for creating an
 * EntryPoint that parses args.
 * 
 * @author Jett Marks
 */
public class ArgParsingMain
{
  /**
   * @param args
   */
  public static void main(String[] args)
  {
    ArgParsingEntryPoint apep = ClassLoadUtil.loadEntryPoint();

    try
    {
      apep.parseArgs(args);
    } catch (ParseException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    apep.execute();
  }

}
