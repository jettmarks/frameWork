/**
 * Created Jun 9, 2009
 */
package com.jettmarks.base;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Abstract class to be extended by application developer defining how arguments
 * are to be parsed and what happens after the args are parsed.
 * 
 * Implementers will provide definitions for {@link #parseArgs(String[])} and 
 * {@link #execute()}.
 */
public abstract class ArgParsingEntryPoint
{
  
  /** The command line. */
  protected static CommandLine commandLine = null;

  /** The options. */
  protected static Options options;

  /**
   * Parses the args using the commons-cli library.
   * 
   * @param args as supplied to the main(String args[]) method.
   * 
   * @return int representing whatever the application developer chooses.  A
   * reasonable choice is to return the number of arguments found.
   * 
   * @throws ParseException the parse exception
   */
  public abstract int parseArgs(String[] args) throws ParseException;

  /**
   * Execute the main thread of the application as defined by the app developer.
   * 
   * @return int representing whatever the application developer choses.  This 
   * signature was chosen to allow testing of mock execute methods.
   */
  public abstract int execute();

  /**
   * Uses the commons-cli library to generate usage information.
   * 
   * @return String representing the options defined by 
   *   {@link #parseArgs(String[])}.
   */
  protected String printUsage()
  {
    if (options == null)
    {
      return null;
    }
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp(this.getClass().getName(), options);
    return formatter.toString();
  }

}
