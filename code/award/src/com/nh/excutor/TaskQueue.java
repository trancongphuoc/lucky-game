/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.excutor;



import com.nh.GlobalConfig;
import java.util.LinkedList;
import org.apache.log4j.Logger;

/**
 *
 * @author hadc
 */
public class TaskQueue {
    private final LinkedList<String> queue = new LinkedList<>();

    private final Object obj = new Object();
    private static TaskQueue _instance = null;
    
    private final static Logger log = Logger.getLogger(TaskQueue.class);
    
    private TaskQueue() {
    }

    public synchronized static TaskQueue getInstance() {
        if (_instance == null) {
            _instance = new TaskQueue();
            int excutorLength = GlobalConfig.getExcutorLength();
            log.info("length of excutor: " + excutorLength);
            for (int i = 0; i < excutorLength; i++) {
//                String excutorName = TaskExcutor.class.getSimpleName() + i;
                TaskExcutor excutor = new TaskExcutor(i);
                excutor.start();
            }
        }
        
        return _instance;
    }
   
    public void enqueue(String cmd) {
        synchronized (obj) {
            queue.add(cmd);
            log.info("enqueue queue("+queue.size()+"): " + cmd);
        }
    }

    public  String dequeue() {
        synchronized (obj) {
            if (queue.isEmpty()) {
                return null;
            }

            return queue.remove(0);
        }
    }
   
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=======================================================\n");
        sb.append("=================").append(this.getClass().getSimpleName())
                .append("=========================\n");
        sb.append("Task in queue: \n");
         
        for (String command : queue) {
            sb.append(command).append("\n");
        }
        sb.append("=======================================================\n");
        sb.append("=======================================================\n");
        
        return sb.toString();
    }
}

