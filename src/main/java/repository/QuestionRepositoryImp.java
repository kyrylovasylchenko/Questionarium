package repository;

import Exceptions.sql.*;
import model.Question;
import repository.dao.QuestionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuestionRepositoryImp implements QuestionRepository {
    private Connection connection;

    public QuestionRepositoryImp(Connection connection) {
        this.connection = ConnectionSingleton.getConnection();
    }

    private final String findById = "SELECT * FROM question where id=?";
    private final String getAllQuest = "SELECT * FROM question ";
    private final String saveQuestion = "INSERT INTO question (text, topic) VALUES (?,?)";
    private final String findByTopic = "SELECT * FROM question where topic=?";
    private final String updateText = "UPDATE question SET text=? where id=?";
    private final String deleteById = "DELETE FROM question where id=?";
    private final String getAllTopics = "SELECT DISTINCT topic FROM question";
    private final String getRndQuestion = "SELECT * FROM question ORDER BY RANDOM() LIMIT 1";


    @Override
    public Question get(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(findById);
            preparedStatement.setInt(1, id);
            ResultSet question = preparedStatement.executeQuery();
            question.next();
            return Question.builder()
                    .id(question.getInt("id"))
                    .text(question.getString("text"))
                    .topic(question.getString("topic"))
                    .build();
        } catch (SQLException e) {
            throw new SqlGetException(e.getMessage());
        }
    }

    @Override
    public void save(Question question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(saveQuestion);
            preparedStatement.setString(1, question.getText());
            preparedStatement.setString(2, question.getTopic());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlSaveException(e.getMessage());
        }
    }

    @Override
    public void update(Question question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateText);
            preparedStatement.setString(1, question.getText());
            preparedStatement.setInt(2, question.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlUpdateException(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteById);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SqlDeleteException(e.getMessage());
        }
    }

    @Override
    public List<Question> getByTopic(String topic) {
        List<Question> questionsByTopic = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(findByTopic);
            preparedStatement.setString(1, topic);
            ResultSet question = preparedStatement.executeQuery();
            while (question.next()) {
                questionsByTopic.add(Question.builder()
                        .id(question.getInt("id"))
                        .text(question.getString("text"))
                        .topic(question.getString("topic"))
                        .build());
            }
            return questionsByTopic;
        } catch (SQLException e) {
            throw new SqlGetByTopicException(e.getMessage());
        }
    }

    @Override
    public List<Question> getAllQuestions(){
        List<Question> allQuestions = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(getAllQuest);
            ResultSet question = preparedStatement.executeQuery();
            while (question.next()) {
                allQuestions.add(Question.builder()
                        .id(question.getInt("id"))
                        .text(question.getString("text"))
                        .topic(question.getString("topic"))
                        .build());
            }
        } catch (SQLException e) {
            throw new SqlGetAllQuestionsException(e.getMessage());
        }
        return allQuestions;
    }

    @Override
    public Set<String> getAllTopics() {
        Set<String> allTopics = new HashSet<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(getAllTopics);
            ResultSet topics = preparedStatement.executeQuery();
            while(topics.next()){
                allTopics.add(topics.getString("topic"));
            }
            return allTopics;
        } catch (SQLException e) {
            throw new SqlGetAllTopicException(e.getMessage());
        }
    }

    @Override
    public Question getRndQuestion() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getRndQuestion);
            ResultSet rndQuestion = preparedStatement.executeQuery();
            rndQuestion.next();
            return Question.builder()
                    .id(rndQuestion.getInt("id"))
                    .text(rndQuestion.getString("text"))
                    .text(rndQuestion.getString("topic"))
                    .build();
        } catch (SQLException e) {
            throw new SqlGetRndQuestionException(e.getMessage());
        }
    }


}
