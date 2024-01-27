package com.example.XiaoLiuqiu.repository;



import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.XiaoLiuqiu.entity.Room;

@Repository
public interface RoomDAO extends JpaRepository<Room, String>{

	public List<Room> findByRoomNameContaining(String roomName);
	
	@Query(value="select * from room where room_name like %:roomName%",nativeQuery = true)
	public List<Room> findByRoomName(@Param("roomName")String roomName);
	
	public boolean  existsByRoomId(String roomId);
	
	@Transactional(rollbackOn = Exception.class)
	@Modifying
	@Query(value = "insert into room(room_id, room_introduce, room_name, room_price, is_open, room_img)"
			+ "select :room_id, :room_introduce, :room_name, :room_price, :is_open, :room_img "
			+ "where not exists (select 1 from room where room_id = :room_id)",		
	nativeQuery = true)
	public int insertRoom(//
			@Param("room_id")String roomId,//
			@Param("room_introduce")String roomIntroduce,//
			@Param("room_name")String roomName,//
			@Param("room_price")int roomPrice,//
			@Param("is_open")boolean isOpen,//
			@Param("room_img") String roomImg);
	
	//�٬O�i�H�ΡA���O�@�뤣��ĳ��PK�i�]�w
	//�P�_�ƭȡB���L�ȥ� CASE WHEN
	//COALESCE()�Y���ONULL�^��NULL�Aint boolean�Y�ϥη|�P���榨�G�ۮ��A��άO�g���j�gInteger�BBoolean�K�i�ϥ�
	@Transactional(rollbackOn = Exception.class)
	@Modifying(clearAutomatically = true)
	@Query(value = "update room set room_introduce = ?2, room_name = ?3, room_price = ?4, is_open=?5, room_img=?6 "
			+ " where room_id = ?1", nativeQuery = true)
	public int updateRoom(String roomId, String roomIntroduce, String roomName, int roomPrice, boolean isOpen, String roomImg);
	
}
