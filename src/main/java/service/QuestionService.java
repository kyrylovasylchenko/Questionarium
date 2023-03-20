package service;

import model.Question;
import repository.dao.QuestionRepository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public class QuestionService {
    private final QuestionRepository repository;

    private final Map<String, List<Question>> questionsByTopic = new HashMap<>();

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public Question getRndQuestionByTopic(String topic) {
        List<Question> questions = questionsByTopic.containsKey(topic) ?questionsByTopic.get(topic) : repository.getByTopic(topic);
        questionsByTopic.put(topic, questions);
        int randomNum = ThreadLocalRandom.current().nextInt(0, questions.size());
        return questions.get(randomNum);
    }

    public Question getRndQuestion() throws SQLException {
        List<Question> topics = repository.getAllQuestions();
        int randomNum = ThreadLocalRandom.current().nextInt(0, topics.size());
        return topics.get(randomNum);
    }
}
