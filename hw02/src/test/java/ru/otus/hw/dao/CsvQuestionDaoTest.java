package ru.otus.hw.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvQuestionDaoTest {
    @Mock
    private TestFileNameProvider testFileNameProvider;

    @InjectMocks
    private CsvQuestionDao csvQuestionDao;

    @Test
    public void readQuestionsAndAnswerFromCSV() throws IOException {
        var properties = new Properties();
        try (var input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            assertNotNull(input, "Properties file isn't found");
            properties.load(input);
        }

        when(testFileNameProvider.getTestFileName())
                .thenReturn(properties.getProperty("test.fileName"));

        var answersFromCsv = csvQuestionDao.findAll();
        var correctAnswers = List.of(
                new Question("Which method compares the contents of two strings in Java?", List.of(
                        new Answer("equals()", true),
                        new Answer("== operator", false),
                        new Answer("compareReferences()", false)
                )),
                new Question("Which collection does not allow duplicate elements?", List.of(
                        new Answer("ArrayList", false),
                        new Answer("HashSet", true),
                        new Answer("LinkedList", false)
                ))
        );
        assertEquals(correctAnswers, answersFromCsv);
    }

    @Test
    public void csvDoesNotExist() {
        when(testFileNameProvider.getTestFileName())
                .thenReturn("not-existent_file.csv");

        assertThrows(
                QuestionReadException.class,
                () -> csvQuestionDao.findAll()
        );
    }
}
