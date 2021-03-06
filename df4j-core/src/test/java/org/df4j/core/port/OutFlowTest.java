package org.df4j.core.port;

import org.df4j.core.activities.PublisherActor;
import org.df4j.core.util.Logger;
import org.reactivestreams.*;
import org.junit.Assert;
import org.junit.Test;

public class OutFlowTest {

    static class SimpleSubscriber implements Subscriber<Long> {
        protected final Logger logger = new Logger(this);
        volatile boolean completed = false;

        @Override
        public void onSubscribe(Subscription subscription) {
            subscription.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(Long in) {
            logger.info(" got: " + in);
        }

        @Override
        public void onError(Throwable e) {
            logger.info(" completed with: " + e);
            completed = true;
        }

        @Override
        public void onComplete() {
            onError(null);
        }
    }

    @Test
    public void outFlowTest() throws InterruptedException {
        PublisherActor pub = new PublisherActor(1, 0);
        SimpleSubscriber simpleSubscriber = new SimpleSubscriber();
        pub.out.subscribe(simpleSubscriber);
        pub.start();

        boolean success = pub.blockingAwait(100);
        Assert.assertTrue(success);
        Thread.sleep(50);
        Assert.assertTrue(simpleSubscriber.completed);
    }
}

