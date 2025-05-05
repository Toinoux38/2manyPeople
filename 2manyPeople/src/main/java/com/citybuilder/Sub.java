package com.citybuilder;
import com.citybuilder.modelBis.events.GameEvent;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Consumer;

public class Sub {

    // Private constructor to hide the implicit public one
    private Sub() {
    }

    public static <T extends GameEvent> Subscriber<GameEvent> get(Class<T> t, Consumer<T> consumer) {
        return new Subscriber<GameEvent>() {
            @Override
            public void onNext(GameEvent item) {
                //Get generic type of t


                if (t.isInstance(item)) {
                    consumer.accept(t.cast(item));
                }

            }

            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };
    }
}
