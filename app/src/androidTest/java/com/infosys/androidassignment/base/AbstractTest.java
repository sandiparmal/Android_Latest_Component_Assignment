package com.infosys.androidassignment.base;

import static junit.framework.Assert.fail;

public class AbstractTest {

    /**
     * Wait for the specific time using {@link Thread#sleep(long)}
     *
     * @param milliseconds The time we want to wait for in millis
     */
    protected void waitFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Wait for a condition to be true
     *
     * @param condition The condition we want to be true
     * @param time      The time to wait for
     */
    protected void waitForCondition(Condition condition, int time) {
        int timeWaited = 0;
        while (timeWaited < time) {
            if (condition.isSatisfied()) {
                return;
            }
            waitFor(200);
            timeWaited += 200;
        }
    }
}
