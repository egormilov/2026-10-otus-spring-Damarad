package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {

        try (var inputStream = getClass().getClassLoader().getResourceAsStream(fileNameProvider.getTestFileName())) {
            if (inputStream == null) {
                throw new QuestionReadException("Reading error");
            }

            CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder<QuestionDto>(new InputStreamReader(inputStream))
                    .withSkipLines(1)
                    .withSeparator(';')
                    .withType(QuestionDto.class)
                    .build();

            return csvToBean.parse()
                    .stream()
                    .map(QuestionDto::toDomainObject)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new QuestionReadException(e.getMessage(), e);
        }

    }
}