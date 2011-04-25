package com.sheng00.epublib.Managers;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.sheng00.epublib.R;


import android.content.Context;
import android.util.Log;



public class ZipManager {
	static final int BUFFER = 2048;
	private PrefsManager prefsManager;
	private Context mContext;
	
	public ZipManager(Context context){
		mContext = context;
		prefsManager = new PrefsManager(mContext);
	}
	
	public void extractZipFile(InputStream is,String extractDir) {
		ZipInputStream zis = new ZipInputStream(is);
		extractZipFile(zis, extractDir);
	}
	
	public void extractZipFile(ZipInputStream zis,String extractDir) {
		File extract = new File(extractDir);
		if(extract.exists()){
			Log.i("ZipManager:extractZipFile", "extract dir exists");
			return;
		}
		try {
			BufferedOutputStream dest = null;
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				if (entry.getName().contains("__MACOSX")) {
					continue;
				}
				File file = new File(extractDir + entry.getName());
				if (file.getName().equals(mContext.getString(R.string.meta_file_name))) {
					prefsManager.prefsSetString(mContext.getString(R.string.meta_file_path), file.getPath());
				}
//				if (file.getName().equals(mContext.getString(R.string.contentFolderName))) {
//					prefsManager.prefsSetString("content_folder_name", file.getPath());
//				}
				if (file.exists()) {
					continue;
				}
				if (entry.isDirectory()) {
					if (!file.exists())
						file.mkdirs();
					continue;
				}
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}
					
					
				int count;
				byte data[] = new byte[BUFFER];

				SecurityManager manager = new SecurityManager();
				manager.checkWrite(file.getPath());
				FileOutputStream fos = new FileOutputStream(file);
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = zis.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
			}
			zis.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
}
