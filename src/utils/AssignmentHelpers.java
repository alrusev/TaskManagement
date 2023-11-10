package utils;

import models.contracts.Bug;
import models.contracts.Person;
import models.contracts.Story;
import models.contracts.Team;

public class AssignmentHelpers {
    public class TaskAssignmentService {

        public void assignBugToPerson(Bug bug, Person person, Team team) {
            if (team.getMembers().contains(person)) {
                bug.assignTo(person);
            } else {
                throw new IllegalArgumentException("Person must be a member of the team.");
            }
        }

        public void assignStoryToPerson(Story story, Person person, Team team) {
            if (team.getMembers().contains(person)) {
                story.assignTo(person);
            } else {
                throw new IllegalArgumentException("Person must be a member of the team.");
            }
        }
    }
}
