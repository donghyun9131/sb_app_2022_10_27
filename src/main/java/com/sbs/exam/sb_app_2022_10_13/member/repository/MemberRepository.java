package com.sbs.exam.sb_app_2022_10_13.member.repository;

import com.sbs.exam.sb_app_2022_10_13.member.vo.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberRepository {

  @Insert("""                                      
          INSERT INTO `member`
          SET regDate = NOW(),
          updateDate = NOW(),
          loginId = #{loginId},
          loginPw = #{loginPw},
          `name` = #{name},
          `nickname` = #{nickname},
          cellphoneNo = #{cellphoneNo},
          email = #{email}
          """)
          // 동적 SQL
  public void join(@Param("loginId") String loginId, @Param("loginPw") String loginPw, @Param("name") String name, @Param("nickname") String nickname,
                   @Param("cellphoneNo") String cellphoneNo, @Param("email") String email);

  @Select("""
          SELECT LAST_INSERT_ID()
          """)
  int getLastInsertId();

  @Select("""
          SELECT *
          FROM `member` AS M
          WHERE M.id = #{id}
          """)
  Member getMemberById(@Param("id") int id);

  @Select("""
          SELECT *
          FROM `member` AS M
          WHERE M.loginId = #{loginId}
          """)
  Member getMemberByloginId(@Param("loginId") String loginId);

  @Select("""
          SELECT *
          FROM `member` AS M
          WHERE M.name = #{name}
          AND M.email = #{email}
          """)
  Member getMemberByNameAndEmail(@Param("name") String name, @Param("email") String email);
}
