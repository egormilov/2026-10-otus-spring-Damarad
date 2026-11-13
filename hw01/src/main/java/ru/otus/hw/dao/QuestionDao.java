package ru.otus.hw.dao;

import ru.otus.hw.domain.Question;

import java.net.URISyntaxException;
import java.util.List;

public interface QuestionDao {
    List<Question> findAll() throws URISyntaxException;
}