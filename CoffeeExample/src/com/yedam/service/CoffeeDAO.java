package com.yedam.service;

import java.util.ArrayList;
import java.util.List;

import com.yedam.common.DAO;

public class CoffeeDAO extends DAO{
	//coffee테이블에 접근하는 객체
	
	//JAVA -> JDBC -> DB -> coffee table에 접근 -> 데이터 조회 -> CRUD발생
	
	//SingleTon
	//coffee테이블에 한명만 접근할 수 있게 해당 패턴 적용
	private static CoffeeDAO coffeeDao= null;
	
	private CoffeeDAO() {
		
	}
	
	//싱글톤 -> static영역에 생성 -> coffee테이블에 접근하는 경우, 안하는 경우
	//만약 coffee테이블에 CRUD가 발생하지 않는다면 굳이 데이터 소모할 필요 x
	//즉 coffeeDao 객체 생성 할 필요가 없음
	//따라서 null 처리해둔다
	public static CoffeeDAO getiInstance() {
		if(coffeeDao == null) {
			coffeeDao = new CoffeeDAO();
		}
		return coffeeDao;
	}
	
	//coffee 테이블에서 어떠한 CRUD할 껀지에 대해서 정의
	//구현 중인 프로그램에서 어떠한 CRUD가 필요한지 ▲
	//CRUD하려는 것[ExeApp]
	//전체 메뉴 조회(R), 상세[단건≒한건] 조회(R), 등록(C), 판매(U), 삭제(D), 매출(U)
	//List<Student> stdList = new ArrayListm<>(); student객체들 하나씩 넣겠다
	//즉, 객체로 만든 배열이다//type = student
	
	
	//상세[단건≒한건] 조회(R)
	 public Coffee getCoffee(String name) {
		 Coffee coffee = null;
		 try {
			 conn();
			 //preparedstatment 경우
			 String sql = "select * from coffee where coffee_menu = ?";
			 //?<<pstmt.setString(1 첫번째 등장, 이름);
			 pstmt = conn.prepareStatement(sql);//쿼리문 가지고 DB연결
			 pstmt.setString(1, name);//가지고간 쿼리문을 보충해주고 실행
			 
			 //쿼리문 실행
			 rs=pstmt.executeQuery();//select문  -> 반환값(결과값) : ResultSet
			 
			 //조회된 데이터가 있는지 확인하고 데이터를 coffee객체에 입력			 			 
			 if(rs.next()) {//next;결과가 존재하는지 물어보는 구문
				 coffee = new Coffee();
				 coffee.setCoffeeMenu(rs.getString("coffee_menu"));
				 coffee.setCoffeePrice(rs.getInt("coffee_price"));
				 coffee.setCoffeeExplain(rs.getString("coffee_explain"));
				 coffee.setCoffeeSales(rs.getInt("coffee_sales"));
			 }
			 //DB입장과 자바 입장의 차이
			 //자바:ResultSet->rs.next()->>데이터가 존재하냐->있으면 true->없으면 flase
			 //즉 .next = boolean형태
			 //if구문 next()=false일 경우 else if구문추가해줘도 좋을듯?
			 
			 //resultSet의 개념에 대해 찾아보기
			 
			 //
			 
			 //statement 경우
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 } finally {
			 disconn();
		 }
		 return coffee;
	 }
	
	//전체 메뉴 조회(R)
	//전체 조회 -> 조건에 맞은 데이터를 모두 Reading
	 //EX>select * from employees; 
	 //list...
	 
	 public List<Coffee> getCoffeeList(){
		 //coffee객체를 list에 담는 용도
		 List<Coffee> list = new ArrayList<>();
		 //db에서 하나의 row에 대한 데이터를 담는 용도
		 Coffee coffee=null;
		 
		 try {
			 conn();
			 String sql = "select * from coffee";
			 
			 
			 //1. statement - 조건 없는 select 문에서 쓰면 편함
			 //String sql = "select * from coffee where coffee_id = ' "+ coffeeID + "' And coffee_name ='"+coffeeName+"'";
			 //statement 사용하기 위해서 연결
			 stmt = conn.createStatement();
			 
			 //query문 실행 및 결과 반환
			 rs=stmt.executeQuery(sql);
			 
			 //next() 메소드 활용해서 쿼리문 조회 결과 확인
			 while(rs.next()) {
				 //db에 데이터가 한건 이상 조회 되었다.
				 
				 //서로 다른 row데이터를 서로 다른 객체(주소)에 담아주기 위함. garbage되나?
				 coffee = new Coffee();
				 
				 coffee.setCoffeeMenu(rs.getString("coffee_menu"));
				 coffee.setCoffeePrice(rs.getInt("coffee_price"));
				 coffee.setCoffeeExplain(rs.getString("coffee_explain"));
				 coffee.setCoffeeSales(rs.getInt("coffee_sales"));
				 coffee.setCoffeeId(rs.getInt("coffee_id"));
			 
				 list.add(coffee);
			 }
			 		 
			 //2. preparedstatemnet  - where에 변동되는 데이터 입력 할 떄 편함.
			 //String sql = "select * from coffee where coffee_id = ?";
			 
		 }catch ( Exception e) {
			 e.printStackTrace();
		 }finally {
			 disconn();
		 }
		 return list;
	 }
	 
	 //등록(C)
	 
	 public int insertCoffee(Coffee coffee) {
		 int result = 0;
		 try {
			 conn();
			 //preparedstatement
			 String sql = "insert into coffee values(?,?,?,?,?)";
			 pstmt = conn.prepareStatement(sql);
			 
			 pstmt.setInt(1, coffee.getCoffeeId());
			 pstmt.setString(2, coffee.getCoffeeMenu());
			 pstmt.setInt(3, coffee.getCoffeePrice());
			 pstmt.setString(4, coffee.getCoffeeExplain());
			 pstmt.setInt(5, coffee.getCoffeeSales());
			 
			 //DML 사용할때 쓰는 query 메소드 : executeUpdate()
			 //select -> executeQuery();
			 result = pstmt.executeUpdate();
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }finally {
			 disconn();
		 }
		 return result;
	 }
	 
	 //삭제(D)
	 public int menuDelete(String menu) {
		 int result = 0;
		 try {
			 conn();
			 String sql = "delete from coffee where coffee_menu = ?";
			 pstmt=conn.prepareStatement(sql);
			 pstmt.setString(1,menu);
			 
			 result = pstmt.executeUpdate();
		 }catch(Exception e) {
			 e.printStackTrace();
		 }finally{
			 disconn();
		 }
		 return result;
	 }
	 
	 //판매(U)
	 public int salesCoffee(Coffee coffee) {
		 int result = 0;
		 try {
			 conn();
			 String sql = "update coffee"
					 +" set coffee_sales = coffee_sales + ?"
					 +" where coffee_menu = ?";
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setInt(1, coffee.getCoffeeSales());
			 pstmt.setString(2, coffee.getCoffeeMenu());
			 
			 result = pstmt.executeUpdate();
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }finally {
			 disconn();
		 }
		 return result;
	 }
	 
}