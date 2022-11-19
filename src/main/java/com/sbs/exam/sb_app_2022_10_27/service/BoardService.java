package com.sbs.exam.sb_app_2022_10_27.service;
import com.sbs.exam.sb_app_2022_10_27.repository.BoardRepository;
import com.sbs.exam.sb_app_2022_10_27.vo.Board;
import org.springframework.stereotype.Service;


@Service
public class BoardService {

  private BoardRepository boardRepository;

  public BoardService(BoardRepository boardRepository) {           // 생성자 주입
    this.boardRepository = boardRepository;
  }

  public Board getBoardById(int id) {
    return boardRepository.getBoardById(id);
  }
}