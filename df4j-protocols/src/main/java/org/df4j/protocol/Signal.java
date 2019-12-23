package org.df4j.protocol;

/**
 * repeatable signal stream without errors
 */
public class Signal {

    private Signal() {}

    /**
     * A {@link Publisher} is a provider of a potentially unbounded number of permits
     */
    public interface Publisher {

        /**
         * asynchronous version of Semaphore.aquire()
         *
         * @param subscriber
         *      the {@link Subscriber} that will consume signals from this {@link Signal.Publisher}
         */
        void subscribe(Subscriber subscriber);
    }

    /**
     *  inlet for permits.
     *
     */
    public interface Subscriber {
        void onSubscribe(Subscription subscription);

        /**
         * asynchronous version of Semaphore.aquire()
         */
        void awake();
    }

}
