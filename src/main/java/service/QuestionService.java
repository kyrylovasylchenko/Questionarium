package service;

import model.Question;
import repository.dao.QuestionRepository;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class QuestionService {
    private final QuestionRepository repository;

    private final Map<String, List<Question>> questionsByTopic = new HashMap<>();

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public Question getRndQuestionByTopic(String topic) {
        List<Question> questions = getAllQuestionByTopic(topic);
        int randomNum = ThreadLocalRandom.current().nextInt(0, questions.size());
        return questions.get(randomNum);
    }

    public Question getRndQuestion(){
        return repository.getRndQuestion();
    }

    public void removeQuestion(int id){
        repository.delete(id);
    }


    public List<Question> getAllQuestionByTopic(String topic) {
        List<Question> questions = questionsByTopic.containsKey(topic) ?questionsByTopic.get(topic) : repository.getByTopic(topic);
        questionsByTopic.put(topic, questions);
        return questions;
    }

    public Set<String> getAllTopics(){
        return repository.getAllTopics();
    }

    public void addQuestion(String topic, String questionText) {
        repository.save(Question.builder().topic(topic).text(questionText).build());
    }
}
