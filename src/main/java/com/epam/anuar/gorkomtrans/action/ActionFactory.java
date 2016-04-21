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
        actions.put(rb.getString("showRegister"), new ShowRegisterAction());
        actions.put(rb.getString("doRegister"), new RegisterAction());
        actions.put(rb.getString("showEmployee"), new ShowPageAction("employee"));
        actions.put(rb.getString("showContracts"), new ShowUserContractsAction());
        actions.put(rb.getString("showServices"), new ShowPageAction("services"));
        actions.put(rb.getString("showPersonalCabinet"), new ShowPageAction("personal-cabinet"));
        actions.put(rb.getString("doChangeUserParameters"), new ChangeUserParametersAction());
        actions.put(rb.getString("doFillTechSpec"), new FillTechSpecAction());
        actions.put(rb.getString("showTechSpec"), new ShowPageAction("tech-spec"));
        actions.put(rb.getString("doCreateContract"), new CreateContractAction());
        actions.put(rb.getString("showContract"), new ShowPageAction("contract"));
        actions.put(rb.getString("showContractStatus"), new ShowPageAction("contract-status"));
        actions.put(rb.getString("doSubmitContract"), new SubmitContractAction());
        actions.put(rb.getString("doContractView"), new ContractViewAction());
        actions.put(rb.getString("showContractSanction"), new ShowAllContractsAction());
        actions.put(rb.getString("showErrorPage"), new ShowPageAction("error-page"));
        actions.put(rb.getString("doAgreeContract"), new AgreeContractAction());
        actions.put(rb.getString("doDenyContract"), new DenyContractAction());
        actions.put(rb.getString("showAdminCabinet"), new ShowAllUsersAction());
        actions.put(rb.getString("showUserView"), new UserViewAction());
        actions.put(rb.getString("doChangeUserView"), new ChangeUserViewAction());
        actions.put(rb.getString("doDeleteUser"), new DeleteUserAction());
    }

    public Action getAction(String actionName) {
        return actions.get(actionName);
    }
}
