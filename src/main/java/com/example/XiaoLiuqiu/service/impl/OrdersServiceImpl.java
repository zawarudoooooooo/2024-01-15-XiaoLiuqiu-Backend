package com.example.XiaoLiuqiu.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.XiaoLiuqiu.constants.RtnCode;
import com.example.XiaoLiuqiu.entity.Extra;
import com.example.XiaoLiuqiu.entity.Member;
import com.example.XiaoLiuqiu.entity.Orders;
import com.example.XiaoLiuqiu.entity.Room;
import com.example.XiaoLiuqiu.repository.MemberDAO;
import com.example.XiaoLiuqiu.repository.OrdersDAO;
import com.example.XiaoLiuqiu.repository.RoomDAO;
import com.example.XiaoLiuqiu.service.ifs.OrdersService;
import com.example.XiaoLiuqiu.vo.OrdersGetRes;
import com.example.XiaoLiuqiu.vo.OrdersRes;
import com.example.XiaoLiuqiu.vo.RoomVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrdersServiceImpl implements OrdersService {

//	�r��P����(���O)����
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private OrdersDAO orderDao;

	@Autowired
	private MemberDAO memberDao;
	
	@Autowired
	private RoomDAO roomDao;

	@Autowired
	private JavaMailSender emailSender;

	@Override
	public OrdersRes search(String memberName, LocalDate startDate, LocalDate endDate) {
		memberName = !StringUtils.hasText(memberName) ? "" : memberName;
		startDate = startDate == null ? startDate = LocalDate.of(1970, 01, 01) : startDate;
		endDate = endDate == null ? endDate = LocalDate.of(2099, 12, 31) : endDate;
		List<Orders> res = orderDao.findByMemberNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
				memberName, startDate, endDate);
		return new OrdersGetRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(), res);
	}

	@Override

	public OrdersRes ordersCreate(String memberName, List<Room> roomIdStr, List<Extra> orderItemStr,
			LocalDate startDate, LocalDate endDate, boolean orderPayment, boolean payOrNot) {
		if (!StringUtils.hasText(memberName) || startDate == null || endDate == null) {
			return new OrdersRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		if (startDate.isAfter(endDate)) {
			return new OrdersRes(RtnCode.DATE_FORMAT_ERROR.getCode(), RtnCode.DATE_FORMAT_ERROR.getMessage());

		}
		try {
			String roomId = mapper.writeValueAsString(roomIdStr);
			String orderItem = mapper.writeValueAsString(orderItemStr);
			Orders newOrder = new Orders(memberName, roomId, orderItem, startDate, endDate, LocalDateTime.now(),
					orderPayment, payOrNot);
			orderDao.save(newOrder);
			// ���\�إ߭q���o�e�l��q���U��
			sendOrderConfirmationEmail(newOrder);
			return new OrdersRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage());
		} catch (JsonProcessingException e) {
			return new OrdersRes(RtnCode.ORDER_CREATE_ERROR.getCode(), RtnCode.ORDER_CREATE_ERROR.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private void sendOrderConfirmationEmail(Orders order) {

		Member member = memberDao.findByMemberName(order.getMemberName()).orElse(null);

		if (member != null) {
			ObjectMapper mapper = new ObjectMapper();
			String orderItemStr = order.getOrderItem();
			try {
				StringBuffer buf = new StringBuffer();
				StringBuffer buff = new StringBuffer();
				List<Map<String, Object>> list = mapper.readValue(orderItemStr, List.class);
				for(Map<String, Object> listItem : list) {
					int count = 0;
					for(Entry<String, Object> mapItem : listItem.entrySet()) {				
						if (mapItem.getKey().equalsIgnoreCase("extraId")) {
							continue;
						}
						buf.append(mapItem.getValue());
						count++;
						if (count % 2 != 0) {
							buf.append(": ");
						} else {
							buf.append(";\t");
						}
					}
				}
				String roomStr = order.getRoomId();
					roomStr = roomStr.replace("roomId", "�ж��s��").replace("roomName", "�Ы�");
					list = mapper.readValue(roomStr, List.class);
					for(Map<String, Object> item : list) {
						for(Entry<String, Object> mapItem : item.entrySet()) {
							if(mapItem.getKey().equalsIgnoreCase("�ж��s��") 
									|| mapItem.getKey().equalsIgnoreCase("�Ы�")) {
								buff.append(" " + mapItem.getKey()).append(": ").append(mapItem.getValue()).append("; ");
							}
						}
					}
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
				String to = member.getMemberEmail();
				String subject = "�q�榨�߳q��";
				String text = "�P�±z���q��!\n" + "�z���q��s���� : " + order.getOrderId() + "; \n"
				               + "�ж���T : " + buff.toString() + "\n" + "�[�ʶ��� : " + buf.toString() + "\n"
						       + "�q���� : " + order.getOrderDateTime().format(formatter) + ";";

				SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(to);
				message.setSubject(subject);
				message.setText(text);
				emailSender.send(message);
			} catch (Exception e) {
				System.out.printf("Error sending order confirmation email", e);
			};
		} else {
			System.out.printf("Member not found for order: {}", order.getOrderId());
		}

	}

	@Override
	public OrdersRes searchRoomId(String memberName) {
		
		memberName = !StringUtils.hasText(memberName) ? "" : memberName;
		
		Orders order=new Orders();
		Optional<Orders> op = orderDao.findByName(memberName);
		
		order=op.get();
		StringBuffer buff = new StringBuffer();
		String roomStr = order.getRoomId();
		List<Map<String, Object>> list;
		try {
			list = mapper.readValue(roomStr, List.class);
			for(Map<String, Object> item : list) {
				buff.setLength(0);
				for(Entry<String, Object> mapItem : item.entrySet()) {
					if(mapItem.getKey().equalsIgnoreCase("roomId")) {
						buff.append(mapItem.getValue());
						System.out.println(buff.toString());
						Optional<Room> roomRes=roomDao.findById(buff.toString());
						Room room=roomRes.get();
						
//						System.out.println(res);
					}
				}
			}
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			 System.err.println("Error processing JSON: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
		
		
		
		
		
	}
	
//	@Override
//	public OrdersRes search(String memberName, LocalDate startDate, LocalDate endDate) {
//		memberName = !StringUtils.hasText(memberName) ? "" : memberName;
//		startDate = startDate == null ? startDate = LocalDate.of(1970, 01, 01) : startDate;
//		endDate = endDate == null ? endDate = LocalDate.of(2099, 12, 31) : endDate;
//		List<Orders> res = orderDao.findByMemberNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
//				memberName, startDate, endDate);
//		return new OrdersGetRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(), res);
//	}



}
