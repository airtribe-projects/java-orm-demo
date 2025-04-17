package com.airtribe.orm.demo.logs;
//package com.groww.logs.layouts;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PIIMaskingLayout extends LayoutBase<ILoggingEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PIIMaskingLayout.class);
    private static final Pattern PII_PRECHECK_PATTERN = Pattern.compile(PIIRegex.getAllPiiRegex());

    public PIIMaskingLayout() {
        LOGGER.info("Initializing PIIMaskingLayout...");
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        String message = event.getFormattedMessage();
        PIIDetectionResult result = maskPII(message);

        if (result.isPiiData()) {
            LOGGER.debug("Masked PII in log message: {}", result.getMaskedMessage());
            return result.getMaskedMessage();
        }
        return message;
    }

    public PIIDetectionResult maskPII(String message) {
        Matcher preCheckMatcher = PII_PRECHECK_PATTERN.matcher(message);
        if (!preCheckMatcher.find()) {
            return new PIIDetectionResult(false, message); // No PII, return original message
        }

        // PII detected, mask all instances
        StringBuilder result = new StringBuilder();
        boolean piiFound = false;
        preCheckMatcher.reset();

        while (preCheckMatcher.find()) {
            String piiData = preCheckMatcher.group();
            StringBuilder maskedPii = createMaskedString(piiData);
            preCheckMatcher.appendReplacement(result, maskedPii.toString());
            piiFound = true;
        }
        preCheckMatcher.appendTail(result);

        return new PIIDetectionResult(piiFound, result.toString());
    }

    private StringBuilder createMaskedString(String piiData) {
        StringBuilder maskedPii = new StringBuilder();
        for (int i = 0; i < piiData.length(); i++) {
            if (i % 2 == 0) {
                maskedPii.append('*');
            } else {
                maskedPii.append(piiData.charAt(i));
            }
        }
        return maskedPii;
    }

    public static final class PIIDetectionResult {
        private final boolean isPiiData;
        private final String maskedMessage;

        public PIIDetectionResult(boolean isPiiData, String maskedMessage) {
            this.isPiiData = isPiiData;
            this.maskedMessage = maskedMessage;
        }

        public boolean isPiiData() {
            return isPiiData;
        }

        public String getMaskedMessage() {
            return maskedMessage;
        }
    }
}