package repository;

import model.Question;
import org.junit.*;
import repository.dao.QuestionRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;


public class QuestionRepositoryTest {
    private static String user = "postgres";
    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String password = "root";
    private static Connection connection;
    private static QuestionRepository questionRepository;

    @BeforeClass
    public static void init() throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        questionRepository = new QuestionRepositoryImp(connection);

    }

    @AfterClass
    public static void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getTest() {
        Question expected = Question.builder().text("text").topic("OOP").build();
        questionRepository.save(expected);
        Question actual = questionRepository.get(2);
        expected.setId(2);
        Assert.assertEquals(expected, actual);
        questionRepository.delete(2);
    }

    @Test
    public void getByTopicTest() {
        List<Question> oopQuestion = questionRepository.getByTopic("OOP");
        oopQuestion.forEach(question -> Assert.assertEquals("OOP", question.getTopic()));
    }

    @Test
    public void deleteTest() {
        Question questionToDelete = Question.builder().text("text").topic("OOP").build();
        questionRepository.save(questionToDelete);
        questionRepository.delete(3);
        List<Question> oop = questionRepository.getByTopic("OOP");
        oop.forEach(question -> Assert.assertTrue(question.getId() != 3));
    }

    @Test
    public void saveTest() {
        Question expected = Question.builder().text("text").topic("OOP").build();
        questionRepository.save(expected);
        Question actual = questionRepository.get(4);
        expected.setId(4);
        Assert.assertEquals(expected, actual);
        questionRepository.delete(4);
    }

    @Test
    public void updateTest() {
        Question expected = Question.builder().text("text").topic("OOP").build();
        questionRepository.save(expected);
        expected.setTopic("NOT OOP");
        expected.setId(13);
        questionRepository.update(expected);
        Question actual = questionRepository.get(13);
        Assert.assertEquals(expected, actual);
        questionRepository.delete(13);
    }


}

