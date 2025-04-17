package com.airtribe.orm.demo.logs;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.encoder.EncoderBase;
import net.logstash.logback.composite.loggingevent.MdcJsonProvider;
import net.logstash.logback.decorate.PrettyPrintingJsonGeneratorDecorator;
import net.logstash.logback.encoder.LogstashEncoder;
import net.logstash.logback.fieldnames.LogstashFieldNames;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class PIIAwareLogstashEncoder extends EncoderBase<ILoggingEvent> {
    private final LogstashEncoder delegate = new LogstashEncoder();
    private final PIIMaskingLayout piiMaskingLayout = new PIIMaskingLayout();

    // Configuration properties
    private boolean includeContext = true;
    private boolean includeMdc = true;
    private boolean includeCallerData = false;
    private PrettyPrintingJsonGeneratorDecorator jsonGeneratorDecorator;
    private LogstashFieldNames fieldNames;
    private MdcJsonProvider mdcJsonProvider;

    public PIIAwareLogstashEncoder() {
    }

    // Setters for configuration from logback.xml
    public void setIncludeContext(boolean includeContext) {
        this.includeContext = includeContext;
        delegate.setIncludeContext(includeContext);
    }

    public void setIncludeMdc(boolean includeMdc) {
        this.includeMdc = includeMdc;
        delegate.setIncludeMdc(includeMdc);
    }

    public void setIncludeCallerData(boolean includeCallerData) {
        this.includeCallerData = includeCallerData;
        delegate.setIncludeCallerData(includeCallerData);
    }

    public void setJsonGeneratorDecorator(PrettyPrintingJsonGeneratorDecorator decorator) {
        this.jsonGeneratorDecorator = decorator;
        delegate.setJsonGeneratorDecorator(decorator);
    }

    public void setFieldNames(LogstashFieldNames fieldNames) {
        this.fieldNames = fieldNames;
        delegate.setFieldNames(fieldNames);
    }

    public void setProvider(MdcJsonProvider provider) {
        this.mdcJsonProvider = provider;
        delegate.addProvider(provider);
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        delegate.setContext(context);
        if (mdcJsonProvider != null) {
            mdcJsonProvider.setContext(context);
        }
    }

    @Override
    public void start() {
        if (jsonGeneratorDecorator != null) {
            delegate.setJsonGeneratorDecorator(jsonGeneratorDecorator);
        }
        if (fieldNames == null) {
            fieldNames = new LogstashFieldNames();
            delegate.setFieldNames(fieldNames);
        }
        if (mdcJsonProvider == null) {
            mdcJsonProvider = new MdcJsonProvider();
            mdcJsonProvider.setIncludeMdcKeyNames(Arrays.asList("trace_id", "span_id"));
            if (getContext() != null) {
                mdcJsonProvider.setContext(getContext());
            }
            delegate.addProvider(mdcJsonProvider);
        } else {
            if (getContext() != null) {
                mdcJsonProvider.setContext(getContext());
            }
            delegate.addProvider(mdcJsonProvider);
        }
        delegate.setIncludeContext(includeContext);
        delegate.setIncludeMdc(includeMdc);
        delegate.setIncludeCallerData(includeCallerData);
        delegate.start();
        super.start();
    }

    @Override
    public void stop() {
        delegate.stop();
        super.stop();
    }

    @Override
    public byte[] headerBytes() {
        return delegate.headerBytes();
    }

    @Override
    public byte[] encode(ILoggingEvent event) {
        String maskedMessage = piiMaskingLayout.doLayout(event);
        LoggingEvent modifiedEvent = new LoggingEvent();
        modifiedEvent.setLoggerName(event.getLoggerName());
        modifiedEvent.setLevel(event.getLevel());
        modifiedEvent.setMessage(maskedMessage);
        modifiedEvent.setArgumentArray(event.getArgumentArray());
        modifiedEvent.setThrowableProxy((ThrowableProxy) event.getThrowableProxy());
        modifiedEvent.setTimeStamp(event.getTimeStamp());
        modifiedEvent.setThreadName(event.getThreadName());
        modifiedEvent.setMDCPropertyMap(event.getMDCPropertyMap());
        return delegate.encode(modifiedEvent);
    }

    @Override
    public byte[] footerBytes() {
        return delegate.footerBytes();
    }
}