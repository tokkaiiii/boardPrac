package board.mvc.model;

import static board.mvc.fileset.Path.FILE_STORE;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import mvc.domain.Board;

public class BoardService {

  BoardDAO dao;

  private static final BoardService instance = new BoardService();

  private BoardService() {
    dao = new BoardDAO();
  }

  public static BoardService getInstance() {return instance;}


  public ArrayList<Board> listS() {return dao.list();}

  public ArrayList<Board> searchTitle(String title) {return dao.searchTitle(title);}

  public int insertS(Board board) {return dao.insert(board);}

  public int updateS(Board board) {return dao.update(board);}

  public int selectS(Board board) {return dao.select(board);}

  public int deleteS(Board board) {return dao.delete(board);}


  public String nicknameS(String id) {return dao.nickname(id);}

  public Board selectCntS(int seq){return dao.selectCnt(seq);}

  public int totalS(){return dao.total();}

  public boolean saveFile(Part filePart){
    String fileName = filePart.getSubmittedFileName();
    String uploadPath = FILE_STORE;
    File uploadDir = new File(uploadPath);
    if(!uploadDir.exists()){
      uploadDir.mkdirs();
    }
    File saveFile = new File(uploadDir, fileName);
    try(InputStream in = filePart.getInputStream();
        FileOutputStream fos = new FileOutputStream(saveFile)){
      byte[] buffer = new byte[1024];
      int len;
      while((len = in.read(buffer)) != -1){
        fos.write(buffer, 0, len);
      }
      fos.flush();
      return true;
    }catch (IOException e){
      return false;
    }
  }
  public void download(HttpServletResponse res, HttpServletRequest req, File file) {
    try {
      String fname = file.getName();
      res.setContentType("application/octet-stream");
      String Agent=req.getHeader("USER-AGENT");
      if( Agent.indexOf("MSIE") >= 0 ){
        int i = Agent.indexOf( 'M', 2 );
        String IEV = Agent.substring( i + 5, i + 8 );
        if( IEV.equalsIgnoreCase("5.5") ){
          res.setHeader("Content-Disposition", "filename=" + new String( fname.getBytes("utf-8"), "8859_1") );
        }else{
          res.setHeader("Content-Disposition", "attachment;filename="+new String(fname.getBytes("utf-8"),"8859_1"));
        }
      }else{
        res.setHeader("Content-Disposition", "attachment;filename=" + new String( fname.getBytes("utf-8"), "8859_1") );
      }
      if(file.exists() && file.isFile()){
      try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file),2048);
        BufferedOutputStream bos = new BufferedOutputStream(res.getOutputStream(),2048)
      ){
        int count = 0;
        byte[] buffer = new byte[2048];
        while((count = bis.read(buffer)) != -1){
          bos.write(buffer, 0, count);
        }
        bos.flush();
      }catch (IOException e){}
      }
    }catch(Exception e){}
  }
  public int view(int seq){return dao.view(seq);}
  public int viewconutS(int seq){return dao.viewcount(seq);}
}