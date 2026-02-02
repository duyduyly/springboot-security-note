package com.example.security.common.utils.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionUtils {
    public static void runWithExceptionCatch(String message, boolean canThrow, CheckedRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            if (canThrow) {
                throw new RuntimeException(message, e);
            }
            // handle Message
            log.error(message, e);
        }
    }

    public static void runWithExAndRunningTime(String message, boolean canThrow, CheckedRunnable runnable) {
        long startTime = System.currentTimeMillis();
        log.info("Start: {}", startTime);
        try {
            runnable.run();
            endTime(startTime);
        } catch (Exception e) {
            endTime(startTime);
            if (canThrow) {
                throw new RuntimeException(message, e);
            }
            // handle Message
            log.error(message, e);
        }
    }

    private static void endTime(long startTime) {
        long endTime = System.currentTimeMillis();
        log.info("End: {}", startTime);
        log.info("Running time: {} milliseconds", (endTime- startTime));
        log.info("Running time: {} seconds", (endTime- startTime)/1000);
    }
}
