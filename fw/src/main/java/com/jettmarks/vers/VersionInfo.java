/**
 * Created Sep 25, 2009
 */
package com.jettmarks.vers;

/**
 * @author jett
 *
 */
public class VersionInfo
{
  private Class<?> clazz = null;
    
  public String getVersion()
  {
    return ManifestVersion.getVersion();
  }
  
  public String getBuild()
  {
    return ManifestVersion.getBuild();
  }
  
  public void setCueClass(String className)
  {
    clazz = null;
    try
    {
      clazz = Class.forName(className);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    ManifestVersion.setClass(clazz);
  }
  
  public String getCueClass()
  {
    return clazz.getName();
  }
  
}
