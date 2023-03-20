package main;

import repository.ConnectionSingelton;
import repository.QuestionRepositoryImp;
import service.QuestionService;

import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        QuestionService questionService = new QuestionService(new QuestionRepositoryImp(ConnectionSingelton.getConnection()));
        System.out.println("Do you want a random question by topic ?????");
        while (scanner.next().equals("yes")){
            System.out.println("Topic name ?");
            System.out.println(questionService.getRndQuestionByTopic(scanner.next()).getText());
            System.out.println("Do you wanna a next question ????");
        }
    }
}
