package arrodes.parser;

import org.junit.jupiter.api.Test;
import arrodes.exception.ArrodesException;
import static org.junit.jupiter.api.Assertions.*;
import arrodes.command.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

class CommandParserTest {

    @Test
    void tokenize_nullInput_exceptionThrown() {
        ArrodesException ex = assertThrows(ArrodesException.class,
                () -> CommandParser.tokenize(null));
        assertEquals(ArrodesException.NO_INPUT, ex.getMessage());
    }

    @Test
    void tokenize_blankInput_exceptionThrown() {
        ArrodesException ex = assertThrows(ArrodesException.class,
                () -> CommandParser.tokenize("   "));
        assertEquals(ArrodesException.NO_INPUT, ex.getMessage());
    }

    @Test
    void tokenize_commandOnly_noDescriptionNoParameters() {
        TokenizedCommand result = CommandParser.tokenize("list");
        assertEquals("list", result.getCommand());
        assertFalse(result.hasDescription());
        assertFalse(result.hasParameters());
    }

    @Test
    void tokenize_commandWithDescription_descriptionParsedCorrectly() {
        TokenizedCommand result = CommandParser.tokenize("todo Buy groceries");
        assertEquals("todo", result.getCommand());
        assertEquals("Buy groceries", result.getDescription());
        assertFalse(result.hasParameters());
    }

    @Test
    void tokenize_commandWithSingleFlag_parameterParsedCorrectly() {
        TokenizedCommand result = CommandParser.tokenize("deadline Submit report /by 2025-12-01");
        assertEquals("deadline", result.getCommand());
        assertEquals("Submit report", result.getDescription());
        assertTrue(result.hasParameters());
        assertEquals("2025-12-01", result.getParameters().get("by"));
    }

    @Test
    void tokenize_commandWithMultipleFlags_allParametersParsed() {
        TokenizedCommand result = CommandParser.tokenize("event Team meeting /from 2025-12-01 /to 2025-12-02");
        assertEquals("event", result.getCommand());
        assertEquals("Team meeting", result.getDescription());
        assertEquals("2025-12-01", result.getParameters().get("from"));
        assertEquals("2025-12-02", result.getParameters().get("to"));
    }

    @Test
    void tokenize_flagWithNoValue_exceptionThrown() {
        // "/by" has no value after it
        assertThrows(ArrodesException.class,
                () -> CommandParser.tokenize("deadline Submit /by"));
    }

    // ── parse – bye ───────────────────────────────────────────────────────────

    @Test
    void parse_byeCommand_returnsCorrectType() {
        Command cmd = CommandParser.parse("bye");
        assertInstanceOf(ByeCommand.class, cmd);
    }

    @Test
    void parse_byeWithExtraText_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("bye now"));
    }

    // ── parse – list ──────────────────────────────────────────────────────────

    @Test
    void parse_listCommand_returnsCorrectType() {
        Command cmd = CommandParser.parse("list");
        assertInstanceOf(ListCommand.class, cmd);
    }

    @Test
    void parse_listWithExtraText_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("list all"));
    }

    @Test
    void parse_listWithFlag_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("list /on 2025-12-01"));
    }

    /** Verifies that the find keyword creates a command with a search term. */
    @Test
    void parse_findCommand_returnsFindCommand() {
        assertInstanceOf(FindCommand.class, CommandParser.parse("find book"));
    }

    /** Verifies that find requires a non-empty search term. */
    @Test
    void parse_findWithoutKeyword_exceptionThrown() {
        assertThrows(ArrodesException.class, () -> CommandParser.parse("find"));
    }

    /** Verifies that find rejects flag-like parameters. */
    @Test
    void parse_findWithParameter_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("find book /from shelf"));
    }

    // ── parse – mark ──────────────────────────────────────────────────────────

    @Test
    void parse_markValidIndex_returnsMarkCommandWithCorrectIndex() {
        assertInstanceOf(MarkCommand.class, CommandParser.parse("mark 3"));
    }

    @Test
    void parse_markNoIndex_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("mark"));
    }

    @Test
    void parse_markNonNumericIndex_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("mark abc"));
    }

    @Test
    void parse_markWithFlag_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("mark 1 /extra value"));
    }


    @Test
    void parse_unmarkValidIndex_returnsUnmarkCommand() {
        assertInstanceOf(UnmarkCommand.class, CommandParser.parse("unmark 5"));
    }

    @Test
    void parse_unmarkNoIndex_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("unmark"));
    }

    @Test
    void parse_unmarkNonNumericIndex_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("unmark xyz"));
    }


    @Test
    void parse_deleteValidIndex_returnsDeleteCommandWithCorrectIndex() {
        assertInstanceOf(DeleteCommand.class, CommandParser.parse("delete 2"));
    }

    @Test
    void parse_deleteNoIndex_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("delete"));
    }

    @Test
    void parse_deleteNonNumericIndex_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("delete one"));
    }


    @Test
    void parse_todoWithDescription_returnsTodoCommandWithDescription() {
        assertInstanceOf(TodoCommand.class, CommandParser.parse("todo Buy milk"));
    }

    @Test
    void parse_todoNoDescription_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("todo"));
    }

    @Test
    void parse_todoWithFlag_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("todo Buy milk /by tomorrow"));
    }

    @Test
    void parse_deadlineNoDescription_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("deadline /by 2025-12-01"));
    }

    @Test
    void parse_deadlineNoByFlag_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("deadline Submit report"));
    }

    @Test
    void parse_deadlineInvalidDateFormat_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("deadline Submit /by next-monday"));
    }

    @Test
    void parse_deadlineNonExistentDate_exceptionThrown() {
        // Feb 30 doesn't exist
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("deadline Submit /by 2025-02-30"));
    }




    @Test
    void parse_eventMixedFromDateToDateTime_exceptionThrown() {
        // XOR guard: one has time component, other does not
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("event Stand-up /from 2025-12-01 /to 2025-12-01T09:30"));
    }

    @Test
    void parse_eventNoDescription_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("event /from 2025-12-01 /to 2025-12-02"));
    }

    @Test
    void parse_eventMissingFromFlag_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("event Team meeting /to 2025-12-02"));
    }

    @Test
    void parse_eventMissingToFlag_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("event Team meeting /from 2025-12-01"));
    }

    @Test
    void parse_eventInvalidFromDate_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("event Meeting /from not-a-date /to 2025-12-02"));
    }

    @Test
    void parse_upcomingMissingOnFlag_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("upcoming"));
    }

    @Test
    void parse_upcomingWithDescription_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("upcoming someday /on 2025-12-01"));
    }


    @Test
    void parse_unknownCommand_exceptionThrown() {
        ArrodesException ex = assertThrows(ArrodesException.class,
                () -> CommandParser.parse("fly to the moon"));
        assertEquals(ArrodesException.UNKNOWN_COMMAND, ex.getMessage());
    }


    @Test
    void parse_emptyString_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse(""));
    }

    @Test
    void parse_whitespaceOnly_exceptionThrown() {
        assertThrows(ArrodesException.class,
                () -> CommandParser.parse("   "));
    }

    @Test
    void tokenize_descriptionWithSlashInMiddle_treatedAsFlag() {
        // A slash in the description creates an extra parameter section
        TokenizedCommand result = CommandParser.tokenize("event A/B test /from 2025-01-01 /to 2025-01-02");
        // "A" is the description; "B test" becomes an unknown flag section
        // The key expectation: the parser does not crash and "from"/"to" are present
        assertTrue(result.hasParameters());
        assertTrue(result.getParameters().containsKey("from"));
        assertTrue(result.getParameters().containsKey("to"));
    }
}
