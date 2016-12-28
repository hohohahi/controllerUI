package com.bottle.common;

import java.io.File;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class ResourceLoader extends AbstractBaseBean implements IResourceLoader {
	@Override
	public File loadResourceAsFile(final String filename) {
		final String relativePath = "resources/images/" + filename;
    	File resourceFile = new File(relativePath);
    	if (false == resourceFile.exists()) {
    		URL pathURL = ClassLoader.getSystemResource("");
    		
    		File classPath = new File(pathURL.getFile());
    		String fullPath = classPath.getParent() + File.separator + relativePath;
    		resourceFile = new File(fullPath);
    	}
    	
    	return resourceFile;
	}
}
