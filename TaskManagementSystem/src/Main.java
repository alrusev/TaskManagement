import core.EngineImpl;
import models.BoardImpl;
import models.PersonImpl;
import models.TeamImpl;
import models.contracts.Board;
import models.contracts.Person;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EngineImpl engine = new EngineImpl();
        engine.run();
    }
}