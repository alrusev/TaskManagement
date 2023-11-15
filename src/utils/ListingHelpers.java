package utils;

import models.contracts.Board;
import models.contracts.Person;
import models.contracts.Team;

import java.util.ArrayList;
import java.util.List;

public class ListingHelpers {

    public static <T extends Person> String peopleToString(List<T> elements) {
        List<String> result = new ArrayList<>();
        for (T element : elements) {
            result.add(element.getName());
        }
        result.sort(String::compareToIgnoreCase);
        return String.format("People: " + String.join(", ", result).trim());
    }

    public static <T extends Team> String teamsToString(List<T> elements) {
        List<String> result = new ArrayList<>();
        for (T element : elements) {
            result.add(element.getName());
        }
        result.sort(String::compareToIgnoreCase);
        return String.format("Teams: " + String.join(", ", result).trim());
    }

    public static <T extends Person> String teamMembersToString(List<T> elements) {
        List<String> result = new ArrayList<>();
        for (T element : elements) {
            result.add(element.getName());
        }
        result.sort(String::compareToIgnoreCase);
        return String.join(", ", result).trim();
    }

    public static <T extends Board> String teamBoardsToString(List<T> elements) {
        List<String> result = new ArrayList<>();
        for (T element : elements) {
            result.add(element.getName());
        }
        result.sort(String::compareToIgnoreCase);
        return String.join(", ", result).trim();
    }
}
