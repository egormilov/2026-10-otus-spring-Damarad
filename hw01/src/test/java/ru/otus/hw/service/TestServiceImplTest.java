package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.Mockito.inOrder;
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
    void printsQuestionWithNumberedAnswers() throws URISyntaxException {
        Question question = new Question("What is 2 + 2?", List.of(
                new Answer("3", false),
                new Answer("4", true)
        ));

        when(questionDao.findAll())
                .thenReturn(List.of(question));

        service.executeTest();

        InOrder order = inOrder(ioService);

        order.verify(ioService).printLine("What is 2 + 2?");
        order.verify(ioService).printLine("1. 3");
        order.verify(ioService).printLine("2. 4");
    }

    @Test
    void restartsAnswerNumberingForEachQuestion() throws URISyntaxException {
        Question question1 = new Question("What is 2 + 2?", List.of(
                new Answer("3", false),
                new Answer("4", true)
        ));
        Question question2 = new Question("What is 3 + 3?", List.of(
                new Answer("5", false),
                new Answer("6", true)
        ));

        when(questionDao.findAll())
                .thenReturn(List.of(question1, question2));

        service.executeTest();

        InOrder order = inOrder(ioService);

        order.verify(ioService).printLine("What is 2 + 2?");
        order.verify(ioService).printLine("1. 3");
        order.verify(ioService).printLine("2. 4");
        order.verify(ioService).printLine("What is 3 + 3?");
        order.verify(ioService).printLine("1. 5");
        order.verify(ioService).printLine("2. 6");
    }
}