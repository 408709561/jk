/*
 *
 *  *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>
 *
 *  *  AG-Enterprise 企业版源码
 *  *  郑重声明:
 *  *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  *  老A将追究授予人和传播人的法律责任!
 *
 *  *  This program is free software; you can redistribute it and/or modify
 *  *  it under the terms of the GNU General Public License as published by
 *  *  the Free Software Foundation; either version 2 of the License, or
 *  *  (at your option) any later version.
 *
 *  *  This program is distributed in the hope that it will be useful,
 *  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  GNU General Public License for more details.
 *
 *  *  You should have received a copy of the GNU General Public License along
 *  *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package com.github.wxiaoqi.security.wf.activiti.modeler;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class GenericResponseWrapper extends HttpServletResponseWrapper { 
  private ByteArrayOutputStream output;
  private int contentLength;
  private String contentType;

  public GenericResponseWrapper(HttpServletResponse response) { 
    super(response);
    output=new ByteArrayOutputStream();
  } 

  public byte[] getData() { 
    return output.toByteArray(); 
  } 

  public ServletOutputStream getOutputStream() { 
    return new FilterServletOutputStream(output); 
  } 
  
  public PrintWriter getWriter() { 
    return new PrintWriter(getOutputStream(),true); 
  } 

  public void setContentLength(int length) { 
    this.contentLength = length;
    super.setContentLength(length); 
  } 

  public int getContentLength() { 
    return contentLength; 
  } 

  public void setContentType(String type) { 
    this.contentType = type;
    super.setContentType(type); 
  } 

  public String getContentType() { 
    return contentType; 
  } 
} 
