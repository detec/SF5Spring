package org.openbox.sf5.application;

import javax.servlet.annotation.MultipartConfig;

import org.openbox.sf5.common.IniReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100)
public class FileUploadController {

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String provideUploadInfo() {
		return "importtransponders";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				iniReader.readMultiPartFile(file);

				return "redirect:/transponders";
			} catch (Exception e) {
				return "Failed to upload " + e.getMessage();
			}
		} else {
			return "redirect:/transponders";
		}

	}

	@Autowired
	private IniReader iniReader;

}