package com.alerts.decorator;

import com.alerts.Alert;

// repeats the wrapped show() n times
public class RepeatedAlertDecorator extends AlertDecorator {
    private int times;

    public RepeatedAlertDecorator(Alertable wrapped, int times) {
        super(wrapped);
        this.times = times;
    }

    @Override
    public void show() {
        for (int i = 0; i < times; i++) {
            wrapped.show();
        }
    }
}
