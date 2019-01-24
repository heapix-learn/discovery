package com.heapixLearn.discovery.logic.authorization;

public class RunnableWithError implements Runnable {
    private AuthorizationErrors.TypeOfAuthManagerError error = AuthorizationErrors.TypeOfAuthManagerError.SERVER_ERROR;
    Runnable init(AuthorizationErrors.TypeOfAuthManagerError myError){
        this.error=myError;
        return this;
    }

    @Override
    public void run() {
    }

    protected AuthorizationErrors.TypeOfAuthManagerError getError() {
        return error;
    }

}
