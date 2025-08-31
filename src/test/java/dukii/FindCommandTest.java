package dukii;

import dukii.command.FindCommand;
import dukii.task.TaskList;
import dukii.task.ToDo;
import dukii.task.Deadline;
import dukii.task.Event;
import dukii.ui.Ui;
import dukii.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FindCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        tasks = new TaskList(new ArrayList<>());
        storage = new Storage("test.txt");
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        ui = new Ui();
    }

    @Test
    void findCommand_withEmptyKeyword_showsErrorMessage() {
        FindCommand findCommand = new FindCommand("");
        findCommand.execute(tasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Sweetie, please tell me what you're looking for! Use: find <keyword>"));
    }

    @Test
    void findCommand_withEmptyTaskList_showsEmptyMessage() {
        FindCommand findCommand = new FindCommand("test");
        findCommand.execute(tasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("No tasks to search through! Your list is empty."));
    }

    @Test
    void findCommand_withNoMatchingTasks_showsNoResultsMessage() {
        ArrayList<dukii.task.Task> taskList = new ArrayList<>();
        taskList.add(new ToDo("Buy groceries"));
        taskList.add(new ToDo("Read book"));
        tasks = new TaskList(taskList);
        
        FindCommand findCommand = new FindCommand("meeting");
        findCommand.execute(tasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Sorry honey, I couldn't find any tasks containing 'meeting'"));
    }

    @Test
    void findCommand_withMatchingTasks_showsMatchingTasks() {
        ArrayList<dukii.task.Task> taskList = new ArrayList<>();
        taskList.add(new ToDo("Buy groceries"));
        taskList.add(new ToDo("Read book"));
        taskList.add(new ToDo("Buy milk"));
        tasks = new TaskList(taskList);
        
        FindCommand findCommand = new FindCommand("buy");
        findCommand.execute(tasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Here are the tasks I found containing 'buy':"));
        assertTrue(output.contains("1.[T][ ] Buy groceries"));
        assertTrue(output.contains("3.[T][ ] Buy milk"));
        assertFalse(output.contains("2.[T][ ] Read book"));
    }

    @Test
    void findCommand_withCaseInsensitiveSearch_findsTasks() {
        ArrayList<dukii.task.Task> taskList = new ArrayList<>();
        taskList.add(new ToDo("Buy GROCERIES"));
        taskList.add(new ToDo("read BOOK"));
        tasks = new TaskList(taskList);
        
        FindCommand findCommand = new FindCommand("groceries");
        findCommand.execute(tasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Here are the tasks I found containing 'groceries':"));
        assertTrue(output.contains("1.[T][ ] Buy GROCERIES"));
    }

    @Test
    void findCommand_withDeadlineTasks_findsMatchingDeadlines() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        ArrayList<dukii.task.Task> taskList = new ArrayList<>();
        taskList.add(new ToDo("Buy groceries"));
        taskList.add(new Deadline("Submit report", tomorrow));
        taskList.add(new ToDo("Submit homework"));
        tasks = new TaskList(taskList);
        
        FindCommand findCommand = new FindCommand("submit");
        findCommand.execute(tasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Here are the tasks I found containing 'submit':"));
        assertTrue(output.contains("2.[D][ ] Submit report"));
        assertTrue(output.contains("3.[T][ ] Submit homework"));
    }

    @Test
    void findCommand_withEventTasks_findsMatchingEvents() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        ArrayList<dukii.task.Task> taskList = new ArrayList<>();
        taskList.add(new ToDo("Buy groceries"));
        taskList.add(new Event("Team meeting", today, tomorrow));
        taskList.add(new ToDo("Meeting preparation"));
        tasks = new TaskList(taskList);
        
        FindCommand findCommand = new FindCommand("meeting");
        findCommand.execute(tasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Here are the tasks I found containing 'meeting':"));
        assertTrue(output.contains("2.[E][ ] Team meeting"));
        assertTrue(output.contains("3.[T][ ] Meeting preparation"));
    }

    @Test
    void findCommand_withPartialKeywordMatches_findsTasks() {
        ArrayList<dukii.task.Task> taskList = new ArrayList<>();
        taskList.add(new ToDo("Buy groceries"));
        taskList.add(new ToDo("Read programming book"));
        taskList.add(new ToDo("Write program"));
        tasks = new TaskList(taskList);
        
        FindCommand findCommand = new FindCommand("prog");
        findCommand.execute(tasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Here are the tasks I found containing 'prog':"));
        assertTrue(output.contains("2.[T][ ] Read programming book"));
        assertTrue(output.contains("3.[T][ ] Write program"));
    }

    @Test
    void findCommand_isExit_returnsFalse() {
        FindCommand findCommand = new FindCommand("test");
        assertFalse(findCommand.isExit());
    }

    @Test
    void findCommand_modifiesStorage_returnsFalse() {
        FindCommand findCommand = new FindCommand("test");
        assertFalse(findCommand.modifiesStorage());
    }

    @Test
    void findCommand_withMultipleMatches_showsAllMatchingTasks() {
        ArrayList<dukii.task.Task> taskList = new ArrayList<>();
        taskList.add(new ToDo("Buy groceries"));
        taskList.add(new ToDo("Buy milk"));
        taskList.add(new ToDo("Buy bread"));
        taskList.add(new ToDo("Read book"));
        tasks = new TaskList(taskList);
        
        FindCommand findCommand = new FindCommand("buy");
        findCommand.execute(tasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Here are the tasks I found containing 'buy':"));
        assertTrue(output.contains("1.[T][ ] Buy groceries"));
        assertTrue(output.contains("2.[T][ ] Buy milk"));
        assertTrue(output.contains("3.[T][ ] Buy bread"));
        assertFalse(output.contains("4.[T][ ] Read book"));
    }

    @Test
    void findCommand_withSpecialCharacters_handlesCorrectly() {
        ArrayList<dukii.task.Task> taskList = new ArrayList<>();
        taskList.add(new ToDo("Buy groceries & milk"));
        taskList.add(new ToDo("Read book (fiction)"));
        tasks = new TaskList(taskList);
        
        FindCommand findCommand = new FindCommand("&");
        findCommand.execute(tasks, ui, storage);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Here are the tasks I found containing '&':"));
        assertTrue(output.contains("1.[T][ ] Buy groceries & milk"));
    }
}
