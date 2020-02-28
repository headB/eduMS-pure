package cn.wolfcode.edums.core.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程辅助类
 *
 * @author Leon
 */
public final class ThreadUtil {
    static Logger logger = LogManager.getLogger();

    public static void sleep(int start, int end) {
        try {
            Thread.sleep(MathUtil.getRandom(start, end).longValue());
        } catch (InterruptedException e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
        }
    }

    public static void sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e.getMessage());
            }
        }
    }

    public static ExecutorService threadPool(String threadName, int core, int seconds) {
        logger.info("Freed threadPoolExecutor " + threadName);
        return new ThreadPoolExecutor(core, core, seconds, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                new ThreadFactoryBuilder().setNameFormat(threadName + "-%d").build());
    }

    private static final Map<String, ExecutorService> EXECUTORS = new ConcurrentHashMap<>();
    private static final Map<String, List<Future<?>>> FUTURES = new ConcurrentHashMap<>();
    private static final Map<String, Lock> LOCKS = new ConcurrentHashMap<>();

    /**
     * 使用线程池执行线程(默认5个线程;线程池空闲1分钟后销毁)
     *
     * @param threadName
     *            线程名
     * @param runnable
     */
    public static void execute(String threadName, Runnable runnable) {
        execute(threadName, 5, 60, runnable);
    }

    /**
     * 使用线程池执行线程(线程池空闲1分钟后销毁)
     *
     * @param threadName
     *            线程名
     * @param core
     *            线程数
     * @param seconds
     *            when the number of threads is greater thanthe core, this is the
     *            maximum time that excess idle threadswill wait for new tasks
     *            before terminating.
     * @param runnable
     */
    public static void execute(String threadName, int core, int seconds, Runnable runnable) {
        if (!LOCKS.containsKey(threadName)) {
            LOCKS.put(threadName, new ReentrantLock(true));
        }
        LOCKS.get(threadName).lock();
        try {
            boolean first = EXECUTORS.get(threadName) == null;
            if (first) {
                EXECUTORS.putIfAbsent(threadName, threadPool(threadName, core, seconds));
                if (FUTURES.get(threadName) == null) {
                    FUTURES.putIfAbsent(threadName, new ArrayList<>());
                } else {
                    FUTURES.get(threadName).clear();
                }
            }
            ExecutorService executorService = EXECUTORS.get(threadName);
            Future<?> future = executorService.submit(runnable);
            FUTURES.get(threadName).add(future);
            if (first) {
                shutdown(threadName);
            }
        } finally {
            LOCKS.get(threadName).unlock();
        }
    }

    @SuppressWarnings("AlibabaAvoidUseTimer")
    private static void shutdown(String threadName) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (LOCKS.get(threadName).tryLock()) {
                    try {
                        boolean done = true;
                        for (Future<?> future : FUTURES.get(threadName)) {
                            if (!future.isDone()) {
                                done = false;
                            }
                        }
                        if (done) {
                            EXECUTORS.get(threadName).shutdown();
                            EXECUTORS.remove(threadName);
                            FUTURES.get(threadName).clear();
                            timer.cancel();
                            logger.info("Freed threadPoolExecutor " + threadName);
                        }
                    } finally {
                        LOCKS.get(threadName).unlock();
                    }
                }
            }
        }, 1000 * 60, 1000 * 60 * 3);
    }
}
