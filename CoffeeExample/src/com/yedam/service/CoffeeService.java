package com.yedam.service;

import java.util.List;
import java.util.Scanner;

public class CoffeeService {
	
	Scanner sc = new Scanner(System.in);
	//1. 커피 전메뉴 조회
	public void getCoffeeList() {
		List<Coffee> list = CoffeeDAO.getiInstance().getCoffeeList();
		for(int i = 0; i<list.size();i++) {
			System.out.println("메뉴 : "+list.get(i).getCoffeeMenu());
			System.out.println("가격 : "+list.get(i).getCoffeePrice());
			System.out.println("설명 : "+list.get(i).getCoffeeExplain());
			System.out.println("판매잔수 : "+list.get(i).getCoffeeSales());
		}
	}
	
	//2. 상세 조회
	public void getCoffee() {
		System.out.println("메뉴 입력>");
		String menu = sc.nextLine();
		Coffee coffee = CoffeeDAO.getiInstance().getCoffee(menu);
		if(coffee ==null) {
			System.out.println("등록되지 않은 메뉴 입니다.");
		}else {
			System.out.println("메 뉴 : "+coffee.getCoffeeMenu());
			System.out.println("가 격 : "+coffee.getCoffeePrice());
			System.out.println("설 명 : "+coffee.getCoffeeExplain());
		}
				
	}
	
	//3. 메뉴 등록
	public void insertCoffee() {
	System.out.println("<<메뉴 추가>>");
	Coffee insertCoffee = new Coffee();
	System.out.println("메뉴 번호>");
	int menuNo=Integer.parseInt(sc.nextLine());
	System.out.println("메뉴 이름>");
	String menuName=sc.nextLine();
	System.out.println("메뉴 가격>");
	int menuPrice = Integer.parseInt(sc.nextLine());
	System.out.println("메뉴 설명>");
	String menuExplain = sc.nextLine();
	System.out.println("메뉴 판매량>");
	int menuSales = Integer.parseInt(sc.nextLine());
			
	insertCoffee.setCoffeeId(menuNo);
	insertCoffee.setCoffeeMenu(menuName);
	insertCoffee.setCoffeePrice(menuPrice);
	insertCoffee.setCoffeeExplain(menuExplain);
	insertCoffee.setCoffeeSales(menuSales);
	
	int result = CoffeeDAO.getiInstance().insertCoffee(insertCoffee);
	
	if(result>0) {
		System.out.println("데이터 입력 완료");
	}else {
		System.out.println("데이터 입력 실패");
	}
	}
	//4. 판매
	public void salesCoffee() {
		int result = 0;
		System.out.println("==판매==");
		System.out.println("메뉴>");
		String coffeeMenu = sc.nextLine();
		System.out.println("잔 수>");
		int sales = Integer.parseInt(sc.nextLine());
		
		Coffee updateCoffee = new Coffee();
		updateCoffee.setCoffeeMenu(coffeeMenu);
		updateCoffee.setCoffeeSales(sales);
		
		CoffeeDAO.getiInstance().salesCoffee(updateCoffee);
		
		if(result>0) {
			System.out.println("데이터 수정 완료");
		}else {
			System.out.println("데이터 수정 실패");
		}

	}
	
	//5. 삭제
	public void deleteMenu() {
		int result = 0;
		System.out.println("지울 메뉴 입력>");
		String deleteMenu=sc.nextLine();
		result = CoffeeDAO.getiInstance().menuDelete(deleteMenu);
		
		if(result>0) {
			System.out.println("데이터 삭제 완료");
		}else {
			System.out.println("데이터 삭제 실패");
		}
		
		
	}
	//6. 매출
	public void storeSales() {
		System.out.println("===또치's 커피샵 매출 내역서===");
		List<Coffee> list = CoffeeDAO.getiInstance().getCoffeeList();
		int totalSales = 0;
		System.out.println("##################################");
		for(Coffee coffee : list) {
			System.out.println("메 뉴 : "+coffee.getCoffeeMenu());
			System.out.println("가 격 : "+coffee.getCoffeePrice());
			System.out.println("판매수량 : "+coffee.getCoffeeSales());
			//판매 금액 = 가격 * 수량
			System.out.println("판매금액 : "+(coffee.getCoffeePrice()*coffee.getCoffeeSales()));
			totalSales+=(coffee.getCoffeePrice()*coffee.getCoffeeSales());
			System.out.println("==========================================");
		}
		System.out.println("총 판매 금액 : "+totalSales+"원");
	}
	
	
	
	
	
}
