package com.project.midterm.main;

import java.util.Scanner;

/**
 * Spring Context 객체 사용해야 함
 */

public class main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        printMenu();
        System.out.print("몇 번을 선택하시겠습니까? : ");
        int number = sc.nextInt();

        menuSelect(number);
    }

    private static void menuSelect(int number) {
        switch (number){
            case 1:
                System.out.println("===================================");
                System.out.println("회원가입을 선택하셨습니다.");
                System.out.println("===================================");
                break;
            case 2:
                System.out.println("===================================");
                System.out.println("로그인을 선택하셨습니다.");
                System.out.println("===================================");
                break;
            case 3:
                System.out.println("===================================");
                System.out.println("상품구매를 선택하셨습니다.");
                System.out.println("===================================");
                break;
            case 4:
                System.out.println("===================================");
                System.out.println("구매내역 확인을 선택하셨습니다.");
                System.out.println("===================================");
                break;
            case 5:
                System.out.println("===================================");
                System.out.println("재고관리를 선택하셨습니다.");
                System.out.println("===================================");
                break;
            case 6:
                System.out.println("===================================");
                System.out.println("통계확인을 선택하셨습니다.");
                System.out.println("===================================");
                break;
            default:
                break;
        }
    }

    private static void printMenu(){
        System.out.println("===================================");
        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        System.out.println("3. 상품구매");
        System.out.println("4. 구매내역 확인");
        System.out.println("5. 재고관리");
        System.out.println("6. 통계확인");
        System.out.println("===================================");
    }

}
