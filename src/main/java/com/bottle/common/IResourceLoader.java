package com.bottle.common;

import java.io.File;

public interface IResourceLoader {
	File loadResourceAsFile(final String relativePath);
}
