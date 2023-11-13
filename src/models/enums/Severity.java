package models.enums;

public enum Severity {
    MINOR,
    MAJOR,
    CRITICAL;

    @Override
    public String toString() {
        switch (this) {
            case MINOR:
                return "Minor";
            case MAJOR:
                return "Major";
            case CRITICAL:
                return "Critical";


            default:
                return "Unknown";
        }
    }
}
