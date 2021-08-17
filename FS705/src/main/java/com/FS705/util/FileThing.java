package com.FS705.util;

import java.io.File;

public class FileThing {
	public void fileDelete(String path, String fileName) {
		System.out.println(path);
		System.out.println(fileName);
		
		File file = new File(path + fileName);
		
		if(file.exists()) {//파일이 있으면			
			if(file.delete()) {
				System.out.println("성공");
			} else {
				System.out.println("문제발생");//파일을 사용중이면
			}
			
		} else {
			System.out.println("파일이 없음.");
		}
	}
}
