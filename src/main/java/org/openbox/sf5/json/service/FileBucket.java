package org.openbox.sf5.json.service;

import org.springframework.web.multipart.MultipartFile;

/*
 *  Based on http://websystique.com/springmvc/spring-mvc-4-file-upload-example-using-multipartconfigelement/
 */

public class FileBucket {

	MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
