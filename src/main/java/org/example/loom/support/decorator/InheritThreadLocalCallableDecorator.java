
package org.example.loom.support.decorator;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

public class InheritThreadLocalCallableDecorator<V> implements Callable<V> {
    private final Callable<V> delegate;
    private final Map<String, String> contextMap;

    public InheritThreadLocalCallableDecorator(Callable<V> delegate) {
        this.delegate = delegate;
        this.contextMap = MDC.getCopyOfContextMap();
    }

    @Override
    public V call() throws Exception {
        var originalMap = MDC.getCopyOfContextMap();
        try {
            if (contextMap == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(contextMap);
            }
            return delegate.call();
        } finally {
            if (originalMap == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(originalMap);
            }
        }
    }
}
