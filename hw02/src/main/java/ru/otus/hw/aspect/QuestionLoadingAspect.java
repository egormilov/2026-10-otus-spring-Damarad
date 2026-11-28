package ru.otus.hw.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.IOService;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class QuestionLoadingAspect {
    private final IOService ioService;

    @Pointcut("execution(* ru.otus.hw.dao.CsvQuestionDao.findAll(..))")
    private void questionLoad() {
    }

    @Before("questionLoad()")
    public void logBeforeLoading() {
        ioService.printLine("Loading questions...");
    }

    @AfterReturning(value = "questionLoad()", returning = "questions")
    public void logAfterLoading(List<Question> questions) {
        ioService.printFormattedLine(
                "Loaded %d questions",
                questions.size()
        );
    }
}
