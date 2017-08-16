package org.openbox.sf5.json.service;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

/*
 *  Based on http://websystique.com/springmvc/spring-mvc-4-file-upload-example-using-multipartconfigelement/
 */

@Component
public class FileValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return FileBucket.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
        Optional.ofNullable(obj).map(FileBucket.class::cast).map(FileBucket::getFile).map(MultipartFile::getSize)
                .filter(size -> size.equals(new Long(0))).ifPresent(size -> errors.rejectValue("file", "missing.file"));
	}
}
