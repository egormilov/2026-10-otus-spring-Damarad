package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;

import java.net.URISyntaxException;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");

        printQuestions();
    }

    private void printQuestions() {
        try {
            questionDao.findAll()
                    .forEach(question -> {
                        ioService.printLine(question.text());
                        var answers = question.answers();

                        for (int i = 0; i < answers.size(); i++) {
                            ioService.printLine((i + 1) + ". " + answers.get(i).text());
                        }
                    });
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
