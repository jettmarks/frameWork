/**
 * Created Jun 9, 2009
 */
package com.jettmarks.vers;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.jettmarks.mf.ManifestReader;

/**
 * Uses the passed {@link java.lang.Class} to find within the containing jar 
 * file the {@link java.util.jar.Manifest} that holds version and build 
 * properties.
 * 
 * @author Jett Marks
 */
public class ManifestVersion
{
  private static Attributes atts = null;

  /**
   * Inspects the manifest within the jar file named by the passed clazz to find
   * version and build properties.
   * 
   * @param clazz
   */
  public static void printVersion(Class<?> clazz)
  {
    try
    {
      Manifest mf = ManifestReader.read(clazz);

      atts = mf.getMainAttributes();

      System.out.println("Version: " + atts.getValue("Implementation-Version"));
      System.out.println("Build: " + atts.getValue("Implementation-Build"));

    } catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /**
   * Use ourself if not supplied with a class.
   */
  private static void getAttributes()
  {
    getAttributes(ManifestVersion.class);
  }

  public static String getVersion()
  {
    if (atts == null)
    {
      getAttributes();
    }
    return (atts.getValue("Implementation-Version"));
  }

  public static String getBuild()
  {
    if (atts == null)
    {
      getAttributes();
    }
    return (atts.getValue("Implementation-Build"));
  }
  
  public static void setClass(Class<?> clazz)
  {
    getAttributes(clazz);
  }

  /**
   * @param clazz
   */
  private static void getAttributes(Class <?> clazz)
  {
    try
    {
      Manifest mf = ManifestReader.read(clazz);

      atts = mf.getMainAttributes();
    } catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
