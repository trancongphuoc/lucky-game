package com.viettel.framework.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.viettel.database.bean.Users;
import com.viettel.utils.Constant;
import java.util.Map;

public class ActionInterceptor extends AbstractInterceptor {

    public ActionInterceptor() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
    }

    private void addActionError(ActionInvocation invocation, String message) {
        Object action = invocation.getAction();

        if (action instanceof ValidationAware) {
            ((ValidationAware) action).addActionError(message);
        }
    }

    @Override
    public String intercept(ActionInvocation actionInvocation)
            throws Exception {
        Map sess = actionInvocation.getInvocationContext().getSession();
        Users userid = (Users) sess.get(Constant.STRING_USERTOKEN);
        if (userid == null) {
            addActionError(actionInvocation, "You must be authenticated to access this page");
            return Action.ERROR;
        }
        return actionInvocation.invoke();
    }
}
