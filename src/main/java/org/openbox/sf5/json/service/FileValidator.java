package org.openbox.sf5.json.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
		FileBucket file = (FileBucket) obj;

		if (file.getFile() != null) {
			if (file.getFile().getSize() == 0) {
				errors.rejectValue("file", "missing.file");
			}
		}
	}

}
