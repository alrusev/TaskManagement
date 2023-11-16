package commands;

import core.RepositoryImpl;
import core.contracts.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

public class ShowAllPeopleCommandTests {

    private Repository repository;
    private ShowAllPeopleCommand showAllPeopleCommand;


    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        showAllPeopleCommand = new ShowAllPeopleCommand(repository);
    }

    @Test
    public void execute_Should_ShowNoPeopleRegistered_When_NoPeopleCreated() {
        // Act
        String result = showAllPeopleCommand.execute(Collections.emptyList());

        // Assert
        String expectedOutput = "There are no registered people.";
        assertEquals(expectedOutput, result.trim());
    }

    @Test
    public void execute_Should_ShowPeopleRegistered_When_PeopleCreated() {
        // Arrange
        repository.createPerson("Alice");
        repository.createPerson("Bobby");

        // Act
        String result = showAllPeopleCommand.execute(Collections.emptyList());

        // Assert
        String expectedOutput = "People: Alice, Bobby";
        assertEquals(expectedOutput, result.trim());
    }
}