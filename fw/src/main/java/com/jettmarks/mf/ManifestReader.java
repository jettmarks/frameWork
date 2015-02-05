/**
 * Created Jun 9, 2009
 */
package com.jettmarks.mf;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.jar.Manifest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * Obtains a {@link java.util.jar.Manifest} from the jar file that contains a 
 * class we expect to be within that jar.
 */
public class ManifestReader
{
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(ManifestReader.class);

  /**
   * Read a {@link java.util.jar.Manifest} from the jar file that also contains
   *  the passed {@link java.lang.Class}.   *  
   * For WAR files, we have a location we can reach based on following 
   * the root, but then jump over to /META-INF instead of /WEB-INF.
   * 
   * We can accomplish this by using the same class as a "cue Class", and
   * then pulling out the /WEB-INF/classes that should be appended to the 
   * root path that we desire./
   * 
   * @param mainClass the class we expect to reside within the jar that holds
   *  the manifest we're interested in.  The class that defines the 
   *  {main(String args[])} method is recommended.
   * 
   * @return the manifest from the jar file that also contains the mainClass.
   * 
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Manifest read(Class<?> mainClass) throws IOException
  {
    String path = mainClass.getResource(toResourceName(mainClass)).toString();
    logger.info("Found path '"+path+"' for "+mainClass.toString());
    String manifestPath = extractRoot(path, mainClass)
        + "/META-INF/MANIFEST.MF";
    InputStream stream = new URL(manifestPath).openStream();
    try
    {
      return new Manifest(stream);
    } finally
    {
      IOUtils.closeQuietly(stream);
    }
  }
  
  /**
   * Extract root.
   * 
   * @param path the path
   * @param mainClass the main class
   * 
   * @return the string
   */
  private static String extractRoot(String path, Class<?> mainClass)
  {
    if (path.contains("/WEB-INF/classes"))
    {
      return StringUtils.substringBefore(path,"/WEB-INF/classes");
    }
    if (path.contains("!"))
    {
      return path.substring(0, path.lastIndexOf("!") + 1);
    } else
    {
      return StringUtils.substringBefore(path, mainClass.getName().replace('.',
          '/'));
    }
  }

  /**
   * To resource name.
   * 
   * @param mainClass the main class
   * 
   * @return the string
   */
  private static String toResourceName(Class<?> mainClass)
  {
    return "/" + mainClass.getName().replace('.', '/') + ".class";
  }
}
