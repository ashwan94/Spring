package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class App {
    private static ApplicationContext context;
    public static void main(String[] args) {
//        context = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        context = new ClassPathXmlApplicationContext("org-context.xml");

        // 사용자가 입력한 정보로 메뉴 실행
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("명령어를 입력하세요: ");
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("exit")) {
                System.out.println("종료합니다.");
                break;
            }
            if (command.startsWith("new ")) {
                processNewCommand(command.split(" "));
                continue;
            } else if (command.startsWith("change ")) {
                processChangeCommand(command.split(" "));
                continue;
            } else if (command.equalsIgnoreCase("list")) {
                processListCommand();
                continue;
            } else if (command.startsWith("info ")) {
                processInfoCommand(command.split(" "));
                continue;
            } else if (command.equalsIgnoreCase("version")) {
                processVersionCommand();
                continue;
            }
            printHelp();
        }
    }

    private static void processInfoCommand(String[] args) {
        if (args.length != 2) {
            printHelp();
            return;
        }
        MemberInfoPrinter memberInfoPrinter = context.getBean("memberInfoPrinter", MemberInfoPrinter.class);
        memberInfoPrinter.printMemberInfo(args[1]);
    }

    private static void processVersionCommand() {
        VersionPrinter versionPrinter = context.getBean("versionPrinter", VersionPrinter.class);
        versionPrinter.setMajorVersion(10);
        versionPrinter.print();
    }

    private static void processNewCommand(String[] args) {
        if (args.length != 5) {
            printHelp();
            return;
        }
        MemberRegisterService registerService = context.getBean("memberRegisterService", MemberRegisterService.class);
//        MemberRegisterService registerService = new MemberRegisterService(null);
        RegisterRequest request = new RegisterRequest();
        request.setEmail(args[1]);
        request.setName(args[2]);
        request.setPassword(args[3]);
        request.setConfirmPassword(args[4]);
        if (!request.isPasswordEqualToConfirmPassword()) {
            System.out.println("암호와 암호확인이 일치하지 않습니다.");
            return;
        }
        try {
            registerService.regist(request);
            System.out.println("등록했습니다.\n");
        } catch (DuplicateMemberException e) {
            System.out.println("이미 존재하는 이메일입니다.\n");
        }

    }
    private static void processChangeCommand(String[] args) {
        if (args.length != 4) {
            printHelp();
            return;
        }
        ChangePasswordService passwordService = context.getBean("passwordService", ChangePasswordService.class);
        try {
            passwordService.changePassword(args[1], args[2], args[3]);
            System.out.println("암호를 변경했습니다.\n");
        } catch (MemberNotFoundException e) {
            System.out.println("존재하지 않는 이메일입니다.\n");
        } catch (WrongIdPasswordException e) {
            System.out.println("이메일과 암호가 일치하지 않습니다.\n");
        }
    }
    private static void processListCommand() {
        MemberListPrinter listPrinter = context.getBean("memberListPrinter", MemberListPrinter.class);
        listPrinter.printAll();
    }
    private static void printHelp() {
        System.out.println();
        System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
        System.out.println("명령어 사용법: ");
        System.out.println("new 이메일 이름 암호 암호확인");
        System.out.println("change 이메일 현재비번 변경비번");
        System.out.println();
    }
}
