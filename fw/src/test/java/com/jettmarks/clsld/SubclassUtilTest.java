/**
 * Created Jun 9, 2009
 */
package com.jettmarks.clsld;

import java.util.List;

import junit.framework.TestCase;

public class SubclassUtilTest extends TestCase
{

  public void testFindString()
  {
    List<Class<?>> subclassInstances = SubclassUtil
        .find("com.jettmarks.base.ArgParsingEntryPoint");
    assertNotNull(subclassInstances);
    assertEquals(1, subclassInstances.size());
  }

}
