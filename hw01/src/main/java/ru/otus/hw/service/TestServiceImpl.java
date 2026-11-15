package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;

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
        questionDao.findAll()
                .forEach(question -> {
                    ioService.printLine(question.text());
                    var answers = question.answers();

                    for (int i = 0; i < answers.size(); i++) {
                        ioService.printLine((i + 1) + ". " + answers.get(i).text());
                    }
                });
    }
}
