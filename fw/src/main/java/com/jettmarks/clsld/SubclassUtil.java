/**
 * Created Jun 9, 2009
 */
package com.jettmarks.clsld;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.log4j.Logger;

/**
 * Utility class that finds all the classes implementing or extending a given
 * interface or class.
 * 
 * Looks within loaded packages, but not the full classpath.
 * 
 * Excludes inner classes (by looking for '$' in the name).
 * 
 * Originally called RTSI (RunTime Subclass Identification) as written by Daniel
 * Le Berre, the original author and described under <a href=
 * "http://www.javaworld.com/javaworld/javatips/jw-javatip113.html">Java Tip 113
 * </a> from Java World.
 * 
 * @author <a href="mailto:daniel@satlive.org">Daniel Le Berre</a> (Original)
 * @author <a href="http://jettmarks.com/">Jett Marks</a>
 */
public class SubclassUtil
{
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(SubclassUtil.class);

  /**
   * Display all the classes inheriting or implementing a given class in the
   * currently loaded packages.
   * 
   * @param parentClassName
   *          the name of the class to inherit from
   */
  public static List<Class<?>> find(String parentClassName)
  {
    List<Class<?>> subClasses = new ArrayList<Class<?>>();
    try
    {
      Class<?> parentClass = Class.forName(parentClassName);
      if (parentClass.isInterface())
      {
        logger.debug(parentClassName + " is an Interface");
      } else
      {
        logger.debug("SuperClass of " + parentClassName + " is "
            + parentClass.getSuperclass());
      }
      for (Package testPackage : Package.getPackages())
      {
        subClasses.addAll(find(testPackage.getName(), parentClass));
      }
    } catch (ClassNotFoundException ex)
    {
      System.err.println("Class " + parentClassName + " not found!");
    }
    return subClasses;
  }

  /**
   * Display all the classes inheriting or implementing a given class in a given
   * package.
   * 
   * @param packageName
   *          the fully qualified name of the package
   * @param parentClassName
   *          the name of the class to inherit from
   */
  public static List<Class<?>> find(String packageName, String parentClassName)
  {
    List<Class<?>> subclasses = new ArrayList<Class<?>>();
    try
    {
      Class<?> parentClass = Class.forName(parentClassName);
      subclasses.addAll(find(packageName, parentClass));
    } catch (ClassNotFoundException ex)
    {
      logger.error("Class " + parentClassName + " not found!");
    }
    return subclasses;
  }

  /**
   * Returns a list of the classes within the package 'packagename' that
   * implement or extend parentClass.
   * 
   * @param packageName
   *          the fully qualified name of the package
   * @param parentClass
   *          the Class object to inherit from
   */
  public static Set<Class<?>> find(String packageName, Class<?> parentClass)
  {
    Set<Class<?>> subclasses = new HashSet<Class<?>>();

    // Translate the package name into an absolute path
    String name = new String(packageName);
    if (!name.startsWith("/"))
    {
      name = "/" + name;
    }
    name = name.replace('.', '/');

    // Get a File object for the package
    URL url = SubclassUtil.class.getResource(name);
    if (url == null)
    {
      url = ClassLoader.getSystemClassLoader().getResource(name);
    }
    logger.debug("URL for " + name + " -> " + url);

    if (url == null)
    {
      return subclasses;
    }

    File directory = new File(url.getFile());

    if (directory.exists())
    {
      // Get the list of the files contained in the package
      String[] files = directory.list();
      for (int i = 0; i < files.length; i++)
      {
        // we are only interested in .class files
        if (files[i].endsWith(".class"))
        {
          // removes the .class extension
          String classname = packageName + "."
              + files[i].substring(0, files[i].length() - 6);

          Class<?> clazz = checkClass(parentClass, classname);
          if (clazz != null)
          {
            subclasses.add(clazz);
          }
        }
      }
    } else
    {
      try
      {
        // It does not work with the filesystem: we must
        // be in the case of a package contained in a jar file.
        JarURLConnection conn = (JarURLConnection) url.openConnection();
        String starts = conn.getEntryName();
        JarFile jfile = conn.getJarFile();
        Enumeration<?> e = jfile.entries();
        while (e.hasMoreElements())
        {
          ZipEntry entry = (ZipEntry) e.nextElement();
          String entryname = entry.getName();
          if (entryname.startsWith(starts)
              && (entryname.lastIndexOf('/') <= starts.length())
              && entryname.endsWith(".class"))
          {
            String classname = entryname.substring(0, entryname.length() - 6);
            if (classname.startsWith("/"))
            {
              classname = classname.substring(1);
            }
            classname = classname.replace('/', '.');

            Class<?> clazz = checkClass(parentClass, classname);
            if (clazz != null)
            {
              subclasses.add(clazz);
            }
          }
        }
      } catch (IOException ioex)
      {
        System.err.println(ioex);
      }
    }
    return subclasses;
  }

  /**
   * Knows how to load a class and check if it is a subclass of the testClass.
   * 
   * Will not count instances of itself. Will not count instances that contain
   * '$' in the name.
   * 
   * @param parentClass
   * @param testClassName
   * @return matching class or null if not matching or if the same as
   *         parentClass.
   */
  private static Class<?> checkClass(Class<?> parentClass, String testClassName)
  {
    // Do not count instances of ourself
    if (testClassName.equals(parentClass.getName()))
    {
      return null;
    }

    // Do not count inner classes (that have '$' in the name)
    if (testClassName.indexOf('$') > 0)
    {
      logger.debug("Class " + testClassName
          + " rejected because of '$' in name");
      return null;
    }

    Class<?> clazz = null;
    try
    {
      logger.debug("Looking at class " + testClassName);
      clazz = Class.forName(testClassName);
      if (parentClass.isAssignableFrom(clazz))
      {
        logger.info(testClassName.substring(testClassName.lastIndexOf('.') + 1)
            + " is a subclass of " + parentClass.getSimpleName());
      } else
      {
        clazz = null;
      }
    } catch (ClassNotFoundException cnfex)
    {
      logger.error(cnfex);
      clazz = null;
    }
    return clazz;
  }

}
