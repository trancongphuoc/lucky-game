/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.excutor;

import com.nh.GlobalConfig;
import com.nh.bean.Command;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;


/**
 *
 * @author hadc
 */
public class TaskManagement implements Runnable{
    private static final long DISTANCE_RELOAD_COMMAND = 5 * 60 * 1000;// 5 minutes
    
    private final Logger log = Logger.getLogger(TaskManagement.class);
    
    private final List<Command> commands;
   
    private final Object lock = new Object();
    private long lastTimeLoadCommand = 0;
    private boolean running = false;
    
    private static TaskManagement taskManagement;
    
    private TaskManagement (){
        commands = new LinkedList<>();
        loadCommand();
    }
    
    private void loadCommand(){
        synchronized(lock) {
            List<Command> tmCommands = new LinkedList<>();
            try(BufferedReader br = new BufferedReader(new FileReader(GlobalConfig.getSchedulerPath()))) {
                String line = br.readLine();
                int i = 0;
                while (line != null) {
                    if (line.isEmpty() || line.startsWith("#")) {
                        line = br.readLine();
                        continue;
                    }
                    line = line.trim();
                    String[] args = line.split(" ");
                    if (args == null || args.length < 6) {
                        log.warn("line error, length < 6, abort =>" + line);
                        continue;
                    }
                    log.debug("read line " + ++i + " => " + line);
                    Command c = new Command(args);
                    c.setTextPlain(line);
                    tmCommands.add(c);

                    line = br.readLine();
                }
            }   catch (Exception ex) {
                log.error("error when read file", ex);
            }
            
            boolean existedCommand = false;
            
            //add new command
            for (Command tmCommand : tmCommands) {
                existedCommand = false;
                
                for (Command command : commands) {
                    if(command.getTextPlain().equals(tmCommand.getTextPlain())) {
                        existedCommand = true;
                        break;
                    }
                }
                
                if (!existedCommand) {
                    log.info("add command: " + tmCommand);
                    commands.add(tmCommand);
                }
            }
            
            //remove not exist in config file
            for (Command command : commands) {
                existedCommand = false;
                for (Command tmCommand : tmCommands) {
                     if(tmCommand.getTextPlain().equals(command.getTextPlain())) {
                        existedCommand = true;
                        break;
                    }
                }
                
                //remove command
                if (!existedCommand) {
                    log.info("remove command: " + command);
                    commands.remove(command);
                }
                
            }
            
            log.debug("command size: " + commands.size() );
        }
    }
    
    public int commandSize() {
         synchronized(lock) {
             return commands.size();
         }
    }
    
    public synchronized static TaskManagement getInstance() {
        if (taskManagement == null) {
            taskManagement = new TaskManagement();
        }
        return taskManagement;
    }
    
    public  void start() {
        running = true;
        Thread t = new Thread(this);
        t.setName("thread-TaskManagement");
        t.start();
    }
    
    public void stop() {
        running = false;
    }
    
    private List<Command> getRunCommand() {
        synchronized(lock) {
            List<Command> runCommands = new LinkedList<>();
            for (Command c: commands) {
                if (c.isRuntime()) {
                    runCommands.add(c);
                }
            }
            return runCommands;
        }
    }
    
    @Override
    public void run() {
        while(running) {
            try{
                List<Command> runCommands = getRunCommand();
                if (!runCommands.isEmpty()) {
                    for (Command command : runCommands) {
                        TaskQueue.getInstance().enqueue(command.getCommand());
                    }
                } else {
                    log.debug("nothing to run");
                }
                
                if (System.currentTimeMillis() - lastTimeLoadCommand > DISTANCE_RELOAD_COMMAND) {
                    log.debug("reload commands");
                    loadCommand();
                    log.debug("commandSize: " + commandSize());
                    lastTimeLoadCommand = System.currentTimeMillis();
                }
                Thread.sleep(30000);//sleep 30s
//                Thread.yield();
            } catch(Exception x){
                log.error("error when collect task", x);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=======================================================\n");
        sb.append("=================").append(this.getClass().getSimpleName())
                .append("=========================\n");
        sb.append("======active command======================\n");
        synchronized(lock) {
            for (Command command : commands) {
                sb.append(command.toString()).append("\n");
            }
        }
        sb.append("=======================================================\n");
        sb.append("=======================================================\n");
        
        return sb.toString();
    }
    
}
