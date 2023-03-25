package main;

import model.Question;
import repository.ConnectionSingleton;
import repository.QuestionRepositoryImp;
import service.QuestionService;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MainClass {
    private static final QuestionService questionService = new QuestionService(new QuestionRepositoryImp(ConnectionSingleton.getConnection()));
    public static void main(String[] args) {

        boolean working = true;
        System.out.println("Hello, this is questionarium, let's get started.");

        Scanner scanner = new Scanner(System.in);
        while (working) {
            System.out.println("Please select an available option: \n" +
                    "1. Get random question by topic. \n" +
                    "2. Get all question by topic. \n" +
                    "3. Remove question \n" +
                    "4. Add question \n" +
                    "5. Get random question \n" +
                    "(Input only number of option)");
            int option = scanner.nextInt();
            scanner.nextLine();
            String topic;
            switch (option) {
                case 1:
                    availableTopics();
                    topic = scanner.nextLine();
                    Question rndQuestion = questionService.getRndQuestionByTopic(topic);
                    System.out.println(rndQuestion);
                    break;
                case 2:
                    availableTopics();
                    String topicToFind = scanner.nextLine();
                    scanner.reset();
                    List<Question> questions = questionService.getAllQuestionByTopic(topicToFind);
                   questions.forEach(System.out::println);
                    break;
                case 3:
                    availableTopics();
                    topic = scanner.nextLine();

                    System.out.println("Please input question id");
                    System.out.println("Available question:");

                    List<Question> allQuestionByTopic = questionService.getAllQuestionByTopic(topic);
                    allQuestionByTopic.forEach(System.out::println);

                    questionService.removeQuestion(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 4:
                    System.out.println("Please input topic");
                    topic = scanner.nextLine();
                    System.out.println("Please input question");
                    String questionText = scanner.nextLine();
                    questionService.addQuestion(topic, questionText);
                    break;
                case 5:
                    System.out.println(questionService.getRndQuestion());
                    break;
            }
            System.out.println("Do you want continue? y/n");
            if (scanner.nextLine().equals("n")) {
                working = false;
            }
        }
    }


    public static void availableTopics(){
        System.out.println("Please input topic name");
        System.out.println("Available topics:");

        Set<String> allTopics = questionService.getAllTopics();
        allTopics.forEach(System.out::println);
    }
}
