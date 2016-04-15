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
        actions.put(rb.getString("doFillTechSpec"), new FillTechSpecAction());
        actions.put(rb.getString("showTechSpec"), new ShowPageAction("tech-spec"));
        actions.put(rb.getString("doCreateContract"), new CreateContractAction());
        actions.put(rb.getString("showContract"), new ShowPageAction("contract"));
        actions.put(rb.getString("showSubmitContract"), new ShowPageAction("submitted-contract"));
        actions.put(rb.getString("doSubmitContract"), new SubmitContractAction());
        actions.put(rb.getString("doContractView"), new ContractViewAction());
    }

    public Action getAction(String actionName) {
        return actions.get(actionName);
    }
}
