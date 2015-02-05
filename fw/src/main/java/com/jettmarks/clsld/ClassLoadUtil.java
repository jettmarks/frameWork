/**
 * Created Jun 9, 2009
 */
package com.jettmarks.clsld;

import java.util.List;


import org.apache.log4j.Logger;

import com.jettmarks.base.ArgParsingEntryPoint;

/**
 * Returns an implmentation of ArgParsingEntryPoint created by the app developer
 * specific to the application.
 * 
 * This uses the {@link com.jettmarks.clsld.SubclassUtil} to get a list of 
 * classes that are subclasses of the given class (in our case {@link 
 * com.jettmarks.base.ArgParsingEntryPoint}), and then filters the resultant 
 * list to find a single instance.
 * 
 * Errors are logged and null is returned if either no classes are found or more
 * than one class is found.
 * 
 * @author Jett Marks
 * 
 */
public class ClassLoadUtil
{
  private static final String CLASS_NAME = "com.jettmarks.base.ArgParsingEntryPoint";

  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(ClassLoadUtil.class);

  /**
   * Load entry point.
   * 
   * @return the arg parsing entry point
   */
  public static ArgParsingEntryPoint loadEntryPoint()
  {
    ArgParsingEntryPoint apep = null;
    Class<?> clientClass = null;
    try
    {
      List<Class<?>> classList = SubclassUtil.find(CLASS_NAME);
      switch (classList.size())
      {
      case 0: // Problem finding any subclass
        logger.error("Unable to find subclass of " + CLASS_NAME);
        break;
      case 1: // Desired Scenario
        clientClass = classList.get(0);
        break;
      default:
        logger.error("Found more than one subclass of " + CLASS_NAME);
        break;
      }
      apep = (ArgParsingEntryPoint) clientClass.newInstance();
    } catch (InstantiationException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return apep;
  }

}
