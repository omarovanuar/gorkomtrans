package com.epam.anuar.gorkomtrans.action;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ActionFactory {

    public static final String ACTION_CONFIG = "action";
    private Map<String, Action> actions;

    public ActionFactory() {
        ResourceBundle rb = ResourceBundle.getBundle(ACTION_CONFIG);
        actions = new HashMap<>();

        actions.put(rb.getString("showWelcome"), new ShowPageAction("welcome"));
        actions.put(rb.getString("showLogin"), new ShowPageAction("login"));
        actions.put(rb.getString("doLogin"), new LoginAction());
        actions.put(rb.getString("doLogout"), new LogoutAction());
        actions.put(rb.getString("showHome"), new ShowPageAction("home"));
    }

    public Action getAction(String actionName) {
        return actions.get(actionName);
    }
}
