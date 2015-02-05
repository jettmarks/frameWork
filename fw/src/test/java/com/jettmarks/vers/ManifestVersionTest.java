/**
 * Created Jun 9, 2009
 */
package com.jettmarks.vers;

import junit.framework.TestCase;

/**
 * 
 * @author Jett Marks
 */
public class ManifestVersionTest extends TestCase
{

  /**
   * Test method for
   * {@link com.jettmarks.vers.ManifestVersion#printVersion(java.lang.Class)}.
   */
  public void testPrintVersion()
  {
    assertNotNull(ManifestVersion.getBuild());
    assertNotNull(ManifestVersion.getVersion());
  }

}
