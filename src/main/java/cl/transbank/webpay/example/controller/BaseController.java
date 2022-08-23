package cl.transbank.webpay.example.controller;

import com.google.gson.GsonBuilder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public  abstract class BaseController {
    @Getter private Map<String, Object> model = new HashMap<>();

    protected void addModel(String key, Object value) {
        getModel().put(key, value);
    }

    protected void addRequest(String key, Object value) {
        Object request = getModel().get("request");
        if (null == request) {
            request = new HashMap<String, Object>();
            getModel().put("request", request);
        }

        if (request instanceof Map) {
            ((Map) request).put(key, value);
        }
    }

    protected void cleanModel() {
        model.clear();
    }

    protected void prepareLoger(Level logLevel) {
        //Logger.getGlobal().setLevel(Level.FINEST);

        /*
        java.util.logging.Logger globalLog = java.util.logging.Logger.getLogger("cl.transbank");
        globalLog.setUseParentHandlers(false);
        globalLog.addHandler(new ConsoleHandler() {
            {
                //setOutputStream(System.out);
                setLevel(Level.ALL);
            }
        });
        globalLog.setLevel(logLevel);*/


        //get the top Logger
        Logger topLogger = java.util.logging.Logger.getLogger("");

        // Handler for console (reuse it if it already exists)
        Handler consoleHandler = null;
        //see if there is already a console handler
        for (Handler handler : topLogger.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                //found the console handler
                consoleHandler = handler;
                break;
            }
        }


        if (consoleHandler == null) {
            //there was no console handler found, create a new one
            consoleHandler = new ConsoleHandler();
            topLogger.addHandler(consoleHandler);
        }
        //set the console handler to fine:
        consoleHandler.setLevel(Level.FINEST);
    }

    public String toJson(Object obj){
        return (new GsonBuilder().setPrettyPrinting().create()).toJson(obj);
    }
}
