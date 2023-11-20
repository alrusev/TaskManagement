package utils;

import models.contracts.Board;
import models.contracts.Person;
import models.contracts.Team;

import java.util.ArrayList;
import java.util.List;

public class ListingHelpers {

    public static <T extends Person> String peopleToString(List<T> elements) {
        StringBuilder result = new StringBuilder();
        int nextId = 1;
        for (T element : elements) {
            result.append(nextId++).append(". ").append(element.getName()).append(System.lineSeparator());
        }
        return "People registered in the system:\n" + String.join(System.lineSeparator(), result).trim();
    }

    public static <T extends Team> String teamsToString(List<T> elements) {
        StringBuilder result = new StringBuilder();
        int nextId = 1;
        for (T element : elements) {
            result.append(nextId++).append(". ").append(element.getName()).append(System.lineSeparator());
        }
        return "Teams registered in the system:\n" + String.join(System.lineSeparator(), result).trim();
    }

    public static <T extends Person> String teamMembersToString(List<T> elements) {
        StringBuilder result = new StringBuilder();
        int nextId = 1;
        for (T element : elements) {
            result.append(nextId++).append(". ").append(element.getName()).append(System.lineSeparator());
        }
        return String.join(System.lineSeparator(), result).trim();
    }

    public static <T extends Board> String teamBoardsToString(List<T> elements) {
        StringBuilder result = new StringBuilder();
        int nextId = 1;
        for (T element : elements) {
            result.append(nextId++).append(". ").append(element.getName()).append(System.lineSeparator());
        }
        return String.join(System.lineSeparator(), result).trim();
    }
}
