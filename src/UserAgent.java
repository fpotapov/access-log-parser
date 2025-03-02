public class UserAgent {
    private final String operatingSystem;
    private final String browser;

    public UserAgent(String userAgentString) {
        this.operatingSystem = extractOperatingSystem(userAgentString);
        this.browser = extractBrowser(userAgentString);
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getBrowser() {
        return browser;
    }

    private String extractOperatingSystem(String userAgentString) {
        if (userAgentString.contains("Windows")) {
            return "Windows";
        } else if (userAgentString.contains("Macintosh") || userAgentString.contains("Mac OS X")) {
            return "macOS";
        } else if (userAgentString.contains("Linux")) {
            return "Linux";
        } else {
            return "Unknown";
        }
    }

    private String extractBrowser(String userAgentString) {
        if (userAgentString.contains("Edge")) {
            return "Edge";
        } else if (userAgentString.contains("Firefox")) {
            return "Firefox";
        } else if (userAgentString.contains("Chrome") && !userAgentString.contains("Edg")) {
            return "Chrome";
        } else if (userAgentString.contains("Opera") || userAgentString.contains("OPR")) {
            return "Opera";
        } else {
            return "Other";
        }
    }
}