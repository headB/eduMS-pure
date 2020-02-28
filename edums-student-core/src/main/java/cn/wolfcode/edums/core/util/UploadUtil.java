package cn.wolfcode.edums.core.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传工具
 */
public abstract class UploadUtil {

	/**
	 * 处理文件上传
	 * 
	 * @param file
	 * @param basePath
	 * @param type
	 * @return
	 */
	public static String upload(MultipartFile file, String basePath, String type) {
		String orgFileName = file.getOriginalFilename();
		String fileName = UUID.randomUUID().toString() + "."
				+ FilenameUtils.getExtension(orgFileName);
		try {
			File targetFile = new File(basePath+type, fileName);
			FileUtils.writeByteArrayToFile(targetFile, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/upload/"+type+fileName;
	}
}

