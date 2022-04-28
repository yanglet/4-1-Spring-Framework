package com.project.midterm.main;

import com.project.midterm.config.JavaConfig;
import com.project.midterm.entity.Item;
import com.project.midterm.entity.Member;
import com.project.midterm.repository.ItemRepository;
import com.project.midterm.repository.MemberRepository;
import com.project.midterm.repository.OrderRepository;
import com.project.midterm.service.AnalysisService;
import com.project.midterm.service.ItemService;
import com.project.midterm.service.MemberService;
import com.project.midterm.service.OrderService;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Spring Context 객체 사용해야 함
 */

public class main {
    static Member member;
    static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
    static MemberRepository memberRepository = (MemberRepository) context.getBean("memberRepository");
    static ItemRepository itemRepository = (ItemRepository) context.getBean("itemRepository");
    static OrderRepository orderRepository = (OrderRepository) context.getBean("orderRepository");
    static MemberService memberService = (MemberService) context.getBean("memberService");
    static OrderService orderService = (OrderService) context.getBean("orderService");
    static ItemService itemService = (ItemService) context.getBean("itemService");
    static AnalysisService analysisService = (AnalysisService) context.getBean("analysisService");

    public static void main(String[] args) throws IOException, ParseException {
        while(true){
            printMenu();

            int number = scanNumber();

            if(number >= 10000){
                return;
            }

            menuSelect(number);
        }
    }

    private static void menuSelect(int number) throws IOException, ParseException {
        if(number > 2 && number <= 6 && member == null){
            System.out.println("로그인 후 이용해주세요.");
            return;
        }

        switch (number){
            case 1:
                System.out.println("===================================");
                System.out.println("회원가입을 선택하셨습니다.");
                System.out.println("===================================");
                selectJoin();
                break;
            case 2:
                System.out.println("===================================");
                System.out.println("로그인을 선택하셨습니다.");
                System.out.println("===================================");
                selectLogin();
                break;
            case 3:
                System.out.println("===================================");
                System.out.println("상품구매를 선택하셨습니다.");
                System.out.println("===================================");
                selectBuy();
                break;
            case 4:
                System.out.println("===================================");
                System.out.println("구매내역 확인을 선택하셨습니다.");
                System.out.println("===================================");
                selectOrderList();
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
                selectAnalysis();
                break;
            default:
                System.out.println("===================================");
                System.out.println("다시 입력해주세요.");
                System.out.println("===================================");
                selectManagement();
                break;
        }
    }

    private static void selectJoin() throws IOException, ParseException {
        System.out.println("회원 이름, 회원 ID (Email 형식), 회원 PW 를 입력해주세요.");
        System.out.println("띄어쓰기로 구분해서 입력해주시면 됩니다!");

        StringTokenizer st = getStringTokenizer();

        Member newMember = Member.builder()
                .name(st.nextToken())
                .email(st.nextToken())
                .password(st.nextToken())
                .build();

        memberService.join(newMember);

        System.out.println("회원가입이 완료되었습니다!");
    }

    private static void selectLogin() throws IOException, ParseException {
        System.out.println("회원 ID (Email 형식), 회원 PW 를 입력해주세요.");
        System.out.println("띄어쓰기로 구분해서 입력해주시면 됩니다!");

        StringTokenizer st = getStringTokenizer();

        String loginId = st.nextToken();
        String loginPw = st.nextToken();


        Long loginResult = memberService.login(loginId, loginPw);

        if(loginResult == 1L){
            member = memberRepository.findByEmail(loginId);
            System.out.println("로그인이 완료되었습니다!");
            System.out.println("접속한 회원의 이름 : " + member.getName());
        }else{
            System.out.println("로그인에 실패하셨습니다.\n");
        }
    }

    private static void selectBuy() throws IOException, ParseException {
        itemRepository.findAllPrint();

        System.out.println("구매하실 상품 이름, 상품 수량, 지불하실 금액을 입력해주세요.");
        System.out.println("띄어쓰기로 구분해서 입력해주시면 됩니다!");

        StringTokenizer st = getStringTokenizer();

        String itemName = st.nextToken();
        Long itemQuantity = Long.valueOf(st.nextToken());
        Long money = Long.valueOf(st.nextToken());

        orderService.sale(itemRepository.findByName(itemName), member, itemQuantity, money);

        System.out.println("구매해주셔서 감사합니다!\n");
    }

