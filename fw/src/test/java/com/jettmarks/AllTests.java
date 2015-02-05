/**
 * Created Jun 9, 2009
 */
package com.jettmarks;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.jettmarks.base.ArgParsingEntryPointTest;
import com.jettmarks.base.EntryPointFactoryTest;
import com.jettmarks.clsld.SubclassUtilTest;

public class AllTests
{

  public static Test suite()
  {
    TestSuite suite = new TestSuite("Test for com.jettmarks");
    // $JUnit-BEGIN$
    suite.addTestSuite(SubclassUtilTest.class);
    suite.addTestSuite(EntryPointFactoryTest.class);
    suite.addTestSuite(ArgParsingEntryPointTest.class);
    // $JUnit-END$
    return suite;
  }

}
