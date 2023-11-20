package models.enums;

public enum BugStatus {
    ACTIVE,
    DONE;

    @Override
    public String toString() {
        switch (this) {
            case ACTIVE:
                return "Active";
            case DONE:
                return "Done";


            default:
                return "Unknown";
        }
    }
}
