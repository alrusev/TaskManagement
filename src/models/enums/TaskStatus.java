package models.enums;

public enum TaskStatus {
    //Common
    //DONE,
    //ACTIVE,

    //Stories
    //NOT_DONE,
    //IN_PROGRESS,

    //Feedback
    //NEW,
    //UNSCHEDULED,
    //SCHEDULED;

    NOTDONE,
    INPROGRESS,
    NEW,
    UNSCHEDULED,
    SCHEDULED,
    ACTIVE,
    DONE;

    @Override
    public String toString() {
        switch (this) {
            case NOTDONE:
                return "Not done";
            case INPROGRESS:
                return "In Progress";
            case NEW:
                return "New";
            case UNSCHEDULED:
                return "Unscheduled";
            case SCHEDULED:
                return "Scheduled";
            case ACTIVE:
                return "Active";
            case DONE:
                return "Done";


            default:
                return "Unknown";
        }
    }
}
