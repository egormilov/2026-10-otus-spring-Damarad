package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            boolean isAnswerValid = askQuestion(question);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private boolean askQuestion(Question question) {
        printQuestion(question);

        List<Answer> answers = question.answers();

        int inputNumber = ioService.readIntForRangeWithPrompt(
                1,
                answers.size(),
                "Enter the answer number:",
                "Please enter a number from 1 to " + answers.size()
        );

        return answers.get(inputNumber - 1).isCorrect();
    }

    private void printQuestion(Question question) {
        ioService.printLine(question.text());

        List<Answer> answers = question.answers();

        for (int i = 0; i < answers.size(); i++) {
            ioService.printFormattedLine(
                    "%d. %s",
                    i + 1,
                    answers.get(i).text()
            );
        }
    }
}
