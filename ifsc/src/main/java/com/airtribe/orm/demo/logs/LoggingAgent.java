package com.airtribe.orm.demo.logs;

import java.lang.instrument.Instrumentation;
public class LoggingAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println(
                "Groww Logging Agent loaded from base image. Providing PIIAwareLogstashEncoder and PIIMaskingLayout.");
    }
}