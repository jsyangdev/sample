package com.example.springmall.sample.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class FileDownloadView extends AbstractView {
	/*
	 * 전체적인 로직은 파일 다운로드 URL 요청 시,
	 * 1. 해당 파일을 찾아 JSP 페이지가 아닌 Class 로 구현된 View 로 연결하여
	 * 2. Stream으로 요청한 Client 쪽으로 전달합니다.
	 */
	
    public FileDownloadView(){

        //content type을 지정. 
        setContentType("apllication/download; charset=utf-8");

    }

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		File file = (File) model.get("downloadFile");

		response.setContentType(getContentType());
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(file.getName(), "utf-8") + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
        
        OutputStream out = response.getOutputStream();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if(fis != null) {
                try { 
                    fis.close(); 
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        out.flush();

	}

}
