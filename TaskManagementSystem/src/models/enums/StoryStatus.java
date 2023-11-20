package models.enums;

public enum StoryStatus {
    NOTDONE,
    INPROGRESS,
    DONE;

    @Override
    public String toString() {
        switch (this) {
            case NOTDONE:
                return "Not done";
            case INPROGRESS:
                return "In Progress";
            case DONE:
                return "Done";


            default:
                return "Unknown";
        }
    }
}
