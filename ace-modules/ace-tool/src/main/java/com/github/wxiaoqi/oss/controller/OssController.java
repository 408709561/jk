

package com.github.wxiaoqi.oss.controller;

import com.github.wxiaoqi.oss.cloud.OSSFactory;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * 文件上传
 * 
 * @author ace
 */
@RestController
@RequestMapping("/oss")
public class OssController{
	@Autowired
	private OSSFactory ossFactory;
	/**
	 * 上传文件
	 */
	@RequestMapping("/upload")
	public ObjectRestResponse<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new BusinessException("上传文件不能为空");
		}
		//上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = ossFactory.build().uploadSuffix(file.getBytes(), suffix);
		return new ObjectRestResponse<>().data(url);
	}



}
