package service;

import model.Question;
import org.junit.Assert;
import org.junit.Test;
import repository.ConnectionSingelton;
import repository.QuestionRepositoryImp;
import repository.dao.QuestionRepository;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class QuestionServiceTest {

    private   List<Question> testQuestions = List.of(
            Question.builder().id(1).text("something").topic("testTopic").build(),
            Question.builder().id(2).text("something").topic("testTopic").build(),
            Question.builder().id(3).text("something").topic("testTopic").build());

    private QuestionRepository repository = new QuestionRepository(){

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
            return null;
        }

        @Override
        public List<Question> getAllQuestions() throws SQLException {
            return testQuestions;
        }
    };


    @Test
    public void getRndQuestionByTopicTest() throws SQLException {
        String topic = "OOP";
        QuestionRepositoryImp impl = new QuestionRepositoryImp(ConnectionSingelton.getConnection());
        QuestionService testService = new QuestionService(impl);
        Question rndQuestionByTopic = testService.getRndQuestionByTopic(topic);
        Assert.assertTrue(impl.getByTopic(topic).contains(rndQuestionByTopic));
    }
    @Test
    public void getRndQuestionTest() throws SQLException {
        QuestionService questionService = new QuestionService(this.repository);
        Question rndQuestion = questionService.getRndQuestion();
        Assert.assertTrue(testQuestions.contains(rndQuestion));
    }
}
