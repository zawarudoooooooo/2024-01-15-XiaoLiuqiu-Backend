package com.example.XiaoLiuqiu;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.XiaoLiuqiu.constants.Department;
import com.example.XiaoLiuqiu.constants.EmployeeRole;
import com.example.XiaoLiuqiu.constants.RtnCode;
import com.example.XiaoLiuqiu.entity.Employee;
import com.example.XiaoLiuqiu.repository.EmployeeDAO;
import com.example.XiaoLiuqiu.service.impl.EmployeeServiceImpl;
import com.example.XiaoLiuqiu.vo.EmployeeLoginRes;

@SpringBootTest
public class EmployeeServiceTest {
	
//	@Autowired
//	private EmployeeDAO employeeDao;
//	
//	@Test
//	public void testCreateEmployeeAsTopManagement() {
//		// �ǳƴ��ռƾ�
//		String account = "john.doe";
//		String password = "password123";
//	    Department department = Department.HR;
//	    boolean isSupervisor = true;
//	    EmployeeRole role = EmployeeRole.ADMINISTRATIVE_SUPERVISOR;
//
//	    // �����@�ӵn�J���̰��޲z��
//	    Employee loggedInEmployee = mock(Employee.class);
//	    when(loggedInEmployee.isTopManagement()).thenReturn(true);
//
//	    // ���� EmployeeDao
//	    EmployeeDAO employeeDao = mock(EmployeeDAO.class);
//
//	    // ���� EmployeeServiceImpl
//	    EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
//	    employeeService.setEmployeeDao(employeeDao);
//
//	    // �]�m�������n�J���u
//	    employeeService.setLoggedInEmployee(loggedInEmployee);
//
//	    // �I�s���ժ���k
//	    EmployeeLoginRes result = employeeService.create(account, password, department, isSupervisor, role);
//
//	    // ���ҵ��G
//	    assertEquals(RtnCode.SUCCESSFUL, result.getCode());
//
//	    // ���ҦbDao���O�_���T�O�s
//	    // �b�o�̧A�i�H�ϥμ����� EmployeeDao �����ҬO�_�I�s�F��������k
//	    }

}
