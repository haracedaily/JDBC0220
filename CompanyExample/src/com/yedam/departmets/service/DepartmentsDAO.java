package com.yedam.departmets.service;

import java.util.ArrayList;
import java.util.List;

import com.yedam.common.DAO;

public class DepartmentsDAO extends DAO{
	
	//싱글톤
	private static DepartmentsDAO departDao=null;
	//필드 만드는 이유; 객체로 만들어 다른 곳에서 같은 이름 생성 못하게 하려고;	
	private DepartmentsDAO() {
		
	}
	public static DepartmentsDAO getInstance() {
		if(departDao == null) {
			departDao = new DepartmentsDAO();
		}
		return departDao;
	}

	//모든 부서 조회, 한 부서 조회, 삭제, 수정, 등록
	//명심!! 각 DAO에서 사용하는 쿼리는 반드시 sqlDeveloper에서 실행 해보고 자바에 적용 시킬 것.
	public List<Departments> getDepartmentsList(){
		List<Departments> list = new ArrayList<>();
		Departments depart = null;
		try {
			conn();
			String sql="select * from dept";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				depart = new Departments();
				depart.setDepartmentName(rs.getString("department_Name"));
				depart.setDepartmentId(rs.getInt("department_id"));
				depart.setLocationId(rs.getInt("location_id"));//제약조건 때문에 외래키 적용되서
				depart.setManagerId(rs.getInt("manager_id"));
				
				list.add(depart);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return list;
	}
	
	public Departments getDepartment(int departmentKey) {
		Departments depart = null;
		try {
			conn();
			String sql = "select * from dept where department_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, departmentKey);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				depart = new Departments();
				depart.setDepartmentId(rs.getInt("department_id"));
				depart.setDepartmentName(rs.getString("department_name"));
				depart.setLocationId(rs.getInt("location_id"));
				depart.setManagerId(rs.getInt("manager_id"));
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return depart;
	}
	
	//삭제 수정 등록
	public int deleteDept(Departments d) {
		int result=0;
		try {
			conn();
			String sql = "delete from dept where department_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, d.getDepartmentId());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return result;
	}
	
	//수정
	public int modifyDept(Departments dept) {
		int result =0;
		try {
			conn();
			String sql ="update dept set manager_id = ? where department_id =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dept.getManagerId());
			pstmt.setInt(2, dept.getDepartmentId());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			disconn();
		}
		return result;
	}
	
	//등록
	public int insertDept(Departments dept) {
		int result=0;
		try {
			conn();
			String sql = "insert into dept values(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dept.getDepartmentId());
			pstmt.setString(2, dept.getDepartmentName());
			pstmt.setInt(3, dept.getManagerId());
			pstmt.setInt(4, dept.getLocationId());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return result;
	}
	
}
