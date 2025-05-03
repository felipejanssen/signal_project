package com.alerts.decorator;

// adds a priority tag before showing the alert
public class PriorityAlertDecorator extends AlertDecorator {
    private String priority;

    public PriorityAlertDecorator(Alertable wrapped, String priority) {
        super(wrapped);
        this.priority = priority;
    }

    @Override
    public void show() {
        System.out.println("[" + priority + "] ");
        wrapped.show();
    }
}
