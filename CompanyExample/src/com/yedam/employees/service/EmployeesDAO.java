package com.yedam.employees.service;

import java.util.ArrayList;
import java.util.List;

import com.yedam.common.DAO;

public class EmployeesDAO extends DAO{
	
	private static EmployeesDAO employeesDao = null;
	
	private EmployeesDAO(){
		
	}
	
	public static EmployeesDAO getinstance() {
		if(employeesDao==null) {
			employeesDao = new EmployeesDAO();
		}
		return employeesDao;
	}
	
	//모든 사원 조회
	public List<Employees> getEmployeesList(){
		List<Employees> list = new ArrayList<>();
		Employees employees=null;
		try {
			conn();
			String sql = "select * from emp";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				employees = new Employees();
				employees.setEmployeeID(rs.getInt("employee_id"));
				employees.setFirstName(rs.getString("first_name"));
				employees.setLastName(rs.getString("last_name"));
				employees.setEmail(rs.getNString("email"));
				employees.setPhoneNumber(rs.getString("phone_number"));
				employees.setHireDate(rs.getDate("hire_date"));
				employees.setJobId(rs.getString("job_ID"));
				employees.setSalary(rs.getInt("salary"));
				employees.setCommissionPct(rs.getDouble("commission_pct"));
				employees.setManagerId(rs.getInt("manager_id"));
				employees.setDepartmentId(rs.getInt("department_id"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return list;
	}
	
}
