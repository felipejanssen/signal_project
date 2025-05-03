package com.alerts.decorator;

// base decorator that wraps an Alertable
public abstract class AlertDecorator implements Alertable{
    protected Alertable wrapped;

    public AlertDecorator(Alertable wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public abstract void show(); // subclasses add extra behavior

    @Override
    public String getPatientId() {
        return wrapped.getPatientId();
    }

    @Override
    public String getCondition() {
        return wrapped.getCondition();
    }

    @Override
    public long getTimestamp() {
        return wrapped.getTimestamp();
    }
}
