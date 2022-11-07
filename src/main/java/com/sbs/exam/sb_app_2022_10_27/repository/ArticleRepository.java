package com.sbs.exam.sb_app_2022_10_27.repository;

import com.sbs.exam.sb_app_2022_10_27.vo.Article;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ArticleRepository {

  @Insert("""
          INSERT INTO article
          SET regDate = NOW(),
          updateDate = NOW(),
          memberId = #{memberId},
          title = #{title},
          `body` = #{body}
          """)
  public void writeArticle(@Param("memberId") int memberId, @Param("title") String title, @Param("body") String body);

  @Select("""
          SELECT *
          FROM article
          WHERE id = #{id}
          """)
  public Article getArticle(@Param("id") int id);

  @Delete("""
          DELETE FROM article
          WHERE id = #{id}
          """)
  public void deleteArticle(@Param("id") int id);

  @Select("""
          SELECT A.*,
          M.nickname AS extra_writerName
          FROM article AS A
          LEFT JOIN member AS M
          ON A.memberId = M.id
          ORDER BY A.id DESC
          """)
  public List<Article> getArticles();

  @Update("""
          <script>
          UPDATE article
          <set>
            <if test='title != null'>
              title = #{title},
            </if>
            <if test='body != null'>
              `body` = #{body},
            </if>
            updateDate = NOW()
          </set>
          WHERE id = #{id}
          </script>
          """)
  public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

  @Select("SELECT LAST_INSERT_ID()")
  public int getLastInsertId();
}