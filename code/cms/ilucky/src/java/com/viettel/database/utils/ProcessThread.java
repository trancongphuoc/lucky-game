package com.viettel.database.utils;

import com.viettel.utils.Constant;

/**
 * class that perform task about thread in ground process.
 * it hold and supply to deliver class many task to control
 * thread is stop, start, restart
 *
 * @author admin
 * @version 2.0
 * @since 1.0
 */
public abstract class ProcessThread implements Runnable {

    static int RUNNING = 0;
    static int STOPPED = 1;
    /**
     * name of processer use to diplay in log
     */
    protected String threadName;
    /**
     * boolean flag indentify that thread is running or not
     */
    protected boolean isRunning;
    /**
     * status of processing, this status must be {@link #RUNNING
     * RUNNING} or {@link #STOPPED STOPPED}
     */
    protected int status;
    protected Object lock = new Object();
//    protected Logger logger;

    protected ProcessThread(String logMdl) {
        this(logMdl, "Unknown");
    }

    public ProcessThread(String logMdl, String threadName) {
        this.threadName = threadName;
//         logger = Logger.getLogger(logMdl);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        setProcessStatus(RUNNING);
        while (isRunning) {
            try {
                process();
                Thread.yield();
            } catch (Exception ex) {Constant.LogNo(ex);
            }
        }
        setProcessStatus(STOPPED);
    }

    /**
     * Method process thread. This method will invoke by run to process many
     * task involve. This method must be overwrite in deliver class that extends
     * this class. Process in this method will invoke in while
     */
    protected abstract void process();

    /**
     * Method set start for run thread. It create new thread and execute run
     * method in this class in separate
     */
    public void start() {
        if (!isRunning) {
            isRunning = true;
//                logger.log("Starting "+threadName+" Process...",DEBUG);
            Thread t = new Thread(this);
            t.start();
//                logger.log(threadName+" is started...",DEBUG);
        }
    }

    /**
     * stop thread. this invoke method may be wait for a mount time until
     * thread real stop, because some current thread is blocked some where
     */
    public void stop() {
        if (isRunning) //is running thread
        {
//            logger.log("Stopping "+threadName+" Process...",DEBUG);
            isRunning = false;
            Thread.interrupted();
            while (getStatus() != STOPPED) {
                Thread.yield();
            }
//            logger.log(threadName+" is stopped...",DEBUG);
        }
    }

    /**
     * method stop and start service
     */
    public void restart() {
        stop();
        start();
    }

    /**
     * stop thread and remove resource involve
     */
    public void destroy() {
        stop();
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    protected void setProcessStatus(int status) {
        this.status = status;
    }

    protected int getStatus() {
        return status;
    }
}