    private static void selectOrderList() throws IOException, ParseException {
        orderRepository.findAll();
    }

    private static void selectManagement() throws IOException, ParseException {
        if( !member.getRole().equals("manager") ){
            System.out.println("권한이 없습니다.\n관리자에게 문의하세요.");
        }

        System.out.println("===================================");
        System.out.println("1. 상품 추가");
        System.out.println("2. 상품 삭제");
        System.out.println("3. 상품 재고 관리");
        System.out.println("4. 상품 목록 확인");
        System.out.println("====================================");
        System.out.println("* 종료를 원하시면 10000을 입력해주세요 *");
        System.out.println("====================================");

        switch ( scanNumber() ){
            case 1:
                System.out.println("추가하실 상품 코드, 상품 이름, 상품 가격, 상품 수량을 입력해주세요.");
                System.out.println("띄어쓰기로 구분해서 입력해주시면 됩니다!");
                StringTokenizer st1 = getStringTokenizer();

                Item newItem = Item.builder()
                        .code(st1.nextToken())
                        .name(st1.nextToken())
                        .price(Long.valueOf(st1.nextToken()))
                        .quantity(Long.valueOf(st1.nextToken()))
                        .build();

                itemRepository.save(newItem);
                System.out.println("상품이 추가되었습니다.");
                System.out.println("상품 이름 : " + newItem.getName() + "\n");
                break;
            case 2:
                System.out.println("삭제하실 상품의 코드를 입력해주세요.");
                itemRepository.delete( new Scanner(System.in).next() );
                System.out.println("상품이 삭제되었습니다.\n");
                break;
            case 3:
                System.out.println("재고 관리할 상품의 코드를 입력해주세요.");
                String code = new Scanner(System.in).next();
                Item findItem = itemRepository.findByCode(code);

                System.out.println("재고 추가하려면 양의 정수, 제거하려면 음의 정수를 입력해주세요.");
                String stockNum = getStringTokenizer().nextToken();

                if( stockNum.charAt(0) == '+' ){
                    itemService.addStock(findItem, Long.valueOf(stockNum));
                    System.out.println("상품이 " + Long.valueOf(stockNum) + "만큼 추가되었습니다.\n");
                }else if( stockNum.charAt(0) == '-' ){
                    itemService.removeStock(findItem, Long.valueOf(stockNum) * -1L);
                    System.out.println("상품이 " + Long.valueOf(stockNum) * -1L + "만큼 제거되었습니다.\n");
                }else{
                    System.out.println("잘못된 입력입니다.\n");
                }
                break;
            case 4:
                itemRepository.findAllPrint();
                break;
            default:
                break;
        }
    }

    private static void selectAnalysis() throws IOException, ParseException {
        if( !member.getRole().equals("manager") ){
            System.out.println("권한이 없습니다.\n관리자에게 문의하세요.");
        }

        System.out.println("===================================");
        System.out.println("1. 하루 판매량");
        System.out.println("2. 일주일 판매량");
        System.out.println("3. 한달 판매량");
        System.out.println("4. 매출액");
        System.out.println("5. 최다 판매 상품");
        System.out.println("====================================");
        System.out.println("* 종료를 원하시면 10000을 입력해주세요 *");
        System.out.println("====================================");

        switch ( scanNumber() ){
            case 1:
                analysisService.printPerDay();
                break;
            case 2:
                analysisService.printPerWeek();
                break;
            case 3:
                analysisService.printPerMonth(0);
                break;
            case 4:
                analysisService.printSales();
                break;
            case 5:
                analysisService.printTopItem();
                break;
            default:
                break;
        }
    }

    private static int scanNumber(){
        Scanner sc = new Scanner(System.in);
        System.out.print("몇 번을 선택하시겠습니까? : ");
        return sc.nextInt();
    }

    private static StringTokenizer getStringTokenizer() {
        Scanner sc = new Scanner(System.in);
        StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");
        return st;
    }

    private static void printMenu(){
        System.out.println("===================================");
        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        System.out.println("3. 상품구매");
        System.out.println("4. 구매내역 확인");
        System.out.println("5. 재고관리");
        System.out.println("6. 통계확인");
        System.out.println("====================================");
        System.out.println("* 종료를 원하시면 10000을 입력해주세요 *");
        System.out.println("====================================");
    }

}
