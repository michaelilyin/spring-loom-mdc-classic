package org.example.loom.support.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;

public class InheritThreadLocalDecorator implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(InheritThreadLocalDecorator.class);
    private final Runnable delegate;
    private final Map<String, String> contextMap;

    public InheritThreadLocalDecorator(Runnable delegate) {
        this.delegate = delegate;
        this.contextMap = MDC.getCopyOfContextMap();
    }

    @Override
    public void run() {
        var originalMap = MDC.getCopyOfContextMap();
        log.info("Inherit callable with original {} and context {}", originalMap, contextMap);
        try {
            if (contextMap == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(contextMap);
            }
            delegate.run();
        } finally {
            if (originalMap == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(originalMap);
            }
        }
    }
}
