package service;

import model.Question;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.ConnectionSingleton;
import repository.QuestionRepositoryImp;
import repository.dao.QuestionRepository;

import java.sql.SQLException;
import java.util.*;

public class QuestionServiceTest {

    private static Map<String, List<Question>> sqlTable = new HashMap<>();

    private static List<Question> oopQuestions = List.of(
            Question.builder().id(1).text("first").topic("OOP").build(),
            Question.builder().id(2).text("second").topic("OOP").build(),
            Question.builder().id(3).text("third").topic("OOP").build());

    private static List<Question> newQuestions = List.of(
            Question.builder().id(1).text("fourth").topic("NEW").build(),
            Question.builder().id(2).text("fifth").topic("NEW").build(),
            Question.builder().id(3).text("sixth").topic("NEW").build());

    private QuestionRepository repository = new QuestionRepository() {

        @Override
        public Question get(int id) {
            return null;
        }

        @Override
        public void save(Question question) {

        }

        @Override
        public void update(Question question) {

        }

        @Override
        public void delete(int id) {

        }

        @Override
        public List<Question> getByTopic(String topic) {
            return sqlTable.get(topic);
        }

        @Override
        public List<Question> getAllQuestions() throws SQLException {
            ArrayList<Question> result = new ArrayList<>();
            result.addAll(oopQuestions);
            result.addAll(newQuestions);
            return result;
        }

        @Override
        public Set<String> getAllTopics() {
            return null;
        }

        @Override
        public Question getRndQuestion() {
            return null;
        }
    };

    @BeforeClass
    public static void init() {
        sqlTable.put("OOP", oopQuestions);
        sqlTable.put("NEW", newQuestions);
    }


    @Test
    public void getRndQuestionByTopicTest() throws SQLException {
        String topic = "OOP";
        QuestionService testService = new QuestionService(repository);
        Set<Question> rndQuestions = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            Question rndQuestionByTopic = testService.getRndQuestionByTopic(topic);
            rndQuestions.add(rndQuestionByTopic);
        }
        Assert.assertTrue(rndQuestions.size() > 1);
    }

    @Test
    public void getAllQuestionByTopicTest() {
        QuestionService questionService = new QuestionService(repository);
        List<Question> oop = questionService.getAllQuestionByTopic("OOP");
        Assert.assertEquals(oop, oopQuestions);


    }
}
