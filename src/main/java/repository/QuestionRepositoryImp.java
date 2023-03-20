package repository;

import Exceptions.SqlUpdateException;
import model.Question;
import repository.dao.QuestionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepositoryImp implements QuestionRepository {
    private Connection connection;

    public QuestionRepositoryImp(Connection connection) {
        this.connection = ConnectionSingelton.getConnection();
    }

    private final String findById = "SELECT * FROM Questions where id=?";
    private final String getAllQuest = "SELECT * FROM Questions ";
    private final String saveQuestion = "INSERT INTO Questions VALUE (?,?,?)";
    private final String findByTopic = "SELECT * FROM Questions where topic=?";
    private final String updateText = "UPDATE Questions SET text=? where id=?";
    private final String deleteById = "DELETE FROM Questions where id=?";


    @Override
    public Question get(int id) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(findById);
            preparedStatement.setInt(1, id);
            ResultSet question = preparedStatement.executeQuery();
            question.next();
            return Question.builder()
                    .id(question.getInt("id"))
                    .text(question.getString("text"))
                    .topic(question.getString("topic"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Question question) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(saveQuestion);
            preparedStatement.setInt(1, question.getId());
            preparedStatement.setString(2, question.getText());
            preparedStatement.setString(3, question.getTopic());
            if (preparedStatement.executeUpdate() != 1) {
                throw new SQLException("Something went wrong");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Question question) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(updateText);
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
            PreparedStatement preparedStatement = this.connection.prepareStatement(deleteById);
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() != 1) {
                throw new SQLException("Something went wrong");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Question> getByTopic(String topic) {
        List<Question> questionsByTopic = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(findByTopic);
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Question> getAllQuestions() throws SQLException {
        List<Question> allQuestions = new ArrayList<>();
        PreparedStatement preparedStatement = this.connection.prepareStatement(getAllQuest);
        ResultSet question = preparedStatement.executeQuery();
        while (question.next()) {
            allQuestions.add(Question.builder()
                    .id(question.getInt("id"))
                    .text(question.getString("text"))
                    .topic(question.getString("topic"))
                    .build());
        }
        return allQuestions;
    }
}
