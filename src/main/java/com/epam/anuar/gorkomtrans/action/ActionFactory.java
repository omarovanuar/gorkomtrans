package com.epam.anuar.gorkomtrans.action;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ActionFactory {

    private static final String ACTION_CONFIG = "action";
    private Map<String, Action> actions;

    public ActionFactory() {
        ResourceBundle rb = ResourceBundle.getBundle(ACTION_CONFIG);
        actions = new HashMap<>();

        actions.put(rb.getString("showWelcome"), new ShowPageAction("welcome"));
        actions.put(rb.getString("doLogin"), new LoginAction());
        actions.put(rb.getString("doLogout"), new LogoutAction());
        actions.put(rb.getString("showHome"), new ShowPageAction("home"));
        actions.put(rb.getString("showRegister"), new ShowPageAction("register"));
        actions.put(rb.getString("doRegister"), new RegisterAction());
        actions.put(rb.getString("showEmployee"), new ShowPageAction("employee"));
        actions.put(rb.getString("showContacts"), new ShowPageAction("contacts"));
        actions.put(rb.getString("showServices"), new ShowPageAction("services"));
        actions.put(rb.getString("showPersonalCabinet"), new ShowPageAction("personal-cabinet"));
        actions.put(rb.getString("doUpdate"), new UpdateAction());
    }

    public Action getAction(String actionName) {
        return actions.get(actionName);
    }
}
