package models;

import models.contracts.HistoryEntry;
import utils.ValidationHelpers;

public class HistoryEntryImpl implements HistoryEntry {

    private static final int DESCRIPTION_MIN_LENGTH = 10;
    private static final int DESCRIPTION_MAX_LENGTH = 500;
    private static final String DESCRIPTION_ERROR_MESSAGE =
            String.format("Description must be between %d and %d symbols", DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH);

    private String description;

    public HistoryEntryImpl(String description) {
        setDescription(description);
    }

    @Override
    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        ValidationHelpers.validateIntRange(description.length(), DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH, DESCRIPTION_ERROR_MESSAGE);
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("### %s", getDescription());
    }
}
