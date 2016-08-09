/**
 * Put your copyright and license info here.
 */
package com.iaccap.data.apex.etl.app;

import com.datatorrent.api.LocalMode;
import org.apache.hadoop.conf.Configuration;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.io.IOException;

/**
 * Test the DAG declaration in local mode.
 */
public class Application2Test {

  @Test
  public void testApplication() throws IOException, Exception {
    try {
      LocalMode lma = LocalMode.newInstance();
      Configuration conf = new Configuration(false);
      conf.addResource(this.getClass().getResourceAsStream("/META-INF/properties.xml"));
      lma.prepareDAG(new ApplicationMap(), conf);
      LocalMode.Controller lc = lma.getController();
      lc.run(200_000); // runs for 2 seconds and quits
    } catch (ConstraintViolationException e) {
      Assert.fail("constraint violations: " + e.getConstraintViolations());
    }
  }

}
