package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {
    @Mock
    private IOService ioService;

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private TestServiceImpl service;

    @Test
    void countsCorrectAnswer() {
        var student = new Student("Maksim", "Savin");
        var question = new Question("What is 2 + 2?", List.of(
                new Answer("3", false),
                new Answer("4", true)
        ));

        when(questionDao.findAll())
                .thenReturn(List.of(question));

        when(ioService.readIntForRangeWithPrompt(
                eq(1),
                eq(2),
                anyString(),
                anyString()
        )).thenReturn(2);

        var result = service.executeTestFor(student);

        assertEquals(1, result.getRightAnswersCount());
    }

    @Test
    void countsIncorrectAnswer() {
        var student = new Student("Maksim", "Savin");
        var question = new Question("What is 2 + 2?", List.of(
                new Answer("3", false),
                new Answer("4", true)
        ));

        when(questionDao.findAll())
                .thenReturn(List.of(question));

        when(ioService.readIntForRangeWithPrompt(
                eq(1),
                eq(2),
                anyString(),
                anyString()
        )).thenReturn(1);

        var result = service.executeTestFor(student);

        assertEquals(0, result.getRightAnswersCount());
        assertEquals(1, result.getAnsweredQuestions().size());
    }
}